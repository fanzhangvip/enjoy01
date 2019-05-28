package com.zero.activityhookdemo.hook;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.zero.activityhookdemo.StubActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

public class HookHelper {
    private static final String TAG = "Zero";

    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";

    public static void hookIActivityManager() {
        //TODO:
//        1. 找到了Hook的点
//        2. hook点 动态代理 静态？
//        3. 获取到getDefault的IActivityManager原始对象
//        4. 动态代理 准备classloader 接口
//        5  classloader, 获取当前线程
//        6. 接口 Class.forName("android.app.IActivityManager");
//        7. Proxy.newProxyInstance() 得到一个IActivityManagerProxy
//        8. IActivityManagerProxy融入到framework

//            public abstract class Singleton<T> {
//                private T mInstance;
//
//                protected abstract T create();
//
//                public final T get() {
//                    synchronized (this) {
//                        if (mInstance == null) {
//                            mInstance = create();
//                        }
//                        return mInstance;
//                    }
//                }
//            }

        try {
            Field gDefaultField = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Class<?> activityManager = Class.forName("android.app.ActivityManager");
                gDefaultField = activityManager.getDeclaredField("IActivityManagerSingleton");
            } else {
                Class<?> activityManager = Class.forName("android.app.ActivityManagerNative");
                //拿到 Singleton<IActivityManager> gDefault
                gDefaultField = activityManager.getDeclaredField("gDefault");
            }

            gDefaultField.setAccessible(true);
            //Singlon<IActivityManager>
            Object gDefault = gDefaultField.get(null);

            //拿到Singleton的Class对象
            Class<?> singletonClass = Class.forName("android.util.Singleton");
            Field mInstanceField = singletonClass.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);
            //获取到ActivityManagerNative里面的gDefault对象里面的原始的IActivityManager对象
            final Object rawIActivityManager = mInstanceField.get(gDefault);

            //进行动态代理
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            //生产IActivityManager的代理对象
            Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{iActivityManagerInterface}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Log.i(TAG, "invoke: method " + method.getName());
                    if ("startActivity".equals(method.getName())) {
                        Log.i(TAG, "准备启动activity");
                        for (Object obj : args) {
                            Log.i(TAG, "invoke: obj= " + obj);
                        }

                        //偷梁换柱 把Target 换成我们的Stub,欺骗AMS的权限验证
                        //拿到原始的Intent,然后保存
                        Intent raw = null;
                        int index = 0;
                        for (int i = 0; i < args.length; i++) {
                            if (args[i] instanceof Intent) {
                                raw = (Intent) args[i];
                                index = i;
                                break;
                            }
                        }
                        Log.i(TAG, "invoke: raw= " + raw);

                        //替换成Stub
                        Intent newIntent = new Intent();
                        String stubPackage = "com.zero.activityhookdemo";
                        newIntent.setComponent(new ComponentName(stubPackage, StubActivity.class.getName()));
                        //把这个newIntent放回到args,达到了一个欺骗AMS的目的
                        newIntent.putExtra(EXTRA_TARGET_INTENT, raw);
                        args[index] = newIntent;

                    }
                    return method.invoke(rawIActivityManager, args);
                }
            });

            //把我们的代理对象融入到framework
            mInstanceField.set(gDefault, proxy);


        } catch (Exception e) {
            Log.e(TAG, "hookIActivityManager: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void hookHandler() {
        //TODO:
        try {
            Class<?> atClass = Class.forName("android.app.ActivityThread");
            Field sCurrentActivityThreadField = atClass.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object sCurrentActivityThread = sCurrentActivityThreadField.get(null);
            //ActivityThread 一个app进程 只有一个，获取它的mH
            Field mHField = atClass.getDeclaredField("mH");
            mHField.setAccessible(true);
            final Handler mH = (Handler) mHField.get(sCurrentActivityThread);

            //获取mCallback
            Field mCallbackField = Handler.class.getDeclaredField("mCallback");
            mCallbackField.setAccessible(true);

            mCallbackField.set(mH, new Handler.Callback() {

                @Override
                public boolean handleMessage(Message msg) {
                    Log.i(TAG, "handleMessage: " + msg.what);
                    switch (msg.what) {
                        case 100: {
                            // final ActivityClientRecord r = (ActivityClientRecord) msg.obj;
//                            static final class ActivityClientRecord {
//                                IBinder token;
//                                int ident;
//                                Intent intent;//hook 恢复
                            //恢复真身
                            try {
                                Field intentField = msg.obj.getClass().getDeclaredField("intent");
                                intentField.setAccessible(true);
                                Intent intent = (Intent) intentField.get(msg.obj);
                                Intent targetIntent = intent.getParcelableExtra(EXTRA_TARGET_INTENT);
                                intent.setComponent(targetIntent.getComponent());

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        break;
                        case 159: {
                            Object obj = msg.obj;
                            Log.i(TAG, "handleMessage: obj=" + obj);
                            try {
                                Field mActivityCallbacksField = obj.getClass().getDeclaredField("mActivityCallbacks");
                                mActivityCallbacksField.setAccessible(true);
                                List mActivityCallbacks = (List)mActivityCallbacksField.get(obj);
                                Log.i(TAG, "handleMessage: mActivityCallbacks= " + mActivityCallbacks);
                                //注意了 这里如果有同学debug调试会发现第一次size=0 原因如下
                                //在Android O之前
                                //public static final int LAUNCH_ACTIVITY         = 100;
                                //public static final int PAUSE_ACTIVITY          = 101;
                                //public static final int PAUSE_ACTIVITY_FINISHING= 102;
                                //public static final int STOP_ACTIVITY_SHOW      = 103;
                                //public static final int STOP_ACTIVITY_HIDE      = 104;
                                //public static final int SHOW_WINDOW             = 105;
                                //public static final int HIDE_WINDOW             = 106;
                                //public static final int RESUME_ACTIVITY         = 107;
                                //public static final int SEND_RESULT             = 108;
                                //public static final int DESTROY_ACTIVITY        = 109;
                                //end
                                //从AndroidP开始重构了状态模式
                                //public static final int EXECUTE_TRANSACTION = 159;
                                // 首先一个app 只有一个ActivityThread 然后就只有一个mH
                                //我们app所有的activity的生命周期的处理都在mH的handleMessage里面处理
                                //在Android 8.0之前，不同的生命周期对应不同的msg.what处理
                                //在Android 8.0 改成了全部由EXECUTE_TRANSACTION来处理
                                //所以这里第一次mActivityCallbacks是MainActivity的生命周期回调的
//                                handleMessage: 159
//                                handleMessage: obj=android.app.servertransaction.ClientTransaction@efd342
//                                handleMessage: mActivityCallbacks= []
//                                invoke: method activityPaused
//                                handleMessage: 159
//                                handleMessage: obj=android.app.servertransaction.ClientTransaction@4962
//                                handleMessage: mActivityCallbacks= [WindowVisibilityItem{showWindow=true}]
//                                handleMessage: size= 1
//                                handleMessage: 159
//                                handleMessage: obj=android.app.servertransaction.ClientTransaction@9e98c6b
//                                handleMessage: mActivityCallbacks= [LaunchActivityItem{intent=Intent { cmp=com.zero.activityhookdemo/.StubActivity (has extras) },ident=168243404,info=ActivityInfo{5b8d769 com.zero.activityhookdemo.StubActivity},curConfig={1.0 310mcc260mnc [en_US] ldltr sw411dp w411dp h659dp 420dpi nrml port finger qwerty/v/v -nav/h winConfig={ mBounds=Rect(0, 0 - 0, 0) mAppBounds=Rect(0, 0 - 1080, 1794) mWindowingMode=fullscreen mActivityType=undefined} s.6},overrideConfig={1.0 310mcc260mnc [en_US] ldltr sw411dp w411dp h659dp 420dpi nrml port finger qwerty/v/v -nav/h winConfig={ mBounds=Rect(0, 0 - 1080, 1794) mAppBounds=Rect(0, 0 - 1080, 1794) mWindowingMode=fullscreen mActivityType=standard} s.6},referrer=com.zero.activityhookdemo,procState=2,state=null,persistentState=null,pendingResults=null,pendingNewIntents=null,profilerInfo=null}]
//                                handleMessage: size= 1
                                if(mActivityCallbacks.size()>0){
                                    Log.i(TAG, "handleMessage: size= " + mActivityCallbacks.size());
                                    String className = "android.app.servertransaction.LaunchActivityItem";
                                    if(mActivityCallbacks.get(0).getClass().getCanonicalName().equals(className)){
                                        Object object = mActivityCallbacks.get(0);
                                        Field intentField =object.getClass().getDeclaredField("mIntent");
                                        intentField.setAccessible(true);
                                        Intent intent = (Intent) intentField.get(object);
                                        Intent targetIntent = intent.getParcelableExtra(EXTRA_TARGET_INTENT);
                                        intent.setComponent(targetIntent.getComponent());
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        break;
                    }
                    mH.handleMessage(msg);
                    return true;
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "hookHandler: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void hookInstrumentation(Activity activity) {
        //TODO:
        Class<?> activityClass = Activity.class;
        //通过Activity.class 拿到 mInstrumentation字段
        Field field = null;
        try {
            field = activityClass.getDeclaredField("mInstrumentation");
            field.setAccessible(true);
            //根据activity内mInstrumentation字段 获取Instrumentation对象
            Instrumentation instrumentation = (Instrumentation) field.get(activity);
            //创建代理对象,注意了因为Instrumentation是类，不是接口 所以我们只能用静态代理，
            Instrumentation instrumentationProxy = new ProxyInstrumentation(instrumentation);
            //进行替换
            field.set(activity, instrumentationProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static class ProxyInstrumentation extends Instrumentation {

        private static final String TAG = "Zero";
        // ActivityThread中原始的对象, 保存起来
        Instrumentation mBase;

        public ProxyInstrumentation(Instrumentation base) {
            mBase = base;
        }

        public ActivityResult execStartActivity(
                Context who, IBinder contextThread, IBinder token, Activity target,
                Intent intent, int requestCode, Bundle options) {

            Log.d(TAG, "执行了startActivity, 参数如下: " + "who = [" + who + "], " +
                    "contextThread = [" + contextThread + "], token = [" + token + "], " +
                    "target = [" + target + "], intent = [" + intent +
                    "], requestCode = [" + requestCode + "], options = [" + options + "]");

            // 由于这个方法是隐藏的,因此需要使用反射调用;首先找到这个方法
            //execStartActivity有重载，别找错了
            try {
                Method execStartActivity = Instrumentation.class.getDeclaredMethod(
                        "execStartActivity",
                        Context.class, IBinder.class, IBinder.class, Activity.class,
                        Intent.class, int.class, Bundle.class);
                execStartActivity.setAccessible(true);
                return (ActivityResult) execStartActivity.invoke(mBase, who,
                        contextThread, token, target, intent, requestCode, options);
            } catch (Exception e) {
                throw new RuntimeException("do not support!!! pls adapt it");
            }
        }

        /**
         * 重写newActivity 因为newActivity 方法有变
         * 原来是：(Activity)cl.loadClass(className).newInstance();
         *
         * @param cl
         * @param className
         * @param intent
         * @return
         * @throws InstantiationException
         * @throws IllegalAccessException
         * @throws ClassNotFoundException
         */
        @Override
        public Activity newActivity(ClassLoader cl, String className,
                                    Intent intent)
                throws InstantiationException, IllegalAccessException,
                ClassNotFoundException {

            return mBase.newActivity(cl, className, intent);
        }
    }

    public static void hookActivityThreadInstrumentation() {
        //TODO:
        try {
            // 先获取到当前的ActivityThread对象
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            //currentActivityThread是一个static函数所以可以直接invoke，不需要带实例参数
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            // 拿到原始的 mInstrumentation字段
            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation mInstrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);
            // 创建代理对象
            Instrumentation proxyInstrumentation = new ProxyInstrumentation(mInstrumentation);
            // 偷梁换柱
            mInstrumentationField.set(currentActivityThread, proxyInstrumentation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
