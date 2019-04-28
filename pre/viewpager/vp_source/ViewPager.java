/*
 * Copyright 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.FocusFinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.Scroller;

import androidx.annotation.CallSuper;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Px;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Layout manager that allows the user to flip left and right
 * through pages of data.  You supply an implementation of a
 * {@link PagerAdapter} to generate the pages that the view shows.
 *
 * <p>ViewPager is most often used in conjunction with {@link android.app.Fragment},
 * which is a convenient way to supply and manage the lifecycle of each page.
 * There are standard adapters implemented for using fragments with the ViewPager,
 * which cover the most common use cases.  These are
 * {@link androidx.fragment.app.FragmentPagerAdapter} and
 * {@link androidx.fragment.app.FragmentStatePagerAdapter}; each of these
 * classes have simple code showing how to build a full user interface
 * with them.
 *
 * <p>Views which are annotated with the {@link DecorView} annotation are treated as
 * part of the view pagers 'decor'. Each decor view's position can be controlled via
 * its {@code android:layout_gravity} attribute. For example:
 *
 * <pre>
 * &lt;androidx.viewpager.widget.ViewPager
 *     android:layout_width=&quot;match_parent&quot;
 *     android:layout_height=&quot;match_parent&quot;&gt;
 *
 *     &lt;androidx.viewpager.widget.PagerTitleStrip
 *         android:layout_width=&quot;match_parent&quot;
 *         android:layout_height=&quot;wrap_content&quot;
 *         android:layout_gravity=&quot;top&quot; /&gt;
 *
 * &lt;/androidx.viewpager.widget.ViewPager&gt;
 * </pre>
 *
 * <p>For more information about how to use ViewPager, read <a
 * href="{@docRoot}training/implementing-navigation/lateral.html">Creating Swipe Views with
 * Tabs</a>.</p>
 *
 * <p>You can find examples of using ViewPager in the API 4+ Support Demos and API 13+ Support Demos
 * sample code.
 */
 /**
  * 允许数据页从左到右或者从右到左翻页，
  * 在APP中的很多场景都用得到，比如第一次安装APP时的用户引导页、图片浏览时左右翻页、广告Banner页等等都会用到ViewPager
  * 那么，关于ViewPager有什么问题呢？ 
  * 1. setOffsreenPageLimit()方法是如何实现页面缓存的？ 
  * 2. 在布局文件中，ViewPager布局内部能否添加其他View？ 
  * 3. 为什么ViewPager初始化时，显示了一个页面却不会触发onPageSelected回调？
 */
public class ViewPager extends ViewGroup {
    private static final String TAG = "ViewPager";
    private static final boolean DEBUG = false;

    private static final boolean USE_CACHE = false;

    private static final int DEFAULT_OFFSCREEN_PAGES = 1;
    private static final int MAX_SETTLE_DURATION = 600; // ms
    private static final int MIN_DISTANCE_FOR_FLING = 25; // dips

    private static final int DEFAULT_GUTTER_SIZE = 16; // dips

    private static final int MIN_FLING_VELOCITY = 400; // dips

    static final int[] LAYOUT_ATTRS = new int[] {
        android.R.attr.layout_gravity
    };

    /**
     * Used to track what the expected number of items in the adapter should be.
     * If the app changes this when we don't expect it, we'll throw a big obnoxious exception.
     */
    private int mExpectedAdapterCount;

    static class ItemInfo {
        Object object;//childView
        int position;//childView在Adapter中的位置
        boolean scrolling;//是否在滚动
        float widthFactor;//宽度的倍数，默认情况下是1
        float offset;//页面移动的偏移量，默认为0，粗暴的理解就是第几个页面
    }

    private static final Comparator<ItemInfo> COMPARATOR = new Comparator<ItemInfo>(){
        @Override
        public int compare(ItemInfo lhs, ItemInfo rhs) {
            return lhs.position - rhs.position;
        }
    };

    private static final Interpolator sInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    private final ArrayList<ItemInfo> mItems = new ArrayList<ItemInfo>();
    private final ItemInfo mTempItem = new ItemInfo();

    private final Rect mTempRect = new Rect();

    PagerAdapter mAdapter;
    int mCurItem;   // Index of currently displayed page.
    private int mRestoredCurItem = -1;
    private Parcelable mRestoredAdapterState = null;
    private ClassLoader mRestoredClassLoader = null;

   //创建Scroller对象：Scroller scroller=new Scroller(context);
   //重写computeScroll()方法
   //最后，在我们的smoothScrollTo方法中调用startScroll方法
    private Scroller mScroller;
    private boolean mIsScrollStarted;

    private PagerObserver mObserver;

    private int mPageMargin;
    private Drawable mMarginDrawable;
    private int mTopPageBounds;
    private int mBottomPageBounds;

    // Offsets of the first and last items, if known.
    // Set during population, used to determine if we are at the beginning
    // or end of the pager data set during touch scrolling.
    private float mFirstOffset = -Float.MAX_VALUE;
    private float mLastOffset = Float.MAX_VALUE;

    private int mChildWidthMeasureSpec;
    private int mChildHeightMeasureSpec;
    private boolean mInLayout;

    private boolean mScrollingCacheEnabled;

    private boolean mPopulatePending;
    private int mOffscreenPageLimit = DEFAULT_OFFSCREEN_PAGES;

    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private int mDefaultGutterSize;
    private int mGutterSize;
    private int mTouchSlop;
    /**
     * Position of the last motion event.
     */
    private float mLastMotionX;
    private float mLastMotionY;
    private float mInitialMotionX;
    private float mInitialMotionY;
    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     */
    private int mActivePointerId = INVALID_POINTER;
    /**
     * Sentinel value for no current active pointer.
     * Used by {@link #mActivePointerId}.
     */
    private static final int INVALID_POINTER = -1;

    /**
     * Determines speed during touch scrolling
     */
    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;
    private int mFlingDistance;
    private int mCloseEnough;

    // If the pager is at least this close to its final position, complete the scroll
    // on touch down and let the user interact with the content inside instead of
    // "catching" the flinging pager.
    private static final int CLOSE_ENOUGH = 2; // dp

    private boolean mFakeDragging;
    private long mFakeDragBeginTime;

    private EdgeEffect mLeftEdge;
    private EdgeEffect mRightEdge;

    private boolean mFirstLayout = true;
    private boolean mNeedCalculatePageOffsets = false;
    private boolean mCalledSuper;
    private int mDecorChildCount;

    private List<OnPageChangeListener> mOnPageChangeListeners;
    private OnPageChangeListener mOnPageChangeListener;
    private OnPageChangeListener mInternalPageChangeListener;
    private List<OnAdapterChangeListener> mAdapterChangeListeners;
    private PageTransformer mPageTransformer;
    private int mPageTransformerLayerType;

    private static final int DRAW_ORDER_DEFAULT = 0;
    private static final int DRAW_ORDER_FORWARD = 1;
    private static final int DRAW_ORDER_REVERSE = 2;
    private int mDrawingOrder;
    private ArrayList<View> mDrawingOrderedChildren;
    private static final ViewPositionComparator sPositionComparator = new ViewPositionComparator();

    /**
     * Indicates that the pager is in an idle, settled state. The current page
     * is fully in view and no animation is in progress.
     */
    public static final int SCROLL_STATE_IDLE = 0;

    /**
     * Indicates that the pager is currently being dragged by the user.
     */
    public static final int SCROLL_STATE_DRAGGING = 1;

    /**
     * Indicates that the pager is in the process of settling to a final position.
     */
    public static final int SCROLL_STATE_SETTLING = 2;

    private final Runnable mEndScrollRunnable = new Runnable() {
        @Override
        public void run() {
            setScrollState(SCROLL_STATE_IDLE);
            populate();
        }
    };

    private int mScrollState = SCROLL_STATE_IDLE;

    /**
     * Callback interface for responding to changing state of the selected page.
     */
    public interface OnPageChangeListener {

        /**
         * This method will be invoked when the current page is scrolled, either as part
         * of a programmatically initiated smooth scroll or a user initiated touch scroll.
         *
         * @param position Position index of the first page currently being displayed.
         *                 Page position+1 will be visible if positionOffset is nonzero.
         * @param positionOffset Value from [0, 1) indicating the offset from the page at position.
         * @param positionOffsetPixels Value in pixels indicating the offset from position.
         */
        void onPageScrolled(int position, float positionOffset, @Px int positionOffsetPixels);

        /**
         * This method will be invoked when a new page becomes selected. Animation is not
         * necessarily complete.
         *
         * @param position Position index of the new selected page.
         */
        void onPageSelected(int position);

        /**
         * Called when the scroll state changes. Useful for discovering when the user
         * begins dragging, when the pager is automatically settling to the current page,
         * or when it is fully stopped/idle.
         *
         * @param state The new scroll state.
         * @see ViewPager#SCROLL_STATE_IDLE
         * @see ViewPager#SCROLL_STATE_DRAGGING
         * @see ViewPager#SCROLL_STATE_SETTLING
         */
        void onPageScrollStateChanged(int state);
    }

    /**
     * Simple implementation of the {@link OnPageChangeListener} interface with stub
     * implementations of each method. Extend this if you do not intend to override
     * every method of {@link OnPageChangeListener}.
     */
    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // This space for rent
        }

        @Override
        public void onPageSelected(int position) {
            // This space for rent
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            // This space for rent
        }
    }

    /**
     * A PageTransformer is invoked whenever a visible/attached page is scrolled.
     * This offers an opportunity for the application to apply a custom transformation
     * to the page views using animation properties.
     *
     * <p>As property animation is only supported as of Android 3.0 and forward,
     * setting a PageTransformer on a ViewPager on earlier platform versions will
     * be ignored.</p>
     */
    public interface PageTransformer {
        /**
         * Apply a property transformation to the given page.
         *
         * @param page Apply the transformation to this page
         * @param position Position of page relative to the current front-and-center
         *                 position of the pager. 0 is front and center. 1 is one full
         *                 page position to the right, and -1 is one page position to the left.
         */
        void transformPage(@NonNull View page, float position);
    }

    /**
     * Callback interface for responding to adapter changes.
     */
    public interface OnAdapterChangeListener {
        /**
         * Called when the adapter for the given view pager has changed.
         *
         * @param viewPager  ViewPager where the adapter change has happened
         * @param oldAdapter the previously set adapter
         * @param newAdapter the newly set adapter
         */
        void onAdapterChanged(@NonNull ViewPager viewPager,
                @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter);
    }

    /**
     * Annotation which allows marking of views to be decoration views when added to a view
     * pager.
     *
     * <p>Views marked with this annotation can be added to the view pager with a layout resource.
     * An example being {@link PagerTitleStrip}.</p>
     *
     * <p>You can also control whether a view is a decor view but setting
     * {@link LayoutParams#isDecor} on the child's layout params.</p>
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    public @interface DecorView {
    }

    public ViewPager(@NonNull Context context) {
        super(context);
        initViewPager();
    }

    public ViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initViewPager();
    }

    void initViewPager() {
        setWillNotDraw(false);
        setDescendantFocusability(FOCUS_AFTER_DESCENDANTS);
        setFocusable(true);
        final Context context = getContext();
        //1. 创建
        mScroller = new Scroller(context, sInterpolator);
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        final float density = context.getResources().getDisplayMetrics().density;

        mTouchSlop = configuration.getScaledPagingTouchSlop();
        mMinimumVelocity = (int) (MIN_FLING_VELOCITY * density);
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        mLeftEdge = new EdgeEffect(context);
        mRightEdge = new EdgeEffect(context);

        mFlingDistance = (int) (MIN_DISTANCE_FOR_FLING * density);
        mCloseEnough = (int) (CLOSE_ENOUGH * density);
        mDefaultGutterSize = (int) (DEFAULT_GUTTER_SIZE * density);

        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());

        if (ViewCompat.getImportantForAccessibility(this)
                == ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_AUTO) {
            ViewCompat.setImportantForAccessibility(this,
                    ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_YES);
        }

        ViewCompat.setOnApplyWindowInsetsListener(this,
                new androidx.core.view.OnApplyWindowInsetsListener() {
                    private final Rect mTempRect = new Rect();

                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(final View v,
                            final WindowInsetsCompat originalInsets) {
                        // First let the ViewPager itself try and consume them...
                        final WindowInsetsCompat applied =
                                ViewCompat.onApplyWindowInsets(v, originalInsets);
                        if (applied.isConsumed()) {
                            // If the ViewPager consumed all insets, return now
                            return applied;
                        }

                        // Now we'll manually dispatch the insets to our children. Since ViewPager
                        // children are always full-height, we do not want to use the standard
                        // ViewGroup dispatchApplyWindowInsets since if child 0 consumes them,
                        // the rest of the children will not receive any insets. To workaround this
                        // we manually dispatch the applied insets, not allowing children to
                        // consume them from each other. We do however keep track of any insets
                        // which are consumed, returning the union of our children's consumption
                        final Rect res = mTempRect;
                        res.left = applied.getSystemWindowInsetLeft();
                        res.top = applied.getSystemWindowInsetTop();
                        res.right = applied.getSystemWindowInsetRight();
                        res.bottom = applied.getSystemWindowInsetBottom();

                        for (int i = 0, count = getChildCount(); i < count; i++) {
                            final WindowInsetsCompat childInsets = ViewCompat
                                    .dispatchApplyWindowInsets(getChildAt(i), applied);
                            // Now keep track of any consumed by tracking each dimension's min
                            // value
                            res.left = Math.min(childInsets.getSystemWindowInsetLeft(),
                                    res.left);
                            res.top = Math.min(childInsets.getSystemWindowInsetTop(),
                                    res.top);
                            res.right = Math.min(childInsets.getSystemWindowInsetRight(),
                                    res.right);
                            res.bottom = Math.min(childInsets.getSystemWindowInsetBottom(),
                                    res.bottom);
                        }

                        // Now return a new WindowInsets, using the consumed window insets
                        return applied.replaceSystemWindowInsets(
                                res.left, res.top, res.right, res.bottom);
                    }
                });
    }

    @Override
    protected void onDetachedFromWindow() {
        removeCallbacks(mEndScrollRunnable);
        // To be on the safe side, abort the scroller
        if ((mScroller != null) && !mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        super.onDetachedFromWindow();
    }

    void setScrollState(int newState) {
        if (mScrollState == newState) {
            return;
        }

        mScrollState = newState;
        if (mPageTransformer != null) {
            // PageTransformers can do complex things that benefit from hardware layers.
            enableLayers(newState != SCROLL_STATE_IDLE);
        }
        dispatchOnScrollStateChanged(newState);
    }

    /**
     * Set a PagerAdapter that will supply views for this pager as needed.
     *
     * @param adapter Adapter to use
     */
    public void setAdapter(@Nullable PagerAdapter adapter) {
        //1.如果已经设置过PagerAdapter，即mAdapter != null，
    // 则做一些清理工作
        if (mAdapter != null) {
            //2.清除观察者
            mAdapter.setViewPagerObserver(null);
             //3.回调startUpdate函数，告诉PagerAdapter开始更新要显示的页面
            mAdapter.startUpdate(this);
            //4.如果之前保存有页面，则将之前所有的页面destroy掉
            for (int i = 0; i < mItems.size(); i++) {
                final ItemInfo ii = mItems.get(i);
                mAdapter.destroyItem(this, ii.position, ii.object);
            }
            //5.回调finishUpdate，告诉PagerAdapter结束更新
            mAdapter.finishUpdate(this);
            //6.将所有的页面清除
            mItems.clear();
            //7.将所有的非Decor View移除，即将页面移除
            removeNonDecorViews();
            //8.当前的显示页面重置到第一个
            mCurItem = 0;
            //9.滑动重置到(0,0)位置
            scrollTo(0, 0);
        }

        //10.保存上一次的PagerAdapter
        final PagerAdapter oldAdapter = mAdapter;
        //11.设置mAdapter为新的PagerAdapter
        mAdapter = adapter;
        //12.设置期望的适配器中的页面数量为0个
        mExpectedAdapterCount = 0;
        //13.如果设置的PagerAdapter不为null
        if (mAdapter != null) {
            //14.确保观察者不为null，观察者主要是用于监视数据源的内容发生变化
            if (mObserver == null) {
                mObserver = new PagerObserver();
            }
            //15.将观察者设置到PagerAdapter中
            mAdapter.setViewPagerObserver(mObserver);
            mPopulatePending = false;
            //16.保存上一次是否是第一次Layout
            final boolean wasFirstLayout = mFirstLayout;
            //17.设定当前为第一次Layout
            mFirstLayout = true;
             //18.更新期望的数据源中页面个数
            mExpectedAdapterCount = mAdapter.getCount();
            if (mRestoredCurItem >= 0) {
                //20.回调PagerAdapter的restoreState函数
                mAdapter.restoreState(mRestoredAdapterState, mRestoredClassLoader);
                setCurrentItemInternal(mRestoredCurItem, false, true);
                //21.标记无需再恢复
                mRestoredCurItem = -1;
                mRestoredAdapterState = null;
                mRestoredClassLoader = null;
            } else if (!wasFirstLayout) {//如果在此之前不是第一次Layout
                //22.由于ViewPager并不是将所有页面作为子View，
            // 而是最多缓存用户指定缓存个数*2（左右两边，可能左边或右边没有那么多页面）
            //因此需要创建和销毁页面，populate主要工作就是这些
                populate();
            } else {
                //23.重新布局（Layout）
                requestLayout();
            }
        }

        // Dispatch the change to any listeners
        //24.如果PagerAdapter发生变化，并且设置了OnAdapterChangeListener监听器
    // 则回调OnAdapterChangeListener的onAdapterChanged函数
        if (mAdapterChangeListeners != null && !mAdapterChangeListeners.isEmpty()) {
            for (int i = 0, count = mAdapterChangeListeners.size(); i < count; i++) {
                mAdapterChangeListeners.get(i).onAdapterChanged(this, oldAdapter, adapter);
            }
        }
    }

    private void removeNonDecorViews() {
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (!lp.isDecor) {
                removeViewAt(i);
                i--;
            }
        }
    }

    /**
     * Retrieve the current adapter supplying pages.
     *
     * @return The currently registered PagerAdapter
     */
    @Nullable
    public PagerAdapter getAdapter() {
        return mAdapter;
    }

    /**
     * Add a listener that will be invoked whenever the adapter for this ViewPager changes.
     *
     * @param listener listener to add
     */
    public void addOnAdapterChangeListener(@NonNull OnAdapterChangeListener listener) {
        if (mAdapterChangeListeners == null) {
            mAdapterChangeListeners = new ArrayList<>();
        }
        mAdapterChangeListeners.add(listener);
    }

    /**
     * Remove a listener that was previously added via
     * {@link #addOnAdapterChangeListener(OnAdapterChangeListener)}.
     *
     * @param listener listener to remove
     */
    public void removeOnAdapterChangeListener(@NonNull OnAdapterChangeListener listener) {
        if (mAdapterChangeListeners != null) {
            mAdapterChangeListeners.remove(listener);
        }
    }

    private int getClientWidth() {
        return getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
    }

    /**
     * Set the currently selected page. If the ViewPager has already been through its first
     * layout with its current adapter there will be a smooth animated transition between
     * the current item and the specified item.
     *
     * @param item Item index to select
     */
    public void setCurrentItem(int item) {
        mPopulatePending = false;
        setCurrentItemInternal(item, !mFirstLayout, false);
    }

    /**
     * Set the currently selected page.
     *
     * @param item Item index to select
     * @param smoothScroll True to smoothly scroll to the new item, false to transition immediately
     */
    public void setCurrentItem(int item, boolean smoothScroll) {
        mPopulatePending = false;
        setCurrentItemInternal(item, smoothScroll, false);
    }

    public int getCurrentItem() {
        return mCurItem;
    }

    void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
        setCurrentItemInternal(item, smoothScroll, always, 0);
    }

    void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
        if (mAdapter == null || mAdapter.getCount() <= 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (!always && mCurItem == item && mItems.size() != 0) {
            setScrollingCacheEnabled(false);
            return;
        }

        if (item < 0) {
            item = 0;
        } else if (item >= mAdapter.getCount()) {
            item = mAdapter.getCount() - 1;
        }
        final int pageLimit = mOffscreenPageLimit;
        if (item > (mCurItem + pageLimit) || item < (mCurItem - pageLimit)) {
            // We are doing a jump by more than one page.  To avoid
            // glitches, we want to keep all current pages in the view
            // until the scroll ends.
            for (int i = 0; i < mItems.size(); i++) {
                mItems.get(i).scrolling = true;
            }
        }
        final boolean dispatchSelected = mCurItem != item;

        if (mFirstLayout) {
            // We don't have any idea how big we are yet and shouldn't have any pages either.
            // Just set things up and let the pending layout handle things.
            mCurItem = item;
            if (dispatchSelected) {
                dispatchOnPageSelected(item);
            }
            requestLayout();
        } else {
            populate(item);
            scrollToItem(item, smoothScroll, velocity, dispatchSelected);
        }
    }

    private void scrollToItem(int item, boolean smoothScroll, int velocity,
            boolean dispatchSelected) {
        final ItemInfo curInfo = infoForPosition(item);
        int destX = 0;
        if (curInfo != null) {
            final int width = getClientWidth();
            destX = (int) (width * Math.max(mFirstOffset,
                    Math.min(curInfo.offset, mLastOffset)));
        }
        if (smoothScroll) {
            smoothScrollTo(destX, 0, velocity);
            if (dispatchSelected) {
                dispatchOnPageSelected(item);
            }
        } else {
            if (dispatchSelected) {//是否需要分发OnPageSelected回调
                dispatchOnPageSelected(item);
            }
            completeScroll(false);
            scrollTo(destX, 0);
            pageScrolled(destX);
        }
    }

    /**
     * Set a listener that will be invoked whenever the page changes or is incrementally
     * scrolled. See {@link OnPageChangeListener}.
     *
     * @param listener Listener to set
     *
     * @deprecated Use {@link #addOnPageChangeListener(OnPageChangeListener)}
     * and {@link #removeOnPageChangeListener(OnPageChangeListener)} instead.
     */
    @Deprecated
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    /**
     * Add a listener that will be invoked whenever the page changes or is incrementally
     * scrolled. See {@link OnPageChangeListener}.
     *
     * <p>Components that add a listener should take care to remove it when finished.
     * Other components that take ownership of a view may call {@link #clearOnPageChangeListeners()}
     * to remove all attached listeners.</p>
     *
     * @param listener listener to add
     */
    public void addOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        mOnPageChangeListeners.add(listener);
    }

    /**
     * Remove a listener that was previously added via
     * {@link #addOnPageChangeListener(OnPageChangeListener)}.
     *
     * @param listener listener to remove
     */
    public void removeOnPageChangeListener(@NonNull OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }

    /**
     * Remove all listeners that are notified of any changes in scroll state or position.
     */
    public void clearOnPageChangeListeners() {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.clear();
        }
    }

    /**
     * Sets a {@link PageTransformer} that will be called for each attached page whenever
     * the scroll position is changed. This allows the application to apply custom property
     * transformations to each page, overriding the default sliding behavior.
     *
     * <p><em>Note:</em> By default, calling this method will cause contained pages to use
     * {@link View#LAYER_TYPE_HARDWARE}. This layer type allows custom alpha transformations,
     * but it will cause issues if any of your pages contain a {@link android.view.SurfaceView}
     * and you have not called {@link android.view.SurfaceView#setZOrderOnTop(boolean)} to put that
     * {@link android.view.SurfaceView} above your app content. To disable this behavior, call
     * {@link #setPageTransformer(boolean,PageTransformer,int)} and pass
     * {@link View#LAYER_TYPE_NONE} for {@code pageLayerType}.</p>
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer PageTransformer that will modify each page's animation properties
     */
    public void setPageTransformer(boolean reverseDrawingOrder,
            @Nullable PageTransformer transformer) {
        setPageTransformer(reverseDrawingOrder, transformer, View.LAYER_TYPE_HARDWARE);
    }

    /**
     * Sets a {@link PageTransformer} that will be called for each attached page whenever
     * the scroll position is changed. This allows the application to apply custom property
     * transformations to each page, overriding the default sliding behavior.
     *
     * @param reverseDrawingOrder true if the supplied PageTransformer requires page views
     *                            to be drawn from last to first instead of first to last.
     * @param transformer PageTransformer that will modify each page's animation properties
     * @param pageLayerType View layer type that should be used for ViewPager pages. It should be
     *                      either {@link View#LAYER_TYPE_HARDWARE},
     *                      {@link View#LAYER_TYPE_SOFTWARE}, or
     *                      {@link View#LAYER_TYPE_NONE}.
     */
    public void setPageTransformer(boolean reverseDrawingOrder,
            @Nullable PageTransformer transformer, int pageLayerType) {
        final boolean hasTransformer = transformer != null;
        final boolean needsPopulate = hasTransformer != (mPageTransformer != null);
        mPageTransformer = transformer;
        setChildrenDrawingOrderEnabled(hasTransformer);
        if (hasTransformer) {
            mDrawingOrder = reverseDrawingOrder ? DRAW_ORDER_REVERSE : DRAW_ORDER_FORWARD;
            mPageTransformerLayerType = pageLayerType;
        } else {
            mDrawingOrder = DRAW_ORDER_DEFAULT;
        }
        if (needsPopulate) populate();
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        final int index = mDrawingOrder == DRAW_ORDER_REVERSE ? childCount - 1 - i : i;
        final int result =
                ((LayoutParams) mDrawingOrderedChildren.get(index).getLayoutParams()).childIndex;
        return result;
    }

    /**
     * Set a separate OnPageChangeListener for internal use by the support library.
     *
     * @param listener Listener to set
     * @return The old listener that was set, if any.
     */
    OnPageChangeListener setInternalPageChangeListener(OnPageChangeListener listener) {
        OnPageChangeListener oldListener = mInternalPageChangeListener;
        mInternalPageChangeListener = listener;
        return oldListener;
    }

    /**
     * Returns the number of pages that will be retained to either side of the
     * current page in the view hierarchy in an idle state. Defaults to 1.
     *
     * @return How many pages will be kept offscreen on either side
     * @see #setOffscreenPageLimit(int)
     */
    public int getOffscreenPageLimit() {
        return mOffscreenPageLimit;
    }

    /**
     * Set the number of pages that should be retained to either side of the
     * current page in the view hierarchy in an idle state. Pages beyond this
     * limit will be recreated from the adapter when needed.
     *
     * <p>This is offered as an optimization. If you know in advance the number
     * of pages you will need to support or have lazy-loading mechanisms in place
     * on your pages, tweaking this setting can have benefits in perceived smoothness
     * of paging animations and interaction. If you have a small number of pages (3-4)
     * that you can keep active all at once, less time will be spent in layout for
     * newly created view subtrees as the user pages back and forth.</p>
     *
     * <p>You should keep this limit low, especially if your pages have complex layouts.
     * This setting defaults to 1.</p>
     *
     * @param limit How many pages will be kept offscreen in an idle state.
     */
    public void setOffscreenPageLimit(int limit) {
        if (limit < DEFAULT_OFFSCREEN_PAGES) {
            Log.w(TAG, "Requested offscreen page limit " + limit + " too small; defaulting to "
                    + DEFAULT_OFFSCREEN_PAGES);
            limit = DEFAULT_OFFSCREEN_PAGES;
        }
        if (limit != mOffscreenPageLimit) {
            mOffscreenPageLimit = limit;
            populate();
        }
    }

    /**
     * Set the margin between pages.
     *
     * @param marginPixels Distance between adjacent pages in pixels
     * @see #getPageMargin()
     * @see #setPageMarginDrawable(Drawable)
     * @see #setPageMarginDrawable(int)
     */
    public void setPageMargin(int marginPixels) {
        final int oldMargin = mPageMargin;
        mPageMargin = marginPixels;

        final int width = getWidth();
        recomputeScrollPosition(width, width, marginPixels, oldMargin);

        requestLayout();
    }

    /**
     * Return the margin between pages.
     *
     * @return The size of the margin in pixels
     */
    public int getPageMargin() {
        return mPageMargin;
    }

    /**
     * Set a drawable that will be used to fill the margin between pages.
     *
     * @param d Drawable to display between pages
     */
    public void setPageMarginDrawable(@Nullable Drawable d) {
        mMarginDrawable = d;
        if (d != null) refreshDrawableState();
        setWillNotDraw(d == null);
        invalidate();
    }

    /**
     * Set a drawable that will be used to fill the margin between pages.
     *
     * @param resId Resource ID of a drawable to display between pages
     */
    public void setPageMarginDrawable(@DrawableRes int resId) {
        setPageMarginDrawable(ContextCompat.getDrawable(getContext(), resId));
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == mMarginDrawable;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        final Drawable d = mMarginDrawable;
        if (d != null && d.isStateful()) {
            d.setState(getDrawableState());
        }
    }

    // We want the duration of the page snap animation to be influenced by the distance that
    // the screen has to travel, however, we don't want this duration to be effected in a
    // purely linear fashion. Instead, we use this method to moderate the effect that the distance
    // of travel has on the overall snap duration.
    float distanceInfluenceForSnapDuration(float f) {
        f -= 0.5f; // center the values about 0.
        f *= 0.3f * (float) Math.PI / 2.0f;
        return (float) Math.sin(f);
    }

    /**
     * Like {@link View#scrollBy}, but scroll smoothly instead of immediately.
     *
     * @param x the number of pixels to scroll by on the X axis
     * @param y the number of pixels to scroll by on the Y axis
     */
    void smoothScrollTo(int x, int y) {
        smoothScrollTo(x, y, 0);
    }

    /**
     * Like {@link View#scrollBy}, but scroll smoothly instead of immediately.
     *
     * @param x the number of pixels to scroll by on the X axis
     * @param y the number of pixels to scroll by on the Y axis
     * @param velocity the velocity associated with a fling, if applicable. (0 otherwise)
     */
    void smoothScrollTo(int x, int y, int velocity) {
        if (getChildCount() == 0) {
            // Nothing to do.
            // 如果没有页面，啥也不干
            setScrollingCacheEnabled(false);
            return;
        }

        //定义x轴起始位置
        int sx;
        //判断在此之前mScroller是否还在计算滚动
        boolean wasScrolling = (mScroller != null) && !mScroller.isFinished();
         //如果当前在滚动
        if (wasScrolling) {
            // We're in the middle of a previously initiated scrolling. Check to see
            // whether that scrolling has actually started (if we always call getStartX
            // we can get a stale value from the scroller if it hadn't yet had its first
            // computeScrollOffset call) to decide what is the current scrolling position.
            //根据在此之前是否还在滚动来决定如何获取当前的x位置
            sx = mIsScrollStarted ? mScroller.getCurrX() : mScroller.getStartX();
            // And abort the current scrolling.
            // 如果mScroller在此之前还在计算滚动，则将其停止计算，并直接滑动到最终位置，
            // 这个最终位置即为此刻smoothScrollTo的起始位置
            mScroller.abortAnimation();
            //不启用缓存
            setScrollingCacheEnabled(false);
        } else {//如果当前滚动结束
            sx = getScrollX();
        }
        //获取y轴起始位置
        int sy = getScrollY();
        int dx = x - sx;
        int dy = y - sy;
        //如果x和y方向的移动距离都是0，说明无需移动，结束并返回
        if (dx == 0 && dy == 0) {
            //做一些清理和还原工作
            completeScroll(false);
            //已经确定好新的页面，将mCurItem设置为新的页面以及其他的相关处理
            populate();
             //设置当前的滚动状态
            setScrollState(SCROLL_STATE_IDLE);
            return;
        }

        //启用缓存，即对每个子View调用setDrawingCacheEnabled(true)
        setScrollingCacheEnabled(true);
        //设置当前的滚动状态
        setScrollState(SCROLL_STATE_SETTLING);

        //获取宽度及一半宽度
        final int width = getClientWidth();
        final int halfWidth = width / 2;
        //要移动的距离占宽度的比例，这个比例必须得小于等于1
        final float distanceRatio = Math.min(1f, 1.0f * Math.abs(dx) / width);
        //smoothScrollTo并没有使用匀速滑动，而是通过distanceInfluenceForSnapDuration函数
        //来实现变速，这里与Scroller里面的插值器之间并无影响
        final float distance = halfWidth + halfWidth
                * distanceInfluenceForSnapDuration(distanceRatio);

        int duration;
        velocity = Math.abs(velocity);
        //如果手指滑动速度不为0
        if (velocity > 0) {
             //如果是手指滑动，则需要根据手指滑动速度计算滑动持续时间
            duration = 4 * Math.round(1000 * Math.abs(distance / velocity));
        } else {
            //如果手指滑动速度为0，即，是通过代码的方式滑动到指定位置，则使用另一种方式计算滑动持续时间
            final float pageWidth = width * mAdapter.getPageWidth(mCurItem);
            final float pageDelta = (float) Math.abs(dx) / (pageWidth + mPageMargin);
            duration = (int) ((pageDelta + 1) * 100);
        }
        //确保整个滑动时间不超出最大的时间
        duration = Math.min(duration, MAX_SETTLE_DURATION);

        // Reset the "scroll started" flag. It will be flipped to true in all places
        // where we call computeScrollOffset().
        //将mIsScrollStarted标记重置为false，表示没有开始滚动，
        //这个标记会在computeScrollOffset函数中重置为true，
        //所以不用担心会影响到其他地方的判断
        mIsScrollStarted = false;
        //开始平滑
        mScroller.startScroll(sx, sy, dx, dy, duration);
        ViewCompat.postInvalidateOnAnimation(this);
    }

    ItemInfo addNewItem(int position, int index) {
        ItemInfo ii = new ItemInfo();//新建一个ItemInfo对象
        ii.position = position;
        ii.object = mAdapter.instantiateItem(this, position);//用Adapter创建一个childView
        ii.widthFactor = mAdapter.getPageWidth(position);//默认返回1.0f
        if (index < 0 || index >= mItems.size()) {
            mItems.add(ii);//添加到mItems中
        } else {
            mItems.add(index, ii);
        }
        return ii;
    }

    void dataSetChanged() {
        // This method only gets called if our observer is attached, so mAdapter is non-null.

        final int adapterCount = mAdapter.getCount();
        mExpectedAdapterCount = adapterCount;
        boolean needPopulate = mItems.size() < mOffscreenPageLimit * 2 + 1
                && mItems.size() < adapterCount;
        int newCurrItem = mCurItem;

        boolean isUpdating = false;
        for (int i = 0; i < mItems.size(); i++) {
            final ItemInfo ii = mItems.get(i);
            final int newPos = mAdapter.getItemPosition(ii.object);

            if (newPos == PagerAdapter.POSITION_UNCHANGED) {
                continue;
            }

            if (newPos == PagerAdapter.POSITION_NONE) {
                mItems.remove(i);
                i--;

                if (!isUpdating) {
                    mAdapter.startUpdate(this);
                    isUpdating = true;
                }

                mAdapter.destroyItem(this, ii.position, ii.object);
                needPopulate = true;

                if (mCurItem == ii.position) {
                    // Keep the current item in the valid range
                    newCurrItem = Math.max(0, Math.min(mCurItem, adapterCount - 1));
                    needPopulate = true;
                }
                continue;
            }

            if (ii.position != newPos) {
                if (ii.position == mCurItem) {
                    // Our current item changed position. Follow it.
                    newCurrItem = newPos;
                }

                ii.position = newPos;
                needPopulate = true;
            }
        }

        if (isUpdating) {
            mAdapter.finishUpdate(this);
        }

        Collections.sort(mItems, COMPARATOR);

        if (needPopulate) {
            // Reset our known page widths; populate will recompute them.
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (!lp.isDecor) {
                    lp.widthFactor = 0.f;
                }
            }

            setCurrentItemInternal(newCurrItem, false, true);
            requestLayout();
        }
    }

    void populate() {
        populate(mCurItem);
    }

    void populate(int newCurrentItem) {
        ItemInfo oldCurInfo = null;
        if (mCurItem != newCurrentItem) {
            oldCurInfo = infoForPosition(mCurItem);//获取旧的ItemInfo对象
            mCurItem = newCurrentItem;//更新mCurItem的值，就是在Adapter中的position
        }

        if (mAdapter == null) {
            //对子View的绘制顺序进行排序，优先绘制Decor View
        //再按照position从小到大排序
            sortChildDrawingOrder();
            return;
        }

        // Bail now if we are waiting to populate.  This is to hold off
        // on creating views from the time the user releases their finger to
        // fling to a new position until we have finished the scroll to
        // that position, avoiding glitches from happening at that point.
         //如果我们正在等待populate,那么在用户手指抬起切换到新的位置期间应该推迟创建子View，
    // 直到滚动到最终位置再去创建，以免在这个期间出现差错
        if (mPopulatePending) {
            if (DEBUG) Log.i(TAG, "populate is pending, skipping for now...");
            //对子View的绘制顺序进行排序，优先绘制Decor View
        //再按照position从小到大排序
            sortChildDrawingOrder();
            return;
        }

        // Also, don't populate until we are attached to a window.  This is to
        // avoid trying to populate before we have restored our view hierarchy
        // state and conflicting with what is restored.
         //同样，在ViewPager没有attached到window之前，不要populate.
    // 这是因为如果我们在恢复View的层次结构之前进行populate，可能会与要恢复的内容有冲突
        if (getWindowToken() == null) {
            return;
        }

    //回调PagerAdapter的startUpdate函数，
    // 告诉PagerAdapter开始更新要显示的页面
        mAdapter.startUpdate(this);
       //mOffscreenPageLimit就是setOffscreenPageLimit方法设置的值
        final int pageLimit = mOffscreenPageLimit;
        //根据下面三行代码可知：mItems的长度就是 2 * pageLimit + 1
        //这里声明的startPos和endPos在后面会起作用，大家注意一下
        //因为我们的mItems长度是有限的，并且与pageLimit有关，所以很可能出现页面总数大于mItems长度的情况。当显示的页面改变时，我们必须将一些ItemInfo添加进来，将另一些ItemInfo移除。 
        //以保证我们的mItems中的ItemInfo.position是这样的： 
        //[ startPos … mCurItem … endPos ] 
        //确保起始位置大于等于0，如果用户设置了缓存页面数量，第一个页面为当前页面减去缓存页面数量
        final int startPos = Math.max(0, mCurItem - pageLimit);
        //保存数据源中的数据个数
        final int N = mAdapter.getCount();
        //确保最后的位置小于等于数据源中数据个数-1，
    // 如果用户设置了缓存页面数量，第一个页面为当前页面加缓存页面数量
        final int endPos = Math.min(N - 1, mCurItem + pageLimit);

      //判断用户是否增减了数据源的元素，如果增减了且没有调用notifyDataSetChanged，则抛出异常
        if (N != mExpectedAdapterCount) {
            String resName;
            try {
                resName = getResources().getResourceName(getId());
            } catch (Resources.NotFoundException e) {
                resName = Integer.toHexString(getId());
            }
            throw new IllegalStateException("The application's PagerAdapter changed the adapter's"
                    + " contents without calling PagerAdapter#notifyDataSetChanged!"
                    + " Expected adapter item count: " + mExpectedAdapterCount + ", found: " + N
                    + " Pager id: " + resName
                    + " Pager class: " + getClass()
                    + " Problematic adapter: " + mAdapter.getClass());
        }

        // Locate the currently focused item or add it if needed.
        // 遍历mItems列表，找出mCurItem对应的ItemInfo对象，是根据position来判断的
        //定位到当前获焦的页面，如果没有的话，则添加一个
        int curIndex = -1;
        ItemInfo curItem = null;
        //遍历每个页面对应的ItemInfo，找出获焦页面
        for (curIndex = 0; curIndex < mItems.size(); curIndex++) {
            final ItemInfo ii = mItems.get(curIndex);
            //找到当前页面对应的ItemInfo后，跳出循环
            if (ii.position >= mCurItem) {
                if (ii.position == mCurItem) curItem = ii;
                break;
            }
        }
        // 如果mItems中还未保存该ItemInfo，则创建一个IntemInfo对象
        //如果没有找到获焦的页面，说明mItems列表里面没有保存获焦页面，
        // 需要将获焦页面加入到mItems里面
        if (curItem == null && N > 0) {
            curItem = addNewItem(mCurItem, curIndex);
        }

        // Fill 3x the available width or up to the number of offscreen
        // pages requested to either side, whichever is larger.
        // If we have no current item we have no work to do.
        //默认缓存当前页面的左右两边的页面，如果用户设定了缓存页面数量，
    // 则将当前页面两边都缓存用户指定的数量的页面
    //如果当前没有页面，则我们啥也不需要做
        if (curItem != null) { 
            //1.调整curItem左边的对象
            float extraWidthLeft = 0.f;
            // curIndex是curItem在mItems中的索引
            // itemIndex就是curItem左边的ItemInfo的索引
            //左边的页面
            int itemIndex = curIndex - 1;
            //获取左边的ItemInfo对象
            //如果当前页面左边有页面，则将左边页面对应的ItemInfo取出，否则左边页面的ItemInfo为null
            ItemInfo ii = itemIndex >= 0 ? mItems.get(itemIndex) : null;
            //保存显示区域的宽度
            final int clientWidth = getClientWidth();
            //curItem左边需要的宽度，默认情况下为1.0f
            //算出左边页面需要的宽度，注意，这里的宽度是指实际宽度与可视区域宽度比例，
        // 即实际宽度=leftWidthNeeded*clientWidth
            final float leftWidthNeeded = clientWidth <= 0 ? 0 :
                    2.f - curItem.widthFactor + (float) getPaddingLeft() / (float) clientWidth;
            //遍历mItems左半部分，即curIndex左边的对象
            //只有在pos < startPos时才能退出循环，否则会一直遍历到pos=0
            //从当前页面左边第一个页面开始，左边的页面进行遍历
            for (int pos = mCurItem - 1; pos >= 0; pos--) {
                // 建议大家先从下面的else if开始看，因为这里的逻辑是准备退出循环了
                //如果左边的宽度超过了所需的宽度，并且当前当前页面位置比第一个缓存页面位置小
            //这说明这个页面需要Destroy掉
                if (extraWidthLeft >= leftWidthNeeded && pos < startPos) {
                     //当pos < startPos，说明mItems左边部分已经调整完毕了
                    //此时的ii代表的是，startPos左边的对象了
                    //如果左边已经没有页面了，跳出循环
                    if (ii == null) {
                        break;
                    }
                    //如果startPos左边还有对象，需要从mItems中移除
                    //将当前页面destroy掉
                    if (pos == ii.position && !ii.scrolling) {
                        mItems.remove(itemIndex);
                        //回调PagerAdapter的destroyItem
                        mAdapter.destroyItem(this, pos, ii.object);
                        if (DEBUG) {
                            Log.i(TAG, "populate() - destroyItem() with pos: " + pos
                                    + " view: " + ((View) ii.object));
                        }
                        //由于mItems删除了一个元素
                    //需要将索引减一
                        itemIndex--;
                        curIndex--;
                        ii = itemIndex >= 0 ? mItems.get(itemIndex) : null;
                    }
                //如果curIndex左边的ItemInfo对象不为null
                } else if (ii != null && pos == ii.position) {
                    //如果当前位置是需要缓存的位置，并且这个位置上的页面已经存在
                //则将左边宽度加上当前位置的页面
                    extraWidthLeft += ii.widthFactor;//累加curItem左边需要的宽度
                    //mItems往左遍历
                    itemIndex--;//再往curIndex左边移一个位置
                     //ii设置为当前遍历的页面的左边一个页面
                    ii = itemIndex >= 0 ? mItems.get(itemIndex) : null;//取出ItemInfo对象
                } else { //如果curIndex左边的ItemInfo为null
                    //如果当前位置是需要缓存，并且这个位置没有页面
                     //需要添加一个ItemInfo,而addNewItem是通过PagerAdapter的instantiateItem获取对象
                    ii = addNewItem(pos, itemIndex + 1);
                    //将左边宽度加上当前位置的页面
                    extraWidthLeft += ii.widthFactor;
                    //由于新加了一个元素，当前的索引号需要加1
                    curIndex++;
                    //ii设置为当前遍历的页面的左边一个页面
                    ii = itemIndex >= 0 ? mItems.get(itemIndex) : null;
                }
            }

            float extraWidthRight = curItem.widthFactor;
            itemIndex = curIndex + 1;
            //2.调整curItem右边的对象，逻辑与上面类似
            if (extraWidthRight < 2.f) {
                ii = itemIndex < mItems.size() ? mItems.get(itemIndex) : null;
                final float rightWidthNeeded = clientWidth <= 0 ? 0 :
                        (float) getPaddingRight() / (float) clientWidth + 2.f;
                for (int pos = mCurItem + 1; pos < N; pos++) {
                    if (extraWidthRight >= rightWidthNeeded && pos > endPos) {
                        if (ii == null) {
                            break;
                        }
                        if (pos == ii.position && !ii.scrolling) {
                            mItems.remove(itemIndex);
                            mAdapter.destroyItem(this, pos, ii.object);
                            if (DEBUG) {
                                Log.i(TAG, "populate() - destroyItem() with pos: " + pos
                                        + " view: " + ((View) ii.object));
                            }
                            ii = itemIndex < mItems.size() ? mItems.get(itemIndex) : null;
                        }
                    } else if (ii != null && pos == ii.position) {
                        extraWidthRight += ii.widthFactor;
                        itemIndex++;
                        ii = itemIndex < mItems.size() ? mItems.get(itemIndex) : null;
                    } else {
                        ii = addNewItem(pos, itemIndex);
                        itemIndex++;
                        extraWidthRight += ii.widthFactor;
                        ii = itemIndex < mItems.size() ? mItems.get(itemIndex) : null;
                    }
                }
            }
            // 3.计算mItems中的偏移参数
            calculatePageOffsets(curItem, curIndex, oldCurInfo);

            //回调PagerAdapter的setPrimaryItem，告诉PagerAdapter当前显示的页面
            mAdapter.setPrimaryItem(this, mCurItem, curItem.object);
        }

        if (DEBUG) {
            Log.i(TAG, "Current page list:");
            for (int i = 0; i < mItems.size(); i++) {
                Log.i(TAG, "#" + i + ": page " + mItems.get(i).position);
            }
        }
        //回调PagerAdapter的finishUpdate，告诉PagerAdapter页面更新结束
        mAdapter.finishUpdate(this);

        // Check width measurement of current pages and drawing sort order.
        // Update LayoutParams as needed.
         // 将ItemInfo的内容更新到childView的LayoutParams中
         //检查页面的宽度是否测量，如果页面的LayoutParams数据没有设定，则去重新设定好
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            final LayoutParams lp = (LayoutParams) child.getLayoutParams();
            lp.childIndex = i;
            if (!lp.isDecor && lp.widthFactor == 0.f) {
                // 0 means requery the adapter for this, it doesn't have a valid width.
                final ItemInfo ii = infoForChild(child);
                if (ii != null) {
                    lp.widthFactor = ii.widthFactor;
                    lp.position = ii.position;
                }
            }
        }
        //根据lp.position的大小对所有childView进行排序，另外DecorView是排在其他child之前的
        //重新对页面排序
        sortChildDrawingOrder();

       //如果ViewPager被设定为可获焦的，则将当前显示的页面设定为获焦
        if (hasFocus()) {
            View currentFocused = findFocus();
            ItemInfo ii = currentFocused != null ? infoForAnyChild(currentFocused) : null;
            if (ii == null || ii.position != mCurItem) {
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    ii = infoForChild(child);
                    if (ii != null && ii.position == mCurItem) {
                        if (child.requestFocus(View.FOCUS_FORWARD)) {
                            break;
                        }
                    }
                }
            }
        }
    }

    private void sortChildDrawingOrder() {
        if (mDrawingOrder != DRAW_ORDER_DEFAULT) {
            if (mDrawingOrderedChildren == null) {
                mDrawingOrderedChildren = new ArrayList<View>();
            } else {
                mDrawingOrderedChildren.clear();
            }
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);
                mDrawingOrderedChildren.add(child);
            }
            Collections.sort(mDrawingOrderedChildren, sPositionComparator);
        }
    }

    private void calculatePageOffsets(ItemInfo curItem, int curIndex, ItemInfo oldCurInfo) {
        final int N = mAdapter.getCount();
        final int width = getClientWidth();
        final float marginOffset = width > 0 ? (float) mPageMargin / width : 0;
        // Fix up offsets for later layout.
        if (oldCurInfo != null) {
            final int oldCurPosition = oldCurInfo.position;
            // Base offsets off of oldCurInfo.
            if (oldCurPosition < curItem.position) {
                int itemIndex = 0;
                ItemInfo ii = null;
                float offset = oldCurInfo.offset + oldCurInfo.widthFactor + marginOffset;
                for (int pos = oldCurPosition + 1;
                        pos <= curItem.position && itemIndex < mItems.size(); pos++) {
                    ii = mItems.get(itemIndex);
                    while (pos > ii.position && itemIndex < mItems.size() - 1) {
                        itemIndex++;
                        ii = mItems.get(itemIndex);
                    }
                    while (pos < ii.position) {
                        // We don't have an item populated for this,
                        // ask the adapter for an offset.
                        offset += mAdapter.getPageWidth(pos) + marginOffset;
                        pos++;
                    }
                    ii.offset = offset;
                    offset += ii.widthFactor + marginOffset;
                }
            } else if (oldCurPosition > curItem.position) {
                int itemIndex = mItems.size() - 1;
                ItemInfo ii = null;
                float offset = oldCurInfo.offset;
                for (int pos = oldCurPosition - 1;
                        pos >= curItem.position && itemIndex >= 0; pos--) {
                    ii = mItems.get(itemIndex);
                    while (pos < ii.position && itemIndex > 0) {
                        itemIndex--;
                        ii = mItems.get(itemIndex);
                    }
                    while (pos > ii.position) {
                        // We don't have an item populated for this,
                        // ask the adapter for an offset.
                        offset -= mAdapter.getPageWidth(pos) + marginOffset;
                        pos--;
                    }
                    offset -= ii.widthFactor + marginOffset;
                    ii.offset = offset;
                }
            }
        }

        // Base all offsets off of curItem.
        final int itemCount = mItems.size();
        float offset = curItem.offset;
        int pos = curItem.position - 1;
        mFirstOffset = curItem.position == 0 ? curItem.offset : -Float.MAX_VALUE;
        mLastOffset = curItem.position == N - 1
                ? curItem.offset + curItem.widthFactor - 1 : Float.MAX_VALUE;
        // Previous pages
        for (int i = curIndex - 1; i >= 0; i--, pos--) {
            final ItemInfo ii = mItems.get(i);
            while (pos > ii.position) {
                offset -= mAdapter.getPageWidth(pos--) + marginOffset;
            }
            offset -= ii.widthFactor + marginOffset;
            ii.offset = offset;
            if (ii.position == 0) mFirstOffset = offset;
        }
        offset = curItem.offset + curItem.widthFactor + marginOffset;
        pos = curItem.position + 1;
        // Next pages
        for (int i = curIndex + 1; i < itemCount; i++, pos++) {
            final ItemInfo ii = mItems.get(i);
            while (pos < ii.position) {
                offset += mAdapter.getPageWidth(pos++) + marginOffset;
            }
            if (ii.position == N - 1) {
                mLastOffset = offset + ii.widthFactor - 1;
            }
            ii.offset = offset;
            offset += ii.widthFactor + marginOffset;
        }

        mNeedCalculatePageOffsets = false;
    }

    /**
     * This is the persistent state that is saved by ViewPager.  Only needed
     * if you are creating a sublass of ViewPager that must save its own
     * state, in which case it should implement a subclass of this which
     * contains that state.
     */
    public static class SavedState extends AbsSavedState {
        int position;
        Parcelable adapterState;
        ClassLoader loader;

        public SavedState(@NonNull Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(position);
            out.writeParcelable(adapterState, flags);
        }

        @Override
        public String toString() {
            return "FragmentPager.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " position=" + position + "}";
        }

        public static final Creator<SavedState> CREATOR = new ClassLoaderCreator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, null);
            }
            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        SavedState(Parcel in, ClassLoader loader) {
            super(in, loader);
            if (loader == null) {
                loader = getClass().getClassLoader();
            }
            position = in.readInt();
            adapterState = in.readParcelable(loader);
            this.loader = loader;
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.position = mCurItem;
        if (mAdapter != null) {
            ss.adapterState = mAdapter.saveState();
        }
        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        if (mAdapter != null) {
            mAdapter.restoreState(ss.adapterState, ss.loader);
            setCurrentItemInternal(ss.position, false, true);
        } else {
            mRestoredCurItem = ss.position;
            mRestoredAdapterState = ss.adapterState;
            mRestoredClassLoader = ss.loader;
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (!checkLayoutParams(params)) {
            params = generateLayoutParams(params);
        }
        final LayoutParams lp = (LayoutParams) params;
        // Any views added via inflation should be classed as part of the decor
        lp.isDecor |= isDecorView(child);
        if (mInLayout) {
            if (lp != null && lp.isDecor) {
                throw new IllegalStateException("Cannot add pager decor view during layout");
            }
            lp.needsMeasure = true;
            addViewInLayout(child, index, params);
        } else {
            super.addView(child, index, params);
        }

        if (USE_CACHE) {
            if (child.getVisibility() != GONE) {
                child.setDrawingCacheEnabled(mScrollingCacheEnabled);
            } else {
                child.setDrawingCacheEnabled(false);
            }
        }
    }

    private static boolean isDecorView(@NonNull View view) {
        Class<?> clazz = view.getClass();
        return clazz.getAnnotation(DecorView.class) != null;
    }

    @Override
    public void removeView(View view) {
        if (mInLayout) {
            removeViewInLayout(view);
        } else {
            super.removeView(view);
        }
    }

    ItemInfo infoForChild(View child) {
        for (int i = 0; i < mItems.size(); i++) {
            ItemInfo ii = mItems.get(i);
            if (mAdapter.isViewFromObject(child, ii.object)) {
                return ii;
            }
        }
        return null;
    }

    ItemInfo infoForAnyChild(View child) {
        ViewParent parent;
        while ((parent = child.getParent()) != this) {
            if (parent == null || !(parent instanceof View)) {
                return null;
            }
            child = (View) parent;
        }
        return infoForChild(child);
    }

    ItemInfo infoForPosition(int position) {
        for (int i = 0; i < mItems.size(); i++) {
            ItemInfo ii = mItems.get(i);
            if (ii.position == position) {
                return ii;
            }
        }
        return null;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mFirstLayout = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // For simple implementation, our internal size is always 0.
        // We depend on the container to specify the layout size of
        // our view.  We can't really know what it is since we will be
        // adding and removing different arbitrary views and do not
        // want the layout to change as this happens.
        //测量ViewPager自身大小,根据布局文件，设置尺寸信息，默认大小为0
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));

        final int measuredWidth = getMeasuredWidth();
        final int maxGutterSize = measuredWidth / 10;
        //设置mGutterSize的值，后面再讲mGutterSize
        mGutterSize = Math.min(maxGutterSize, mDefaultGutterSize);

        // Children are just made to fill our space.
        //ViewPager的显示区域只能显示对于一个View
        //childWidthSize和childHeightSize为一个View的可用宽高大小
        //即去除了ViewPager内边距后的宽高
        // child的宽高，占满父控件
        int childWidthSize = measuredWidth - getPaddingLeft() - getPaddingRight();
        int childHeightSize = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();

        /*
         * Make sure all children have been properly measured. Decor views first.
         * Right now we cheat and make this less complicated by assuming decor
         * views won't intersect. We will pin to edges based on gravity.
         */
        //1.先对Decor View进行测量
        //下面这个循环是只针对Decor View的，即用于装饰ViewPager的View
        int size = getChildCount();
        for (int i = 0; i < size; ++i) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                //1.1 如果该View是Decor View,即用于装饰ViewPager的View
                if (lp != null && lp.isDecor) {
                    //1.2 获取Decor View的在水平方向和竖直方向上的Gravity
                    final int hgrav = lp.gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
                    final int vgrav = lp.gravity & Gravity.VERTICAL_GRAVITY_MASK;
                    //1.3 默认Dedor View模式对应的宽高是wrap_content
                    int widthMode = MeasureSpec.AT_MOST;
                    int heightMode = MeasureSpec.AT_MOST;
                    //1.4 记录Decor View是在垂直方向上还是在水平方向上占用空间
                    boolean consumeVertical = vgrav == Gravity.TOP || vgrav == Gravity.BOTTOM;
                    boolean consumeHorizontal = hgrav == Gravity.LEFT || hgrav == Gravity.RIGHT;
                    //1.5 consumeHorizontal：如果是在垂直方向上占用空间，
                    // 那么水平方向就是match_parent，即EXACTLY
                    //而垂直方向上具体占用多少空间，还得由Decor View决定
                    //consumeHorizontal也是同理
                    if (consumeVertical) {
                        widthMode = MeasureSpec.EXACTLY;
                    } else if (consumeHorizontal) {
                        heightMode = MeasureSpec.EXACTLY;
                    }
                    //1.6 宽高大小，初始化为ViewPager可视区域中子View可用空间
                    int widthSize = childWidthSize;
                    int heightSize = childHeightSize;
                    //1.7 如果宽度不是wrap_content，那么width的测量模式就是EXACTLY
                    //如果宽度既不是wrap_content又不是match_parent，那么说明是用户
                    //在布局文件写的具体的尺寸，直接将widthSize设置为这个具体尺寸
                    if (lp.width != LayoutParams.WRAP_CONTENT) {
                        widthMode = MeasureSpec.EXACTLY;
                        if (lp.width != LayoutParams.MATCH_PARENT) {
                            widthSize = lp.width;
                        }
                    }
                    //1.8 同1.7
                    if (lp.height != LayoutParams.WRAP_CONTENT) {
                        heightMode = MeasureSpec.EXACTLY;
                        if (lp.height != LayoutParams.MATCH_PARENT) {
                            heightSize = lp.height;
                        }
                    }
                    //1.9 合成Decor View的宽高specification（包含尺寸和模式的整数）
                    final int widthSpec = MeasureSpec.makeMeasureSpec(widthSize, widthMode);
                    final int heightSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
                    //1.10 对DecorView进行测量
                    child.measure(widthSpec, heightSpec);
                    //1.11 如果Decor View占用了ViewPager的垂直方向的空间
                    //需要将子View的竖直方向可用的空间减去DecorView的高度，
                    //同理，水平方向上也做同样的处理
                    if (consumeVertical) {
                        childHeightSize -= child.getMeasuredHeight();
                    } else if (consumeHorizontal) {
                        childWidthSize -= child.getMeasuredWidth();
                    }
                }
            }
        } 
        //2. 子View默认宽高的specification（包含尺寸和模式的整数）
        mChildWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        mChildHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);

        // Make sure we have created all fragments that we need to have shown.
        //3.确保我们需要显示的fragment已经被我们创建好了
        mInLayout = true;
        populate();
        mInLayout = false;

        // Page views next.
        // 4.再对子View进行测量
        size = getChildCount();
        for (int i = 0; i < size; ++i) {
            final View child = getChildAt(i);
            //4.1 visibility为GONE的无需测量
            if (child.getVisibility() != GONE) {
                if (DEBUG) {
                    Log.v(TAG, "Measuring #" + i + " " + child + ": " + mChildWidthMeasureSpec);
                }
                //4.2 获取子View的LayoutParams
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                //4.3 只针对子View而不对Decor View测量
                if (lp == null || !lp.isDecor) {
                    //4.4 LayoutParams的widthFactor是取值为[0,1]的浮点数，
                    // 用于表示子View占ViewPager显示区域中子View可用宽度的比例，
                    // 即(childWidthSize * lp.widthFactor)表示当前子View的实际宽度
                    final int widthSpec = MeasureSpec.makeMeasureSpec(
                            (int) (childWidthSize * lp.widthFactor), MeasureSpec.EXACTLY);
                    //4.5 对当前子View进行测量
                    child.measure(widthSpec, mChildHeightMeasureSpec);
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Make sure scroll position is set correctly.
        if (w != oldw) {
            recomputeScrollPosition(w, oldw, mPageMargin, mPageMargin);
        }
    }

    private void recomputeScrollPosition(int width, int oldWidth, int margin, int oldMargin) {
        if (oldWidth > 0 && !mItems.isEmpty()) {
            if (!mScroller.isFinished()) {
                mScroller.setFinalX(getCurrentItem() * getClientWidth());
            } else {
                final int widthWithMargin = width - getPaddingLeft() - getPaddingRight() + margin;
                final int oldWidthWithMargin = oldWidth - getPaddingLeft() - getPaddingRight()
                        + oldMargin;
                final int xpos = getScrollX();
                final float pageOffset = (float) xpos / oldWidthWithMargin;
                final int newOffsetPixels = (int) (pageOffset * widthWithMargin);

                scrollTo(newOffsetPixels, getScrollY());
            }
        } else {
            final ItemInfo ii = infoForPosition(mCurItem);
            final float scrollOffset = ii != null ? Math.min(ii.offset, mLastOffset) : 0;
            final int scrollPos =
                    (int) (scrollOffset * (width - getPaddingLeft() - getPaddingRight()));
            if (scrollPos != getScrollX()) {
                completeScroll(false);
                scrollTo(scrollPos, getScrollY());
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //1.以下局部变量很简单，不再解释
        final int count = getChildCount();
        int width = r - l;
        int height = b - t;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        final int scrollX = getScrollX();
        //2.Decor View 数量
        int decorCount = 0;

        // First pass - decor views. We need to do this in two passes so that
        // we have the proper offsets for non-decor views later.
        //3.首先对Decor View进行layout，再对普通子View进行layout，
        // 之所以先对Decor View布局，是为了让普通子View能有合适的偏移
        //下面循环主要是针对Decor View
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            //3.1 visibility不为GONE才layout
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                 //3.2 左边和顶部的边距初始化为0
                int childLeft = 0;
                int childTop = 0;
                
                if (lp.isDecor) {//3.3 只针对Decor View
                    //3.4 获取水平或垂直方向上的Gravity
                    final int hgrav = lp.gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
                    final int vgrav = lp.gravity & Gravity.VERTICAL_GRAVITY_MASK;
                     //3.5 根据水平方向上的Gravity，确定childLeft以及paddingRight
                    switch (hgrav) {
                        default://没有设置水平方向Gravity时（左中右），childLeft就取paddingLeft
                            childLeft = paddingLeft;
                            break;
                        case Gravity.LEFT://水平方向Gravity为left，Decor View往最左边靠
                            childLeft = paddingLeft;
                            paddingLeft += child.getMeasuredWidth();
                            break;
                        case Gravity.CENTER_HORIZONTAL://将Decor View居中摆放
                            childLeft = Math.max((width - child.getMeasuredWidth()) / 2,
                                    paddingLeft);
                            break;
                        case Gravity.RIGHT://将Decor View往最右边靠
                            childLeft = width - paddingRight - child.getMeasuredWidth();
                            paddingRight += child.getMeasuredWidth();
                            break;
                    }
                    //3.6 与3.5同理
                    switch (vgrav) {
                        default:
                            childTop = paddingTop;
                            break;
                        case Gravity.TOP:
                            childTop = paddingTop;
                            paddingTop += child.getMeasuredHeight();
                            break;
                        case Gravity.CENTER_VERTICAL:
                            childTop = Math.max((height - child.getMeasuredHeight()) / 2,
                                    paddingTop);
                            break;
                        case Gravity.BOTTOM:
                            childTop = height - paddingBottom - child.getMeasuredHeight();
                            paddingBottom += child.getMeasuredHeight();
                            break;
                    }
                    //3.7 上面计算的childLeft是相对ViewPager的左边计算的，
                    //还需要加上x方向已经滑动的距离scrollX
                    childLeft += scrollX;
                    //3.8 对Decor View布局
                    child.layout(childLeft, childTop,
                            childLeft + child.getMeasuredWidth(),
                            childTop + child.getMeasuredHeight());
                    //3.9 将Decor View数量+1
                    decorCount++;
                }
            }
        }

        //4.普通子View的宽度
        final int childWidth = width - paddingLeft - paddingRight;
        // Page views. Do this once we have the right padding offsets from above.
        //5.下面针对普通子View布局，在此之前我们已经得到正确的偏移量了
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                //5.1 ItemInfo 是ViewPager静态内部类，
                // 它保存了普通子View的position、offset等信息，是对普通子View的一个抽象描述
                ItemInfo ii;
                //此处将DecorView过滤掉，并且根据view从mItems中查找ItemInfo对象
                //如果ViewPager布局中添加了未实现Decor接口的控件，将不会被布局
                //因为无法从mItems中查找到ItemInfo对象
                //5.2 infoForChild通过传入View查询对应的ItemInfo对象
                if (!lp.isDecor && (ii = infoForChild(child)) != null) {
                     //计算当前page的左边界偏移值，此处的offset会随着页面增加而增加
                    //在用ViewPager实现轮播控件时，有一种方法是将Adapter.getCount返回Integer.MAX_VALUE，已达到伪循环播放的目的。从上面的代码可以看到，此时这个offset值会不断的变大，那么
                    //这个loff很可能会超出int的最大值边界。 
                    //所以，以后大家实现轮播控件时，还是不要采用这种方法了。
                    //计算当前子View的左边偏移量
                    int loff = (int) (childWidth * ii.offset);
                    //将左边距+左边偏移量得到最终子View左边位置
                    int childLeft = paddingLeft + loff;
                    int childTop = paddingTop;
                    //5.3 如果当前子View需要进行测量（measure），当这个子View是在Layout期间新添加新的，
                   // 那么这个子View需要进行测量，即needsMeasure为true
                    if (lp.needsMeasure) {//如果需要重新测量，则重新测量之
                        // This was added during layout and needs measurement.
                        // Do it now that we know what we're working with.
                        //5.4 标记已经测量过了
                        lp.needsMeasure = false;
                        final int widthSpec = MeasureSpec.makeMeasureSpec(
                                (int) (childWidth * lp.widthFactor),
                                MeasureSpec.EXACTLY);
                        final int heightSpec = MeasureSpec.makeMeasureSpec(
                                (int) (height - paddingTop - paddingBottom),
                                MeasureSpec.EXACTLY);
                        child.measure(widthSpec, heightSpec);
                    }
                    if (DEBUG) {
                        Log.v(TAG, "Positioning #" + i + " " + child + " f=" + ii.object
                                + ":" + childLeft + "," + childTop + " " + child.getMeasuredWidth()
                                + "x" + child.getMeasuredHeight());
                    }
                    //child调用自己的layout方法来布局自己
                    //5.6 对普通子View进行layout
                    child.layout(childLeft, childTop,
                            childLeft + child.getMeasuredWidth(),
                            childTop + child.getMeasuredHeight());
                }
            }
        }
        //6. 将部分局部变量保存到实例变量中
        mTopPageBounds = paddingTop;
        mBottomPageBounds = height - paddingBottom;
        mDecorChildCount = decorCount;
        //7 如果是首次布局，则会调用scrollToItem方法 如果是第一次layout，则将ViewPager滑动到第一个子View的位置
        if (mFirstLayout) {
            scrollToItem(mCurItem, false, 0, false);
        }
        //8. 标记已经布局过了，即不再是第一次布局了
        mFirstLayout = false;
    }

    //2 重写computeScroll()方法
    @Override
    public void computeScroll() {
        //1.mIsScrollStarted标记当前在滑动
        mIsScrollStarted = true;
        //2.确保mScroller还没有结束计算滑动位置
        if (!mScroller.isFinished() && mScroller.computeScrollOffset()) {
            //3.保存当前所处的位置oldX,oldY
            int oldX = getScrollX();
            int oldY = getScrollY();
             //4.取出由mScroller计算出来的位置
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            //5.只要x和y方向有一个发生了变化，就去滚动
            if (oldX != x || oldY != y) {
                //6.滑到mScroller计算出来的新位置
                scrollTo(x, y);
                if (!pageScrolled(x)) {
                    //8.结束动画，并使得当前位置处于最终的位置
                    mScroller.abortAnimation();
                    //9.没有子View，说明x方向无需滑动，再次确保y方向滑动
                    scrollTo(0, y);
                }
            }

            // Keep on drawing until the animation has finished.
            // 10.不断的postInvalidate,使得不断重绘，达到动画效果
            ViewCompat.postInvalidateOnAnimation(this);
            return;
        }

        // Done with scroll, clean up state.
        //11.做一些滑动结束后的相关操作
        // 注意到，上面的if里面有个return，也就是说，
        // 只要是在滑动，就不会执行到下面的代码，
        // 反之，执行到下面代码就说明已经滑动结束 
        completeScroll(true);
    }

    //主要的作用是回调onPageScrolled，虽然做了很多计算，
    //但这些计算的结果最终是为了作为形参传给onPageScrolled
    private boolean pageScrolled(int xpos) {
        //1.mItems是ArrayList类型，它保存的是每个子View的抽象描述类ItemInfo
        //如果没有子View
        if (mItems.size() == 0) {
            if (mFirstLayout) {
                // If we haven't been laid out yet, we probably just haven't been populated yet.
                // Let's skip this call since it doesn't make sense in this state
                return false;
            }
            //2.先认为没有调用父类
            //mCalledSuper作用是：如果子类重写了onPageScrolled，
            // 那么子类的实现必须要先调用父类ViewPager的onPageScrolled
            //为了确保子类的实现中先调用了父类ViewPager的onPageScrolled，定义了mCalledSuper
            //并且在ViewPager类中的onPageScrolled将mCalledSuper设置为了true，用于判断子类有没有调用。
            mCalledSuper = false;
            //3.调用onPageScrolled，如果子类重写了该方法，调用的则是子类的onPageScrolled
            onPageScrolled(0, 0, 0);
            //4.如果没有执行ViewPager的onPageScrolled,抛出异常
            if (!mCalledSuper) {
                throw new IllegalStateException(
                        "onPageScrolled did not call superclass implementation");
            }
             //5.如果没有子View，返回false
            return false;
        }
        //6.根据当前滑动的位置，得到当前显示的子View的抽象描述类ItemInfo
        //只要存在子View，得到的ItemInfo对象肯定不为null
        final ItemInfo ii = infoForCurrentScrollPosition();
        //7.获取显示区域的宽度
        final int width = getClientWidth();
        //8.加上外边距后的宽度
        final int widthWithMargin = width + mPageMargin;
        final float marginOffset = (float) mPageMargin / width;
        //保存当前是第几个页面（即第几个子View）
        final int currentPage = ii.position;
        //计算当前页面的偏移量，取值为[0,1),如果pageOffset不等于0，则下一个页面可见
        final float pageOffset = (((float) xpos / width) - ii.offset)
                / (ii.widthFactor + marginOffset);
        //当前页面移动的像素点个数
        final int offsetPixels = (int) (pageOffset * widthWithMargin);
        //以下作用与2、3、4类似
        mCalledSuper = false;
        onPageScrolled(currentPage, pageOffset, offsetPixels);
        if (!mCalledSuper) {
            throw new IllegalStateException(
                    "onPageScrolled did not call superclass implementation");
        }
        return true;
    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     * If you override this method you must call through to the superclass implementation
     * (e.g. super.onPageScrolled(position, offset, offsetPixels)) before onPageScrolled
     * returns.
     *
     * @param position Position index of the first page currently being displayed.
     *                 Page position+1 will be visible if positionOffset is nonzero.
     * @param offset Value from [0, 1) indicating the offset from the page at position.
     * @param offsetPixels Value in pixels indicating the offset from position.
     */
    /**
    * pageScrolled函数是为了调用onPageScrolled做前期计算，并将计算结果作为onPageScrolled的形参，
    * 最终是为了回调onPageScrolled函数，那么我们看看onPageScrolled函数到底是干了啥~，
    * 从函数名看的出来，它是一个回调函数，那么是什么情况下回调呢？其实，
    * 在我们手指滑动或者是通过代码直接滑动到指定位置过程中，会使得一些页面滑动，
    * 如果我们想要在每个页面在显示区域滑动过程中实现某些效果，可以重写这个函数，
    * 当然了，我们前面分析pageScrolled函数时就提到，重写onPageScrolled时，
    * 必须先调用super.onPageScrolled(position, offset, offsetPixels)，
    * 我们的ViewPager在滑动过程中，会不断回调onPageScrolled函数，
    * 这个“不断”是从这里体现：computeScroll—>onPageScrolled->onPageScrolled。
    * 滑动过程不断调用computeScroll，而computeScroll调用onPageScrolled，onPageScrolled又调用onPageScrolled
    * int position，表示当前是第几个页面
    * float offset表示当前页面移动的距离，其实就是个相对实际宽度比例值，取值为[0,1)。
    * 0表示整个页面在显示区域，1表示整个页面已经完全左移出显示区域。
    * int offsetPixels , 表示当前页面左移的像素个数
    */
    @CallSuper
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        // Offset any decor views if needed - keep them on-screen at all times.
        //1.如果有Decor View，则需要使得它们时刻显示在屏幕中，不移出屏幕
        if (mDecorChildCount > 0) {
            //根据Gravity将Decor View摆放到指定位置，注释略，可以参考上一篇文章
            final int scrollX = getScrollX();
            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            final int width = getWidth();
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (!lp.isDecor) continue;

                final int hgrav = lp.gravity & Gravity.HORIZONTAL_GRAVITY_MASK;
                int childLeft = 0;
                switch (hgrav) {
                    default:
                        childLeft = paddingLeft;
                        break;
                    case Gravity.LEFT:
                        childLeft = paddingLeft;
                        paddingLeft += child.getWidth();
                        break;
                    case Gravity.CENTER_HORIZONTAL:
                        childLeft = Math.max((width - child.getMeasuredWidth()) / 2,
                                paddingLeft);
                        break;
                    case Gravity.RIGHT:
                        childLeft = width - paddingRight - child.getMeasuredWidth();
                        paddingRight += child.getMeasuredWidth();
                        break;
                }
                childLeft += scrollX;

                final int childOffset = childLeft - child.getLeft();
                if (childOffset != 0) {
                    child.offsetLeftAndRight(childOffset);
                }
            }
        }

        //2.分发页面滚动事件
        dispatchOnPageScrolled(position, offset, offsetPixels);

         //3.如果mPageTransformer不为null，则不断去调用mPageTransformer的transformPage函数
        if (mPageTransformer != null) {
            final int scrollX = getScrollX();
            final int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = getChildAt(i);
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
                //只针对页面进行处理
                if (lp.isDecor) continue;
                final float transformPos = (float) (child.getLeft() - scrollX) / getClientWidth();
                 //调用transformPage
                mPageTransformer.transformPage(child, transformPos);
            }
        }
        //标记ViewPager的onPageScrolled函数执行过
        mCalledSuper = true;
    }

    private void dispatchOnPageScrolled(int position, float offset, int offsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, offset, offsetPixels);
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageScrolled(position, offset, offsetPixels);
                }
            }
        }
        if (mInternalPageChangeListener != null) {
            mInternalPageChangeListener.onPageScrolled(position, offset, offsetPixels);
        }
    }

    private void dispatchOnPageSelected(int position) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageSelected(position);
                }
            }
        }
        if (mInternalPageChangeListener != null) {
            mInternalPageChangeListener.onPageSelected(position);
        }
    }

    private void dispatchOnScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageScrollStateChanged(state);
                }
            }
        }
        if (mInternalPageChangeListener != null) {
            mInternalPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    //确保滚动已经到最终位置，如果没有到最终位置，则滚动到最终位置。
    //还有就是将每个页面对应的ItemInfo对象的scrolling设为false
    private void completeScroll(boolean postEvents) {
        boolean needPopulate = mScrollState == SCROLL_STATE_SETTLING;
        if (needPopulate) {
            // Done with scroll, no longer want to cache view drawing.
            setScrollingCacheEnabled(false);
            boolean wasScrolling = !mScroller.isFinished();
            if (wasScrolling) {
                mScroller.abortAnimation();
                int oldX = getScrollX();
                int oldY = getScrollY();
                int x = mScroller.getCurrX();
                int y = mScroller.getCurrY();
                if (oldX != x || oldY != y) {
                    scrollTo(x, y);
                    if (x != oldX) {
                        pageScrolled(x);
                    }
                }
            }
        }
        mPopulatePending = false;
        for (int i = 0; i < mItems.size(); i++) {
            ItemInfo ii = mItems.get(i);
            if (ii.scrolling) {
                needPopulate = true;
                ii.scrolling = false;
            }
        }
        if (needPopulate) {
            if (postEvents) {
                ViewCompat.postOnAnimation(this, mEndScrollRunnable);
            } else {
                mEndScrollRunnable.run();
            }
        }
    }

    private boolean isGutterDrag(float x, float dx) {
        return (x < mGutterSize && dx > 0) || (x > getWidth() - mGutterSize && dx < 0);
    }

    private void enableLayers(boolean enable) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int layerType = enable
                    ? mPageTransformerLayerType : View.LAYER_TYPE_NONE;
            getChildAt(i).setLayerType(layerType, null);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        /*
         * This method JUST determines whether we want to intercept the motion.
         * If we return true, onMotionEvent will be called and we do the actual
         * scrolling there.
         */
        //1. 触摸动作
        final int action = ev.getAction() & MotionEvent.ACTION_MASK;

        // Always take care of the touch gesture being complete.
        //2. 时刻要注意触摸是否已经结束
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            // Release the drag.
            if (DEBUG) Log.v(TAG, "Intercept done!");
            //4. 重置一些跟判断是否拦截触摸相关变量
            resetTouch();
            //5. 触摸结束，无需拦截
            return false;
        }

        // Nothing more to do here if we have decided whether or not we
        // are dragging.
        //6. 如果当前不是按下事件，我们就判断一下，是否是在拖拽切换页面
        if (action != MotionEvent.ACTION_DOWN) {
            //7. 如果当前是正在拽切换页面，直接拦截掉事件，后面无需再做拦截判断
            if (mIsBeingDragged) {
                if (DEBUG) Log.v(TAG, "Intercept returning true!");
                return true;
            }
            //8. 如果标记为不允许拖拽切换页面，我们就"放过"一切触摸事件
            if (mIsUnableToDrag) {
                if (DEBUG) Log.v(TAG, "Intercept returning false!");
                return false;
            }
        }

        //9. 根据不同的动作进行处理
        switch (action) {
            case MotionEvent.ACTION_MOVE: {//10. 如果是手指移动操作
                /*
                 * mIsBeingDragged == false, otherwise the shortcut would have caught it. Check
                 * whether the user has moved far enough from his original down touch.
                 */

                /*
                * Locally do absolute value. mLastMotionY is set to the y value
                * of the down event.
                */
                 //11. 代码能执行到这里，就说明mIsBeingDragged==false，否则的话，在第7个注释处就已经执行结束了
                //12.使用触摸点Id，主要是为了处理多点触摸
                final int activePointerId = mActivePointerId;
                if (activePointerId == INVALID_POINTER) {
                    //13.如果当前的触摸点id不是一个有效的Id，无需再做处理
                    // If we don't have a valid id, the touch down wasn't on content.
                    break;
                }

                //14.根据触摸点的id来区分不同的手指，我们只需关注一个手指就好
                final int pointerIndex = ev.findPointerIndex(activePointerId);
                //16.在x轴方向上移动的距离
                final float x = ev.getX(pointerIndex);
                //17.x轴方向的移动距离绝对值
                final float dx = x - mLastMotionX;
                final float xDiff = Math.abs(dx);
                //18.同理，参照16、17条注释
                final float y = ev.getY(pointerIndex);
                final float yDiff = Math.abs(y - mInitialMotionY);
                if (DEBUG) Log.v(TAG, "Moved x to " + x + "," + y + " diff=" + xDiff + "," + yDiff);

                //19.判断当前显示的页面是否可以滑动，如果可以滑动，则将该事件丢给当前显示的页面处理
                //isGutterDrag是判断是否在两个页面之间的缝隙内移动
                //canScroll是判断页面是否可以滑动
                if (dx != 0 && !isGutterDrag(mLastMotionX, dx)
                        && canScroll(this, false, (int) dx, (int) x, (int) y)) {
                    // Nested view has scrollable area under this point. Let it be handled there.
                    mLastMotionX = x;
                    mLastMotionY = y;
                    //20.标记ViewPager不去拦截事件
                    mIsUnableToDrag = true;
                    return false;
                }
                //21.如果x移动距离大于最小距离，并且斜率小于0.5，表示在水平方向上的拖动
                if (xDiff > mTouchSlop && xDiff * 0.5f > yDiff) {
                    if (DEBUG) Log.v(TAG, "Starting drag!");
                    //22.水平方向的移动，需要ViewPager去拦截
                    mIsBeingDragged = true;
                    //23.如果ViewPager还有父View，则还要向父View申请将触摸事件传递给ViewPager
                    requestParentDisallowInterceptTouchEvent(true);
                    //24.设置滚动状态
                    setScrollState(SCROLL_STATE_DRAGGING);
                    //25.保存当前位置
                    mLastMotionX = dx > 0
                            ? mInitialMotionX + mTouchSlop : mInitialMotionX - mTouchSlop;
                    mLastMotionY = y;
                     //26.启用缓存
                    setScrollingCacheEnabled(true);
                } else if (yDiff > mTouchSlop) {//27.否则的话，表示是竖直方向上的移动
                    // The finger has moved enough in the vertical
                    // direction to be counted as a drag...  abort
                    // any attempt to drag horizontally, to work correctly
                    // with children that have scrolling containers.
                    if (DEBUG) Log.v(TAG, "Starting unable to drag!");
                    //28.竖直方向上的移动则不去拦截触摸事件
                    mIsUnableToDrag = true;
                }
                if (mIsBeingDragged) {
                    // Scroll to follow the motion event
                    // 29.跟随手指一起滑动
                    if (performDrag(x)) {
                        ViewCompat.postInvalidateOnAnimation(this);
                    }
                }
                break;
            }
            //30.如果手指是按下操作
            case MotionEvent.ACTION_DOWN: {
                /*
                 * Remember location of down touch.
                 * ACTION_DOWN always refers to pointer index 0.
                 */
                //31.记录按下的点位置
                mLastMotionX = mInitialMotionX = ev.getX();
                mLastMotionY = mInitialMotionY = ev.getY();
                //32.第一个ACTION_DOWN事件对应的手指序号为0
                mActivePointerId = ev.getPointerId(0);
                //33.重置允许拖拽切换页面
                mIsUnableToDrag = false;
                 //34.标记开始滚动
                mIsScrollStarted = true;
                //35.手动调用计算滑动的偏移量
                mScroller.computeScrollOffset();
                 //36.如果当前滚动状态为正在将页面放置到最终位置，
                //且当前位置距离最终位置足够远
                if (mScrollState == SCROLL_STATE_SETTLING
                        && Math.abs(mScroller.getFinalX() - mScroller.getCurrX()) > mCloseEnough) {
                    // Let the user 'catch' the pager as it animates.
                    //37. 如果此时用户手指按下，则立马暂停滑动
                    mScroller.abortAnimation();
                    mPopulatePending = false;
                    populate();
                    mIsBeingDragged = true;
                    //38.如果ViewPager还有父View，则还要向父View申请将触摸事件传递给ViewPager
                    requestParentDisallowInterceptTouchEvent(true);
                    setScrollState(SCROLL_STATE_DRAGGING);
                } else {
                    //40.结束滚动
                    completeScroll(false);
                    mIsBeingDragged = false;
                }

                if (DEBUG) {
                    Log.v(TAG, "Down at " + mLastMotionX + "," + mLastMotionY
                            + " mIsBeingDragged=" + mIsBeingDragged
                            + "mIsUnableToDrag=" + mIsUnableToDrag);
                }
                break;
            }

            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
        }

        //41.添加速度追踪
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        /*
         * The only time we want to intercept motion events is if we are in the
         * drag mode.
         */
        //42.只有在当前是拖拽切换页面时我们才会去拦截事件
        return mIsBeingDragged;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mFakeDragging) {
            // A fake drag is in progress already, ignore this real one
            // but still eat the touch events.
            // (It is likely that the user is multi-touching the screen.)
            return true;
        }

        if (ev.getAction() == MotionEvent.ACTION_DOWN && ev.getEdgeFlags() != 0) {
            // Don't handle edge touches immediately -- they may actually belong to one of our
            // descendants.
            return false;
        }

        if (mAdapter == null || mAdapter.getCount() == 0) {
            // Nothing to present or scroll; nothing to touch.
            return false;
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);

        final int action = ev.getAction();
        boolean needsInvalidate = false;

        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mScroller.abortAnimation();
                mPopulatePending = false;
                populate();

                // Remember where the motion event started
                mLastMotionX = mInitialMotionX = ev.getX();
                mLastMotionY = mInitialMotionY = ev.getY();
                mActivePointerId = ev.getPointerId(0);
                break;
            }
            case MotionEvent.ACTION_MOVE:
                if (!mIsBeingDragged) {
                    final int pointerIndex = ev.findPointerIndex(mActivePointerId);
                    if (pointerIndex == -1) {
                        // A child has consumed some touch events and put us into an inconsistent
                        // state.
                        needsInvalidate = resetTouch();
                        break;
                    }
                    final float x = ev.getX(pointerIndex);
                    final float xDiff = Math.abs(x - mLastMotionX);
                    final float y = ev.getY(pointerIndex);
                    final float yDiff = Math.abs(y - mLastMotionY);
                    if (DEBUG) {
                        Log.v(TAG, "Moved x to " + x + "," + y + " diff=" + xDiff + "," + yDiff);
                    }
                    if (xDiff > mTouchSlop && xDiff > yDiff) {
                        if (DEBUG) Log.v(TAG, "Starting drag!");
                        mIsBeingDragged = true;
                        requestParentDisallowInterceptTouchEvent(true);
                        mLastMotionX = x - mInitialMotionX > 0 ? mInitialMotionX + mTouchSlop :
                                mInitialMotionX - mTouchSlop;
                        mLastMotionY = y;
                        setScrollState(SCROLL_STATE_DRAGGING);
                        setScrollingCacheEnabled(true);

                        // Disallow Parent Intercept, just in case
                        ViewParent parent = getParent();
                        if (parent != null) {
                            parent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
                // Not else! Note that mIsBeingDragged can be set above.
                if (mIsBeingDragged) {
                    // Scroll to follow the motion event
                    final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
                    final float x = ev.getX(activePointerIndex);
                    needsInvalidate |= performDrag(x);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsBeingDragged) {
                    final VelocityTracker velocityTracker = mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                    int initialVelocity = (int) velocityTracker.getXVelocity(mActivePointerId);
                    mPopulatePending = true;
                    final int width = getClientWidth();
                    final int scrollX = getScrollX();
                    final ItemInfo ii = infoForCurrentScrollPosition();
                    final float marginOffset = (float) mPageMargin / width;
                    final int currentPage = ii.position;
                    final float pageOffset = (((float) scrollX / width) - ii.offset)
                            / (ii.widthFactor + marginOffset);
                    final int activePointerIndex = ev.findPointerIndex(mActivePointerId);
                    final float x = ev.getX(activePointerIndex);
                    final int totalDelta = (int) (x - mInitialMotionX);
                    int nextPage = determineTargetPage(currentPage, pageOffset, initialVelocity,
                            totalDelta);
                    setCurrentItemInternal(nextPage, true, true, initialVelocity);

                    needsInvalidate = resetTouch();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mIsBeingDragged) {
                    scrollToItem(mCurItem, true, 0, false);
                    needsInvalidate = resetTouch();
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                final int index = ev.getActionIndex();
                final float x = ev.getX(index);
                mLastMotionX = x;
                mActivePointerId = ev.getPointerId(index);
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                mLastMotionX = ev.getX(ev.findPointerIndex(mActivePointerId));
                break;
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
        return true;
    }

    private boolean resetTouch() {
        boolean needsInvalidate;
        mActivePointerId = INVALID_POINTER;
        endDrag();
        mLeftEdge.onRelease();
        mRightEdge.onRelease();
        needsInvalidate = mLeftEdge.isFinished() || mRightEdge.isFinished();
        return needsInvalidate;
    }

    private void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        final ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

    private boolean performDrag(float x) {
        boolean needsInvalidate = false;

        final float deltaX = mLastMotionX - x;
        mLastMotionX = x;

        float oldScrollX = getScrollX();
        float scrollX = oldScrollX + deltaX;
        final int width = getClientWidth();

        float leftBound = width * mFirstOffset;
        float rightBound = width * mLastOffset;
        boolean leftAbsolute = true;
        boolean rightAbsolute = true;

        final ItemInfo firstItem = mItems.get(0);
        final ItemInfo lastItem = mItems.get(mItems.size() - 1);
        if (firstItem.position != 0) {
            leftAbsolute = false;
            leftBound = firstItem.offset * width;
        }
        if (lastItem.position != mAdapter.getCount() - 1) {
            rightAbsolute = false;
            rightBound = lastItem.offset * width;
        }

        if (scrollX < leftBound) {
            if (leftAbsolute) {
                float over = leftBound - scrollX;
                mLeftEdge.onPull(Math.abs(over) / width);
                needsInvalidate = true;
            }
            scrollX = leftBound;
        } else if (scrollX > rightBound) {
            if (rightAbsolute) {
                float over = scrollX - rightBound;
                mRightEdge.onPull(Math.abs(over) / width);
                needsInvalidate = true;
            }
            scrollX = rightBound;
        }
        // Don't lose the rounded component
        mLastMotionX += scrollX - (int) scrollX;
        scrollTo((int) scrollX, getScrollY());
        pageScrolled((int) scrollX);

        return needsInvalidate;
    }

    /**
     * @return Info about the page at the current scroll position.
     *         This can be synthetic for a missing middle page; the 'object' field can be null.
     */
    private ItemInfo infoForCurrentScrollPosition() {
        final int width = getClientWidth();
        final float scrollOffset = width > 0 ? (float) getScrollX() / width : 0;
        final float marginOffset = width > 0 ? (float) mPageMargin / width : 0;
        int lastPos = -1;
        float lastOffset = 0.f;
        float lastWidth = 0.f;
        boolean first = true;

        ItemInfo lastItem = null;
        for (int i = 0; i < mItems.size(); i++) {
            ItemInfo ii = mItems.get(i);
            float offset;
            if (!first && ii.position != lastPos + 1) {
                // Create a synthetic item for a missing page.
                ii = mTempItem;
                ii.offset = lastOffset + lastWidth + marginOffset;
                ii.position = lastPos + 1;
                ii.widthFactor = mAdapter.getPageWidth(ii.position);
                i--;
            }
            offset = ii.offset;

            final float leftBound = offset;
            final float rightBound = offset + ii.widthFactor + marginOffset;
            if (first || scrollOffset >= leftBound) {
                if (scrollOffset < rightBound || i == mItems.size() - 1) {
                    return ii;
                }
            } else {
                return lastItem;
            }
            first = false;
            lastPos = ii.position;
            lastOffset = offset;
            lastWidth = ii.widthFactor;
            lastItem = ii;
        }

        return lastItem;
    }

    private int determineTargetPage(int currentPage, float pageOffset, int velocity, int deltaX) {
        int targetPage;
        if (Math.abs(deltaX) > mFlingDistance && Math.abs(velocity) > mMinimumVelocity) {
            targetPage = velocity > 0 ? currentPage : currentPage + 1;
        } else {
            final float truncator = currentPage >= mCurItem ? 0.4f : 0.6f;
            targetPage = currentPage + (int) (pageOffset + truncator);
        }

        if (mItems.size() > 0) {
            final ItemInfo firstItem = mItems.get(0);
            final ItemInfo lastItem = mItems.get(mItems.size() - 1);

            // Only let the user target pages we have items for
            targetPage = Math.max(firstItem.position, Math.min(targetPage, lastItem.position));
        }

        return targetPage;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        boolean needsInvalidate = false;

        final int overScrollMode = getOverScrollMode();
        if (overScrollMode == View.OVER_SCROLL_ALWAYS
                || (overScrollMode == View.OVER_SCROLL_IF_CONTENT_SCROLLS
                        && mAdapter != null && mAdapter.getCount() > 1)) {
            if (!mLeftEdge.isFinished()) {
                final int restoreCount = canvas.save();
                final int height = getHeight() - getPaddingTop() - getPaddingBottom();
                final int width = getWidth();

                canvas.rotate(270);
                canvas.translate(-height + getPaddingTop(), mFirstOffset * width);
                mLeftEdge.setSize(height, width);
                needsInvalidate |= mLeftEdge.draw(canvas);
                canvas.restoreToCount(restoreCount);
            }
            if (!mRightEdge.isFinished()) {
                final int restoreCount = canvas.save();
                final int width = getWidth();
                final int height = getHeight() - getPaddingTop() - getPaddingBottom();

                canvas.rotate(90);
                canvas.translate(-getPaddingTop(), -(mLastOffset + 1) * width);
                mRightEdge.setSize(height, width);
                needsInvalidate |= mRightEdge.draw(canvas);
                canvas.restoreToCount(restoreCount);
            }
        } else {
            mLeftEdge.finish();
            mRightEdge.finish();
        }

        if (needsInvalidate) {
            // Keep animating
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the margin drawable between pages if needed.
        if (mPageMargin > 0 && mMarginDrawable != null && mItems.size() > 0 && mAdapter != null) {
            final int scrollX = getScrollX();
            final int width = getWidth();

            final float marginOffset = (float) mPageMargin / width;
            int itemIndex = 0;
            ItemInfo ii = mItems.get(0);
            float offset = ii.offset;
            final int itemCount = mItems.size();
            final int firstPos = ii.position;
            final int lastPos = mItems.get(itemCount - 1).position;
            for (int pos = firstPos; pos < lastPos; pos++) {
                while (pos > ii.position && itemIndex < itemCount) {
                    ii = mItems.get(++itemIndex);
                }

                float drawAt;
                if (pos == ii.position) {
                    drawAt = (ii.offset + ii.widthFactor) * width;
                    offset = ii.offset + ii.widthFactor + marginOffset;
                } else {
                    float widthFactor = mAdapter.getPageWidth(pos);
                    drawAt = (offset + widthFactor) * width;
                    offset += widthFactor + marginOffset;
                }

                if (drawAt + mPageMargin > scrollX) {
                    mMarginDrawable.setBounds(Math.round(drawAt), mTopPageBounds,
                            Math.round(drawAt + mPageMargin), mBottomPageBounds);
                    mMarginDrawable.draw(canvas);
                }

                if (drawAt > scrollX + width) {
                    break; // No more visible, no sense in continuing
                }
            }
        }
    }

    /**
     * Start a fake drag of the pager.
     *
     * <p>A fake drag can be useful if you want to synchronize the motion of the ViewPager
     * with the touch scrolling of another view, while still letting the ViewPager
     * control the snapping motion and fling behavior. (e.g. parallax-scrolling tabs.)
     * Call {@link #fakeDragBy(float)} to simulate the actual drag motion. Call
     * {@link #endFakeDrag()} to complete the fake drag and fling as necessary.
     *
     * <p>During a fake drag the ViewPager will ignore all touch events. If a real drag
     * is already in progress, this method will return false.
     *
     * @return true if the fake drag began successfully, false if it could not be started.
     *
     * @see #fakeDragBy(float)
     * @see #endFakeDrag()
     */
    public boolean beginFakeDrag() {
        if (mIsBeingDragged) {
            return false;
        }
        mFakeDragging = true;
        setScrollState(SCROLL_STATE_DRAGGING);
        mInitialMotionX = mLastMotionX = 0;
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        } else {
            mVelocityTracker.clear();
        }
        final long time = SystemClock.uptimeMillis();
        final MotionEvent ev = MotionEvent.obtain(time, time, MotionEvent.ACTION_DOWN, 0, 0, 0);
        mVelocityTracker.addMovement(ev);
        ev.recycle();
        mFakeDragBeginTime = time;
        return true;
    }

    /**
     * End a fake drag of the pager.
     *
     * @see #beginFakeDrag()
     * @see #fakeDragBy(float)
     */
    public void endFakeDrag() {
        if (!mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }

        if (mAdapter != null) {
            final VelocityTracker velocityTracker = mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
            int initialVelocity = (int) velocityTracker.getXVelocity(mActivePointerId);
            mPopulatePending = true;
            final int width = getClientWidth();
            final int scrollX = getScrollX();
            final ItemInfo ii = infoForCurrentScrollPosition();
            final int currentPage = ii.position;
            final float pageOffset = (((float) scrollX / width) - ii.offset) / ii.widthFactor;
            final int totalDelta = (int) (mLastMotionX - mInitialMotionX);
            int nextPage = determineTargetPage(currentPage, pageOffset, initialVelocity,
                    totalDelta);
            setCurrentItemInternal(nextPage, true, true, initialVelocity);
        }
        endDrag();

        mFakeDragging = false;
    }

    /**
     * Fake drag by an offset in pixels. You must have called {@link #beginFakeDrag()} first.
     *
     * @param xOffset Offset in pixels to drag by.
     * @see #beginFakeDrag()
     * @see #endFakeDrag()
     */
    public void fakeDragBy(float xOffset) {
        if (!mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }

        if (mAdapter == null) {
            return;
        }

        mLastMotionX += xOffset;

        float oldScrollX = getScrollX();
        float scrollX = oldScrollX - xOffset;
        final int width = getClientWidth();

        float leftBound = width * mFirstOffset;
        float rightBound = width * mLastOffset;

        final ItemInfo firstItem = mItems.get(0);
        final ItemInfo lastItem = mItems.get(mItems.size() - 1);
        if (firstItem.position != 0) {
            leftBound = firstItem.offset * width;
        }
        if (lastItem.position != mAdapter.getCount() - 1) {
            rightBound = lastItem.offset * width;
        }

        if (scrollX < leftBound) {
            scrollX = leftBound;
        } else if (scrollX > rightBound) {
            scrollX = rightBound;
        }
        // Don't lose the rounded component
        mLastMotionX += scrollX - (int) scrollX;
        scrollTo((int) scrollX, getScrollY());
        pageScrolled((int) scrollX);

        // Synthesize an event for the VelocityTracker.
        final long time = SystemClock.uptimeMillis();
        final MotionEvent ev = MotionEvent.obtain(mFakeDragBeginTime, time, MotionEvent.ACTION_MOVE,
                mLastMotionX, 0, 0);
        mVelocityTracker.addMovement(ev);
        ev.recycle();
    }

    /**
     * Returns true if a fake drag is in progress.
     *
     * @return true if currently in a fake drag, false otherwise.
     *
     * @see #beginFakeDrag()
     * @see #fakeDragBy(float)
     * @see #endFakeDrag()
     */
    public boolean isFakeDragging() {
        return mFakeDragging;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = ev.getActionIndex();
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId) {
            // This was our active pointer going up. Choose a new
            // active pointer and adjust accordingly.
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMotionX = ev.getX(newPointerIndex);
            mActivePointerId = ev.getPointerId(newPointerIndex);
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
    }

    private void endDrag() {
        mIsBeingDragged = false;
        mIsUnableToDrag = false;

        if (mVelocityTracker != null) {
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private void setScrollingCacheEnabled(boolean enabled) {
        if (mScrollingCacheEnabled != enabled) {
            mScrollingCacheEnabled = enabled;
            if (USE_CACHE) {
                final int size = getChildCount();
                for (int i = 0; i < size; ++i) {
                    final View child = getChildAt(i);
                    if (child.getVisibility() != GONE) {
                        child.setDrawingCacheEnabled(enabled);
                    }
                }
            }
        }
    }

    /**
     * Check if this ViewPager can be scrolled horizontally in a certain direction.
     *
     * @param direction Negative to check scrolling left, positive to check scrolling right.
     * @return Whether this ViewPager can be scrolled in the specified direction. It will always
     *         return false if the specified direction is 0.
     */
    @Override
    public boolean canScrollHorizontally(int direction) {
        if (mAdapter == null) {
            return false;
        }

        final int width = getClientWidth();
        final int scrollX = getScrollX();
        if (direction < 0) {
            return (scrollX > (int) (width * mFirstOffset));
        } else if (direction > 0) {
            return (scrollX < (int) (width * mLastOffset));
        } else {
            return false;
        }
    }

    /**
     * Tests scrollability within child views of v given a delta of dx.
     *
     * @param v View to test for horizontal scrollability
     * @param checkV Whether the view v passed should itself be checked for scrollability (true),
     *               or just its children (false).
     * @param dx Delta scrolled in pixels
     * @param x X coordinate of the active touch point
     * @param y Y coordinate of the active touch point
     * @return true if child views of v can be scrolled by delta of dx.
     */
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v instanceof ViewGroup) {
            final ViewGroup group = (ViewGroup) v;
            final int scrollX = v.getScrollX();
            final int scrollY = v.getScrollY();
            final int count = group.getChildCount();
            // Count backwards - let topmost views consume scroll distance first.
            for (int i = count - 1; i >= 0; i--) {
                // TODO: Add versioned support here for transformed views.
                // This will not work for transformed views in Honeycomb+
                final View child = group.getChildAt(i);
                if (x + scrollX >= child.getLeft() && x + scrollX < child.getRight()
                        && y + scrollY >= child.getTop() && y + scrollY < child.getBottom()
                        && canScroll(child, true, dx, x + scrollX - child.getLeft(),
                                y + scrollY - child.getTop())) {
                    return true;
                }
            }
        }

        return checkV && v.canScrollHorizontally(-dx);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Let the focused view and/or our descendants get the key first
        return super.dispatchKeyEvent(event) || executeKeyEvent(event);
    }

    /**
     * You can call this function yourself to have the scroll view perform
     * scrolling from a key event, just as if the event had been dispatched to
     * it by the view hierarchy.
     *
     * @param event The key event to execute.
     * @return Return true if the event was handled, else false.
     */
    public boolean executeKeyEvent(@NonNull KeyEvent event) {
        boolean handled = false;
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    if (event.hasModifiers(KeyEvent.META_ALT_ON)) {
                        handled = pageLeft();
                    } else {
                        handled = arrowScroll(FOCUS_LEFT);
                    }
                    break;
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    if (event.hasModifiers(KeyEvent.META_ALT_ON)) {
                        handled = pageRight();
                    } else {
                        handled = arrowScroll(FOCUS_RIGHT);
                    }
                    break;
                case KeyEvent.KEYCODE_TAB:
                    if (event.hasNoModifiers()) {
                        handled = arrowScroll(FOCUS_FORWARD);
                    } else if (event.hasModifiers(KeyEvent.META_SHIFT_ON)) {
                        handled = arrowScroll(FOCUS_BACKWARD);
                    }
                    break;
            }
        }
        return handled;
    }

    /**
     * Handle scrolling in response to a left or right arrow click.
     *
     * @param direction The direction corresponding to the arrow key that was pressed. It should be
     *                  either {@link View#FOCUS_LEFT} or {@link View#FOCUS_RIGHT}.
     * @return Whether the scrolling was handled successfully.
     */
    public boolean arrowScroll(int direction) {
        View currentFocused = findFocus();
        if (currentFocused == this) {
            currentFocused = null;
        } else if (currentFocused != null) {
            boolean isChild = false;
            for (ViewParent parent = currentFocused.getParent(); parent instanceof ViewGroup;
                    parent = parent.getParent()) {
                if (parent == this) {
                    isChild = true;
                    break;
                }
            }
            if (!isChild) {
                // This would cause the focus search down below to fail in fun ways.
                final StringBuilder sb = new StringBuilder();
                sb.append(currentFocused.getClass().getSimpleName());
                for (ViewParent parent = currentFocused.getParent(); parent instanceof ViewGroup;
                        parent = parent.getParent()) {
                    sb.append(" => ").append(parent.getClass().getSimpleName());
                }
                Log.e(TAG, "arrowScroll tried to find focus based on non-child "
                        + "current focused view " + sb.toString());
                currentFocused = null;
            }
        }

        boolean handled = false;

        View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused,
                direction);
        if (nextFocused != null && nextFocused != currentFocused) {
            if (direction == View.FOCUS_LEFT) {
                // If there is nothing to the left, or this is causing us to
                // jump to the right, then what we really want to do is page left.
                final int nextLeft = getChildRectInPagerCoordinates(mTempRect, nextFocused).left;
                final int currLeft = getChildRectInPagerCoordinates(mTempRect, currentFocused).left;
                if (currentFocused != null && nextLeft >= currLeft) {
                    handled = pageLeft();
                } else {
                    handled = nextFocused.requestFocus();
                }
            } else if (direction == View.FOCUS_RIGHT) {
                // If there is nothing to the right, or this is causing us to
                // jump to the left, then what we really want to do is page right.
                final int nextLeft = getChildRectInPagerCoordinates(mTempRect, nextFocused).left;
                final int currLeft = getChildRectInPagerCoordinates(mTempRect, currentFocused).left;
                if (currentFocused != null && nextLeft <= currLeft) {
                    handled = pageRight();
                } else {
                    handled = nextFocused.requestFocus();
                }
            }
        } else if (direction == FOCUS_LEFT || direction == FOCUS_BACKWARD) {
            // Trying to move left and nothing there; try to page.
            handled = pageLeft();
        } else if (direction == FOCUS_RIGHT || direction == FOCUS_FORWARD) {
            // Trying to move right and nothing there; try to page.
            handled = pageRight();
        }
        if (handled) {
            playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
        }
        return handled;
    }

    private Rect getChildRectInPagerCoordinates(Rect outRect, View child) {
        if (outRect == null) {
            outRect = new Rect();
        }
        if (child == null) {
            outRect.set(0, 0, 0, 0);
            return outRect;
        }
        outRect.left = child.getLeft();
        outRect.right = child.getRight();
        outRect.top = child.getTop();
        outRect.bottom = child.getBottom();

        ViewParent parent = child.getParent();
        while (parent instanceof ViewGroup && parent != this) {
            final ViewGroup group = (ViewGroup) parent;
            outRect.left += group.getLeft();
            outRect.right += group.getRight();
            outRect.top += group.getTop();
            outRect.bottom += group.getBottom();

            parent = group.getParent();
        }
        return outRect;
    }

    boolean pageLeft() {
        if (mCurItem > 0) {
            setCurrentItem(mCurItem - 1, true);
            return true;
        }
        return false;
    }

    boolean pageRight() {
        if (mAdapter != null && mCurItem < (mAdapter.getCount() - 1)) {
            setCurrentItem(mCurItem + 1, true);
            return true;
        }
        return false;
    }

    /**
     * We only want the current page that is being shown to be focusable.
     */
    @Override
    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        final int focusableCount = views.size();

        final int descendantFocusability = getDescendantFocusability();

        if (descendantFocusability != FOCUS_BLOCK_DESCENDANTS) {
            for (int i = 0; i < getChildCount(); i++) {
                final View child = getChildAt(i);
                if (child.getVisibility() == VISIBLE) {
                    ItemInfo ii = infoForChild(child);
                    if (ii != null && ii.position == mCurItem) {
                        child.addFocusables(views, direction, focusableMode);
                    }
                }
            }
        }

        // we add ourselves (if focusable) in all cases except for when we are
        // FOCUS_AFTER_DESCENDANTS and there are some descendants focusable.  this is
        // to avoid the focus search finding layouts when a more precise search
        // among the focusable children would be more interesting.
        if (descendantFocusability != FOCUS_AFTER_DESCENDANTS
                || (focusableCount == views.size())) { // No focusable descendants
            // Note that we can't call the superclass here, because it will
            // add all views in.  So we need to do the same thing View does.
            if (!isFocusable()) {
                return;
            }
            if ((focusableMode & FOCUSABLES_TOUCH_MODE) == FOCUSABLES_TOUCH_MODE
                    && isInTouchMode() && !isFocusableInTouchMode()) {
                return;
            }
            if (views != null) {
                views.add(this);
            }
        }
    }

    /**
     * We only want the current page that is being shown to be touchable.
     */
    @Override
    public void addTouchables(ArrayList<View> views) {
        // Note that we don't call super.addTouchables(), which means that
        // we don't call View.addTouchables().  This is okay because a ViewPager
        // is itself not touchable.
        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                ItemInfo ii = infoForChild(child);
                if (ii != null && ii.position == mCurItem) {
                    child.addTouchables(views);
                }
            }
        }
    }

    /**
     * We only want the current page that is being shown to be focusable.
     */
    @Override
    protected boolean onRequestFocusInDescendants(int direction,
            Rect previouslyFocusedRect) {
        int index;
        int increment;
        int end;
        int count = getChildCount();
        if ((direction & FOCUS_FORWARD) != 0) {
            index = 0;
            increment = 1;
            end = count;
        } else {
            index = count - 1;
            increment = -1;
            end = -1;
        }
        for (int i = index; i != end; i += increment) {
            View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                ItemInfo ii = infoForChild(child);
                if (ii != null && ii.position == mCurItem) {
                    if (child.requestFocus(direction, previouslyFocusedRect)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        // Dispatch scroll events from this ViewPager.
        if (event.getEventType() == AccessibilityEventCompat.TYPE_VIEW_SCROLLED) {
            return super.dispatchPopulateAccessibilityEvent(event);
        }

        // Dispatch all other accessibility events from the current page.
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() == VISIBLE) {
                final ItemInfo ii = infoForChild(child);
                if (ii != null && ii.position == mCurItem
                        && child.dispatchPopulateAccessibilityEvent(event)) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams();
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return generateDefaultLayoutParams();
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams && super.checkLayoutParams(p);
    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    class MyAccessibilityDelegate extends AccessibilityDelegateCompat {

        @Override
        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(host, event);
            event.setClassName(ViewPager.class.getName());
            event.setScrollable(canScroll());
            if (event.getEventType() == AccessibilityEvent.TYPE_VIEW_SCROLLED && mAdapter != null) {
                event.setItemCount(mAdapter.getCount());
                event.setFromIndex(mCurItem);
                event.setToIndex(mCurItem);
            }
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setClassName(ViewPager.class.getName());
            info.setScrollable(canScroll());
            if (canScrollHorizontally(1)) {
                info.addAction(AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD);
            }
            if (canScrollHorizontally(-1)) {
                info.addAction(AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD);
            }
        }

        @Override
        public boolean performAccessibilityAction(View host, int action, Bundle args) {
            if (super.performAccessibilityAction(host, action, args)) {
                return true;
            }
            switch (action) {
                case AccessibilityNodeInfoCompat.ACTION_SCROLL_FORWARD: {
                    if (canScrollHorizontally(1)) {
                        setCurrentItem(mCurItem + 1);
                        return true;
                    }
                } return false;
                case AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD: {
                    if (canScrollHorizontally(-1)) {
                        setCurrentItem(mCurItem - 1);
                        return true;
                    }
                } return false;
            }
            return false;
        }

        private boolean canScroll() {
            return (mAdapter != null) && (mAdapter.getCount() > 1);
        }
    }

    private class PagerObserver extends DataSetObserver {
        PagerObserver() {
        }

        @Override
        public void onChanged() {
            dataSetChanged();
        }
        @Override
        public void onInvalidated() {
            dataSetChanged();
        }
    }

    /**
     * Layout parameters that should be supplied for views added to a
     * ViewPager.
     */
    public static class LayoutParams extends ViewGroup.LayoutParams {
        /**
         * true if this view is a decoration on the pager itself and not
         * a view supplied by the adapter.
         */
        public boolean isDecor;

        /**
         * Gravity setting for use on decor views only:
         * Where to position the view page within the overall ViewPager
         * container; constants are defined in {@link android.view.Gravity}.
         */
        public int gravity;

        /**
         * Width as a 0-1 multiplier of the measured pager width
         */
        float widthFactor = 0.f;

        /**
         * true if this view was added during layout and needs to be measured
         * before being positioned.
         */
        boolean needsMeasure;

        /**
         * Adapter position this view is for if !isDecor
         */
        int position;

        /**
         * Current child index within the ViewPager that this view occupies
         */
        int childIndex;

        public LayoutParams() {
            super(MATCH_PARENT, MATCH_PARENT);
        }

        public LayoutParams(Context context, AttributeSet attrs) {
            super(context, attrs);

            final TypedArray a = context.obtainStyledAttributes(attrs, LAYOUT_ATTRS);
            gravity = a.getInteger(0, Gravity.TOP);
            a.recycle();
        }
    }

    static class ViewPositionComparator implements Comparator<View> {
        @Override
        public int compare(View lhs, View rhs) {
            final LayoutParams llp = (LayoutParams) lhs.getLayoutParams();
            final LayoutParams rlp = (LayoutParams) rhs.getLayoutParams();
            if (llp.isDecor != rlp.isDecor) {
                return llp.isDecor ? 1 : -1;
            }
            return llp.position - rlp.position;
        }
    }
}
