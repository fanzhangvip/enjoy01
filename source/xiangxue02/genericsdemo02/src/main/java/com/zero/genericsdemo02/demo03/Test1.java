package com.zero.genericsdemo02.demo03;


import com.zero.genericsdemo02.Collections;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class Test1 {



    public static void scene01() {
        //先让几位角色上场
        XiaoMing xiaoMing = new XiaoMing();
        XiaoLi xiaoLi = new XiaoLi();
        XiaoLiMa xiaoLiMa = new XiaoLiMa();
        //小丽妈去拿苹果盘子装了些苹果
        Plate<Apple> applePlate = xiaoLiMa.getApple();
        //小明吃苹果
        xiaoMing.eat(applePlate.get());
        Plate<? extends Fruit> fruitPlate = xiaoLiMa.getSnack(applePlate);
        //这时候小明再从盘子里面那苹果吃,发现不行了
        xiaoMing.eat((Apple) fruitPlate.get());
        //实际上
        Fruit fruit = fruitPlate.get();
        Object object = fruitPlate.get();
//        Banana banana = fruitPlate.get();
    }

    public static void scene02() {
        //ColorPlate,BigPlate ->AIPlate ->Plate
        Plate<Apple> applePlate = new AIPlate<Apple>();
        Plate<Apple> applePlate1 = new BigPlate<Apple>();
        Plate<Apple> applePlate2 = new ColorPlate<String, Apple>();
    }

    public static void scene03() {
        Plate<? super Fruit> lowerfruitPlate = new AIPlate<Food>();
        lowerfruitPlate.set(new Apple());
        lowerfruitPlate.set(new Banana());
//        lowerfruitPlate.set(new Food());

//        Fruit newFruit1 = lowerfruitPlate.get();
//        Apple newFruit3 = lowerfruitPlate.get();
        Object newFruit2 = lowerfruitPlate.get();
    }

    public static void scene05() {
        //<?> == <? extends Object>
        Plate<?> fruitPlate = new AIPlate<Apple>();
//        Fruit fruit = fruitPlate.get();
//        fruitPlate.set(new Apple());

        fruitPlate.toString();
        Object object = fruitPlate.get();
        fruitPlate.set(null);

    }

    public static void main(String[] args) {

        scen07();

    }
    public static void scen07() {
        List<Apple> src = new ArrayList<>();
        src.add(new Apple(1));
        List<Apple> dest = new ArrayList<>(10);
        dest.add(new Apple(2));
        System.out.println(dest);
        copy(dest,src);
        System.out.println(dest);


        List<Banana> src1 = new ArrayList<>();
        src1.add(new Banana(1));
        List<Banana> dest1 = new ArrayList<>(10);
        dest1.add(new Banana(2));
        copy1(dest1,src1);

        List<Fruit> dest2 = new ArrayList<>(10);
        dest2.add(new Banana());
        Test1.<Fruit>copy3(dest2,src1);



    }

    public static void copy(List<Apple> dest, List<Apple> src) {
         Collections.copy(dest,src);
    }

    public static <T> void copy1(List<T> dest, List<T> src) {
        Collections.copy(dest,src);
    }

    public static <T> void copy2(List<? super T> dest, List<T> src) {
        Collections.copy(dest,src);
    }

    public static <T> void copy3(List<? super T> dest, List<? extends T> src) {
        Collections.copy(dest,src);
    }

    public static void scene06() {
//        List<Apple> src = new ArrayList<>();
//        src.add(new Apple(1));
//        List<Apple> dest = new ArrayList<>(10);
//        dest.add(new Apple(2));
//        Test1.<Apple>copy(dest,src);
//        System.out.println(dest);
//        List<Fruit> dest1 = new ArrayList<>(10);
//        dest1.add(new Banana());
//        Test1.<Apple>copy1(dest1,src);//不行了，因为要求T是同一种类型
//        System.out.println(dest1);
//        List<Food> dest2 = new ArrayList<>(10);
//        dest2.add(new Banana());
////        Test1.<Food>copy1(dest2,src);//又不行了
//        Test1.<Food>copy2(dest2,src);
//        System.out.println(dest2);
    }

//    public static <T> void copy(List<T> dest,List<T> src){
//        Collections.copy(dest,src);
//    }
//    public static <T> void copy1(List<? super T> dest,List<T> src){
//        Collections.copy(dest,src);
//    }
//    public static <T> void copy2(List<? super T> dest,List<? extends T> src){
//        Collections.copy(dest,src);
//    }

    public static void scene07() {
//      * @param <T> the input value type  <? super T>  Super Consumer
//      * @param <R> the output value type <? extends R> Producer Extends
//      public interface Function<T, R>
//      Function<? super T, ? extends ObservableSource<? extends R>> mapper
        Observable.<String>just("1")// T -> String
                .<Integer>flatMap(new Function<String, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(String s) throws Exception {
                        return new Observable<Integer>() {
                            @Override
                            protected void subscribeActual(Observer<? super Integer> observer) {
                                observer.onNext(Integer.parseInt(s));//observer只写
                            }
                        };
                    }
                })
                .<Integer>subscribe(new Consumer<Number>() {
                    @Override
                    public void accept(Number integer) throws Exception {
                        System.out.println("integer: " + integer);
                    }
                });

        Observable.<AIPlate<Apple>>just(new AIPlate<Apple>())
                .<Plate<? extends Fruit>>flatMap(new Function<Plate<Apple>, ObservableSource<Plate<? extends Fruit>>>() {
                    @Override
                    public ObservableSource<Plate<? extends Fruit>> apply(Plate<Apple> appleAIPlate) throws Exception {
                        appleAIPlate.set(new Apple());
                        return Observable.just(appleAIPlate);
                    }
                })
                .subscribe(new Consumer<Plate<? extends Food>>() {
                    @Override
                    public void accept(Plate<? extends Food> fruitPlate) throws Exception {
                        System.out.println(fruitPlate.get());
                    }
                });

        Observable.just(new D())
                .flatMap(new Function<C, ObservableSource<B>>() {
                    @Override
                    public ObservableSource<B> apply(C d) throws Exception {
                        return Observable.just(d);
                    }
                })
                .subscribe(new Consumer<A>() {
                    @Override
                    public void accept(A b) throws Exception {

                    }
                });

    }

    static class A {
    }

    static class B extends A {
    }

    static class C extends B {
    }

    static class D extends C {
    }

    static class E extends D {
    }

    static class F extends E {
    }

    static class G extends F {
    }


}
