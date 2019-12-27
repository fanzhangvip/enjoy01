package com.zero.kotlinstudy.test

import android.view.View

open class KBaseModel<T>{}

open class KBaseViewModel<V,M:KBaseModel<View>>{}

class KMvvMActivity<VM:KBaseViewModel<View,KBaseModel<View>>>{}