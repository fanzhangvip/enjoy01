//package com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use;
//
//public class PESC {
//    public static void main(String[] args) {
//        //副作用1
//        //生产者使用extends但不能往列表中添加任何元素，只能往外取元素；
//        //<? extends Fruit>会使往盘子里放东西的set( )方法失效。
//        // 但取东西get( )方法还有效。比如下面例子里两个set()方法，
//        // 插入Apple和Fruit都报错
//        com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Plate<? extends com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Fruit> p = new com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Plate<com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Apple>(new com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Apple());
//        //不能存入任何元素
////        p.set(new Fruit());    //Error
////        p.set(new Apple());    //Error
//      //读取出来的东西只能存放在Fruit或它的基类里。
//        com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Fruit newFruit1 = p.get();
//        Object newFruit2 = p.get();
////        Apple newFruit3 = p.get();    //Error
//        /**
//         * 原因：编译器只知道容器内是Fruit或者它的派生类，但具体是什么类型不知道。
//         * 可能是Fruit？可能是Apple？也可能是Banana，RedApple，GreenApple？
//         * 编译器在看到后面用Plate赋值以后，盘子里没有被标上有“苹果”。
//         * 而是标上一个占位符：CAP#1，来表示捕获一个Fruit或Fruit的子类，
//         * 具体是什么类不知道，代号CAP#1。
//         * 然后无论是想往里插入Apple或者Meat或者Fruit编译器都不知道能不能和这个CAP#1匹配，
//         * 所以就都不允许
//         */
//        //所以通配符<?>和T类型参数的区别就在于，对编译器来说所有的T都代表同一种类型。
//        // 比如下面这个泛型方法里，三个T都指代同一个类型，要么都是String，要么都是Integer。
//        //
//        //public <T> List<T> fill(T... t);
//        //通配符<?>没有这种约束，Plate<?>单纯的就表示：盘子里放了一个东西，是什么我不知道。
//        // 所以Plate<？ extends Fruit>里什么都放不进去。
//
//
//        //副作用2
//        //消费者使用super，可以往里存，但是往外取只能放在Object对象里，
//        // 如果没有放在Object中就不能保证从中读取到的元素的类型，也可以理解Set（）方法时效。
//        com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Plate<? super com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Fruit> p1 = new com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Plate<com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Fruit>(new com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Fruit());
//        //存入元素正常
//        p1.set(new com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Fruit());
//        p1.set(new com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Apple());
//        //读取出来的东西只能存放在Object类里。
////        Apple newFruit3 = p1.get();    //Error
////        Fruit newFruit1 = p1.get();    //Error
////        Object newFruit2 = p1.get();
//        /**
//         * 因为下界规定了元素的最小粒度的下限，实际上是放松了容器元素的类型控制。
//         * 既然元素是Fruit的基类，那往里存粒度比Fruit小的都可以。
//         * 但往外读取元素就费劲了，只有所有类的基类Object对象才能装下。
//         * 但这样的话，元素的类型信息就全部丢失
//         */
//    }
//}
//
//class Plate<T>{
//    private T item;
//    public Plate(T t){item = t;}
//    public void set(T t){item = t;}
//    public T get(){return item;}
//}
//
//class Food{}
//
//class Fruit extends com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Food {}
//class Meat extends com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Food {}
//
//class Apple extends com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Fruit {}
//class Banana extends com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Fruit {}
//class Pork extends com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Meat {}
//class Beef extends com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Meat {}
//
//class RedApple extends com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Apple {}
//class GreenApple extends com.zero.genericsdemo.wildcards_07.guildelines_wildcard_use.ext.Apple {}
