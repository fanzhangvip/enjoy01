https://zhuanlan.zhihu.com/p/28242753
https://blog.csdn.net/FIRE_TRAY/article/details/50649683
https://pingfangx.github.io/java-tutorials/java/generics/genTypeInference.html
https://www.jianshu.com/p/e5b8cd33ec94

https://cloud.tencent.com/developer/article/1516755

重要
https://blog.csdn.net/s10461/article/details/53941091
https://blog.csdn.net/ShuSheng0007/article/details/80720406
https://blog.csdn.net/sunxianghuang/article/details/51982979#commentsedit


https://mp.weixin.qq.com/s?__biz=MzI4Njg5MDA5NA==&mid=2247484109&idx=1&sn=ed48fa9216c260fb9b622d9f383d8c25&chksm=ebd743ccdca0cadad9e8e4a5cd9a7ce96b595ddaf6fb2e817a9a0d49d4d54c50bb93a97e56eb&scene=21###wechat_redirect

https://kb.cnblogs.com/page/93005/
class ClassTestX extends Number, Y, Z {    
    private X x;    
    private static Y y; //编译错误，不能用在静态变量中    
    public X getFirst() {
        //正确用法        
        return x;    
    }    
    public void wrong() {        
        Z z = new Z(); //编译错误，不能创建对象    
    }
} 

最佳实践
　　在使用泛型的时候可以遵循一些基本的原则，从而避免一些常见的问题。

在代码中避免泛型类和原始类型的混用。比如ListString和List不应该共同使用。这样会产生一些编译器警告和潜在的运行时异常。当需要利用JDK 5之前开发的遗留代码，而不得不这么做时，也尽可能的隔离相关的代码。
在使用带通配符的泛型类的时候，需要明确通配符所代表的一组类型的概念。由于具体的类型是未知的，很多操作是不允许的。
泛型类最好不要同数组一块使用。你只能创建new List?[10]这样的数组，无法创建new ListString[10]这样的。这限制了数组的使用能力，而且会带来很多费解的问题。因此，当需要类似数组的功能时候，使用集合类即可。
不要忽视编译器给出的警告信息

http://www.hollischuang.com/archives/226

//Type
http://loveshisong.cn/%E7%BC%96%E7%A8%8B%E6%8A%80%E6%9C%AF/2016-02-16-Type%E8%AF%A6%E8%A7%A3.html
https://www.jianshu.com/p/0c2948f7e656


　1. Java中的泛型是什么 ? 使用泛型的好处是什么?

　　这是在各种Java泛型面试中，一开场你就会被问到的问题中的一个，主要集中在初级和中级面试中。那些拥有Java1.4或更早版本的开发背景的人都知道，在集合中存储对象并在使用前进行类型转换是多么的不方便。泛型防止了那种情况的发生。它提供了编译期的类型安全，确保你只能把正确类型的对象放入集合中，避免了在运行时出现ClassCastException。

　　2. Java的泛型是如何工作的 ? 什么是类型擦除 ?

　　这是一道更好的泛型面试题。泛型是通过类型擦除来实现的，编译器在编译时擦除了所有类型相关的信息，所以在运行时不存在任何类型相关的信息。例如List<String>在运行时仅用一个List来表示。这样做的目的，是确保能和Java 5之前的版本开发二进制类库进行兼容。你无法在运行时访问到类型参数，因为编译器已经把泛型类型转换成了原始类型。根据你对这个泛型问题的回答情况，你会得到一些后续提问，比如为什么泛型是由类型擦除来实现的或者给你展示一些会导致编译器出错的错误泛型代码。请阅读我的Java中泛型是如何工作的来了解更多信息。

　　3. 什么是泛型中的限定通配符和非限定通配符 ?

　　这是另一个非常流行的Java泛型面试题。限定通配符对类型进行了限制。有两种限定通配符，一种是<? extends T>它通过确保类型必须是T的子类来设定类型的上界，另一种是<? super T>它通过确保类型必须是T的父类来设定类型的下界。泛型类型必须用限定内的类型来进行初始化，否则会导致编译错误。另一方面<?>表示了非限定通配符，因为<?>可以用任意类型来替代。更多信息请参阅我的文章泛型中限定通配符和非限定通配符之间的区别。

　　4. List<? extends T>和List <? super T>之间有什么区别 ?

　　这和上一个面试题有联系，有时面试官会用这个问题来评估你对泛型的理解，而不是直接问你什么是限定通配符和非限定通配符。这两个List的声明都是限定通配符的例子，List<? extends T>可以接受任何继承自T的类型的List，而List<? super T>可以接受任何T的父类构成的List。例如List<? extends Number>可以接受List<Integer>或List<Float>。在本段出现的连接中可以找到更多信息。

　　5. 如何编写一个泛型方法，让它能接受泛型参数并返回泛型类型?

　　编写泛型方法并不困难，你需要用泛型类型来替代原始类型，比如使用T, E or K,V等被广泛认可的类型占位符。泛型方法的例子请参阅Java集合类框架。最简单的情况下，一个泛型方法可能会像这样:

      public V put(K key, V value) {

              return cache.put(key, value);

      }

　  6. Java中如何使用泛型编写带有参数的类?

　　这是上一道面试题的延伸。面试官可能会要求你用泛型编写一个类型安全的类，而不是编写一个泛型方法。关键仍然是使用泛型类型来代替原始类型，而且要使用JDK中采用的标准占位符。

　　7. 编写一段泛型程序来实现LRU缓存?

　　对于喜欢Java编程的人来说这相当于是一次练习。给你个提示，LinkedHashMap可以用来实现固定大小的LRU缓存，当LRU缓存已经满了的时候，它会把最老的键值对移出缓存。LinkedHashMap提供了一个称为removeEldestEntry()的方法，该方法会被put()和putAll()调用来删除最老的键值对。当然，如果你已经编写了一个可运行的JUnit测试，你也可以随意编写你自己的实现代码。

　　8. 你可以把List<String>传递给一个接受List<Object>参数的方法吗？

　　对任何一个不太熟悉泛型的人来说，这个Java泛型题目看起来令人疑惑，因为乍看起来String是一种Object，所以List<String>应当可以用在需要List<Object>的地方，但是事实并非如此。真这样做的话会导致编译错误。如果你再深一步考虑，你会发现Java这样做是有意义的，因为List<Object>可以存储任何类型的对象包括String, Integer等等，而List<String>却只能用来存储Strings。　

       List<Object> objectList;
       List<String> stringList;
       objectList = stringList;  //compilation error incompatible types
 　9. Array中可以用泛型吗?

　　这可能是Java泛型面试题中最简单的一个了，当然前提是你要知道Array事实上并不支持泛型，这也是为什么Joshua Bloch在Effective Java一书中建议使用List来代替Array，因为List可以提供编译期的类型安全保证，而Array却不能。

　　10. 如何阻止Java中的类型未检查的警告?

　　如果你把泛型和原始类型混合起来使用，例如下列代码，Java 5的javac编译器会产生类型未检查的警告，例如　　

       List<String> rawList = new ArrayList()


问：Java 的泛型是什么？有什么好处和优点？JDK 不同版本的泛型有什么区别？

答：泛型是 Java SE 1.5 的新特性，泛型的本质是参数化类型，这种参数类型可以用在类、接口和方法的创建中，分别称为泛型类、泛型接口、泛型方法。在 Java SE 1.5 之前没有泛型的情况的下只能通过对类型 Object 的引用来实现参数的任意化，其带来的缺点是要做显式强制类型转换，而这种强制转换编译期是不做检查的，容易把问题留到运行时，所以 泛型的好处是在编译时检查类型安全，并且所有的强制转换都是自动和隐式的，提高了代码的重用率，避免在运行时出现 ClassCastException。

JDK 1.5 引入了泛型来允许强类型在编译时进行类型检查；JDK 1.7 泛型实例化类型具备了自动推断能力，譬如 List<String> list = new ArrayList<String>(); 可以写成 List<String> llist = new ArrayList<>(); 了，JDK 具备自动推断能力。下面几种写法可以说是不同版本的兼容性了：

//JDK 1.5 推荐使用的写法

List<String> list =new ArrayList<String>();

//JDK 1.7 推荐使用的写法

List<String> llist =new ArrayList<>();

//可以使用，但不推荐，是为了兼容老版本，IDE 会提示警告，可以通过注解屏蔽警告

List<String> list =new ArrayList();

//可以使用，但不推荐，是为了兼容老版本，IDE 会提示警告，可以通过注解屏蔽警告

List list =new ArrayList<String>();

问：Java 泛型是如何工作的？什么是类型擦除？

答：泛型是通过类型擦除来实现的，编译器在编译时擦除了所有泛型类型相关的信息，所以在运行时不存在任何泛型类型相关的信息，譬如 List<Integer> 在运行时仅用一个 List 来表示，这样做的目的是为了和 Java 1.5 之前版本进行兼容。泛型擦除具体来说就是在编译成字节码时首先进行类型检查，接着进行类型擦除（即所有类型参数都用他们的限定类型替换，包括类、变量和方法），接着如果类型擦除和多态性发生冲突时就在子类中生成桥方法解决，接着如果调用泛型方法的返回类型被擦除则在调用该方法时插入强制类型转换。

问：Java 泛型类、泛型接口、泛型方法有什么区别？

答：泛型类是在实例化类的对象时才能确定的类型，其定义譬如 class Test<T> {}，在实例化该类时必须指明泛型 T 的具体类型。

泛型接口与泛型类一样，其定义譬如 interface Generator<E> { E dunc(E e); }。

泛型方法所在的类可以是泛型类也可以是非泛型类，是否拥有泛型方法与所在的类无关，所以在我们应用中应该尽可能使用泛型方法，不要放大作用空间，尤其是在 static 方法时 static 方法无法访问泛型类的类型参数，所以更应该使用泛型的 static 方法（声明泛型一定要写在 static 后返回值类型前）。泛型方法的定义譬如 <T> void func(T val) {}。

问：Java 如何优雅的实现元组？

答：元组其实是关系数据库中的一个学术名词，一条记录就是一个元组，一个表就是一个关系，纪录组成表，元组生成关系，这就是关系数据库的核心理念。很多语言天生支持元组，譬如 Python 等，在语法本身支持元组的语言中元组是用括号表示的，如 (int, bool, string) 就是一个三元组类型，不过在 Java、C 等语言中就比较坑爹，语言语法本身不具备这个特性，所以在 Java 中我们如果想优雅实现元组就可以借助泛型类实现，如下是一个三元组类型的实现：

Triplet<A,B,C>{

    private A a;

    private B a;

    private C a;

    public Triplet(A a,B b,C c){

        this.a =a;

        this.b =b;

        this.c =c;

}

}

