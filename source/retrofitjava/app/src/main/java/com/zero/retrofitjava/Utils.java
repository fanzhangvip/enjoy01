package com.zero.retrofitjava;
//
//import java.io.FileOutputStream;
//
//import sun.misc.ProxyGenerator;
//
//public class Utils {
//
//    /**
//     * 生成代理类文件
//     */
//    public static void generyProxyFile(String classFileName,Class<?>[] classes) {//.class.getInterfaces()
//        byte[] classFile = ProxyGenerator.generateProxyClass(classFileName, classes);
//        String path = "./"+classFileName+".class";
//        try {
//            FileOutputStream fos = new FileOutputStream(path);
//            fos.write(classFile);
//            fos.flush();
//            System.out.println("代理类class文件写入成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("写入出错类");
//        }
//    }
//}
