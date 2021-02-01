package com.zero.lib.kotlin.basic01

class V1< T :P<*>> {

    var mP :T ?=null
}

class P1<T :V<*> > {

    var mV :T?=null
}


