package com.enjoy.zero.libzhujie01.inherited;


import java.lang.annotation.Inherited;

@Inherited
@interface Tuhao {
}

@Tuhao
public class LaoWang {
}

/**
 * 自动继承老王的 土豪 这个标签
 */
class XiaoWang extends LaoWang {
}

