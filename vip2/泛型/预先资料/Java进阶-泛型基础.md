# Java进阶-泛型

> 本预先资料来源于Oracle官方文档Java™ 教程-Java Tutorials 
> 官方文档:https://docs.oracle.com/javase/tutorial/java/generics/index.html
> 中文翻译:https://pingfangx.github.io/java-tutorials/java/generics/types.html

泛型（Generic）是Java编程语言的强大功能。它们提高了代码的类型安全性，使你在编译时可以检测到更多错误。

在任何不平凡的软件项目中，错误都是生活中的事实。仔细的计划，编程和测试可以帮助降低其普遍性，但是无论如何，它们总会在某种程度上找到爬入你的代码的方法。随着新功能的引入以及代码库的大小和复杂性的增加，这一点变得尤为明显。

幸运的是，某些错误比其它错误更容易发现。例如，可以在早期发现编译时错误；你可以使用编译器的错误消息找出问题所在，然后就可以在那里进行修复。但是，运行时错误可能会带来更多问题。它们并不总是立即浮出水面，而当它们浮出水面时，可能是在程序中与实际问题原因相去甚远的某个时刻。

泛型通过在编译时检测到更多错误来增加代码的稳定性。完成本课程后，你可能需要继续阅读Gilad Bracha的[Generics](https://docs.oracle.com/javase/tutorial/extra/generics/index.html)教程。

## Why Use Generics?（为什么要使用泛型？）

简而言之，泛型在定义类，接口和方法时使类型（类和接口）成为参数。与方法声明中使用的更熟悉的形式参数非常相似，类型参数为你提供了一种使用不同输入重复使用相同代码的方法。区别在于形式参数的输入是值，而类型参数的输入是类型。

与非泛型代码相比，使用泛型的代码具有许多优点：

- 在编译时进行更强的类型检查。Java编译器将强类型检查应用于通用代码，如果代码违反类型安全，则会发出错误。修复编译时错误比修复运行时错误容易，后者可能很难找到。

- 消除类型转换。以下不带泛型的代码段需要强制转换：

  ```java
  List list = new ArrayList();
  list.add("hello");
  String s = (String) list.get(0);
  ```
  

当使用泛型重写时，代码不需要强制转换：

```java
  List<String> list = new ArrayList<String>();
  list.add("hello");
  String s = list.get(0);   // no cast
```

- 使程序员能够实现通用算法。通过使用泛型，程序员可以实现对不同类型的集合进行工作，可以自定义并且类型安全且易于阅读的泛型算法。

## Generic Types（通用类型）

通用类型是通过类型进行参数化的通用类或接口。下面的Box类将被修改以演示该概念。

#### A Simple Box Class（一个简单的Box类）

首先检查对任何类型的对象进行操作的非通用Box类。它只需要提供两种方法：`set`（将对象添加到Box中）和`get`（将其检索到）：

```java
public class Box {
    private Object object;

    public void set(Object object) { this.object = object; }
    public Object get() { return object; }
}
```

由于它的方法接受或返回一个`Object`，因此只要它不是原始类型之一，你就可以随意传递任何想要的东西。在编译时无法验证类的使用方式。代码的一部分可能会将`Integer`放在Box中，并期望从中取出`Integer`，而代码的另一部分可能会错误地传入`String`，从而导致运行时错误。

#### A Generic Version of the Box Class（Box类的通用版本）

通用类的定义格式如下：

```java
class name<T1, T2, ..., Tn> { /* ... */ }
```

在类名之后，类型参数部分由尖括号（`<>`）分隔。它指定了类型参数（也称为类型变量）T1、T2、...和Tn。

要更新Box类以使用泛型，可以通过将代码“`public class Box`”更改为“`public class Box`”来创建泛型类型声明。

进行此更改后，Box类变为：

```java
/**
 * Generic version of the Box class.
 * @param <T> the type of the value being boxed
 */
public class Box<T> {
    // T stands for "Type"
    private T t;

    public void set(T t) { this.t = t; }
    public T get() { return t; }
}
```

如你所见，所有出现的`Object`都将替换为T。类型变量可以是你指定的任何非基本类型：任何类类型，任何接口类型，任何数组类型，甚至是另一个类型变量。

可以将相同的技术应用于创建通用接口。

#### Type Parameter Naming Conventions（类型参数命名约定）

按照约定，类型参数名称是单个大写字母。这与你已经知道的变量命名约定形成鲜明对比，并且有充分的理由：没有该约定，将很难分辨类型变量与普通类或接口名称之间的区别。

最常用的类型参数名称是：

- E - Element (Java Collections Framework广泛使用)
- K - Key
- N - Number
- T - Type
- V - Value
- S,U,V etc. - 2nd, 3rd, 4th types

你将看到在Java SE API以及本课程其余部分中使用的这些名称。

#### Invoking and Instantiating a Generic Type（调用和实例化泛型类型）

要从代码中引用通用Box类，必须执行通用类型调用，该调用将T替换为某些具体值，例如`Integer`：

```java
Box<Integer> integerBox;
```

你可以认为泛型类型调用类似于普通方法调用，但是你没有将参数传递给方法，而是将类型参数（在这种情况下为`Integer`）传递给Box类本身。

> 类型参数和类型参数术语：许多开发人员可以互换使用术语“type parameter”（又名形参）和“type argument”（又名实参），但是这些术语并不相同。编码时，提供类型实参以创建参数化类型。因此，`Foo`中的T是类型参数（形参），而`Foo f`中的`String`是类型参数（实参）。在使用这些术语时，本课将遵循此定义。

像任何其它变量声明一样，此代码实际上不会创建新的Box对象。它只是声明integerBox将保存对“Box of Integer”的引用，这就是读取`Box`的方式。

泛型类型的调用通常称为参数化类型。

要实例化此类，请像往常一样使用`new`关键字，但将``放在类名和括号之间：

```java
Box<Integer> integerBox = new Box<Integer>();
```

#### The Diamond（菱形）

在Java SE 7和更高版本中，只要编译器可以从上下文确定或推断出类型参数，就可以用一组空的类型参数（`<>`）替换调用通用类的构造函数所需的类型参数。这对尖括号`<>`被非正式地称为菱形。例如，你可以使用以下语句创建`Box`的实例：

```java
Box<Integer> integerBox = new Box<>();
```

有关菱形符号和类型推断的更多信息，请参见[类型推断](https://docs.oracle.com/javase/tutorial/java/generics/genTypeInference.html)。

#### Multiple Type Parameters（多种类型的参数）

如前所述，泛型类可以具有多个类型参数。例如，通用的OrderedPair类实现了通用的Pair接口：

```java
public interface Pair<K, V> {
    public K getKey();
    public V getValue();
}

public class OrderedPair<K, V> implements Pair<K, V> {

    private K key;
    private V value;

    public OrderedPair(K key, V value) {
	this.key = key;
	this.value = value;
    }

    public K getKey()	{ return key; }
    public V getValue() { return value; }
}
```

以下语句创建OrderedPair类的两个实例：

```java
Pair<String, Integer> p1 = new OrderedPair<String, Integer>("Even", 8);
Pair<String, String>  p2 = new OrderedPair<String, String>("hello", "world");
```

代码`new OrderedPair`将K实例化为`String`，将V实例化为`Integer`。因此，OrderedPair的构造函数的参数类型分别为`String`和`Integer`。由于自动装箱，将`String`和`int`传递给类是有效的。

如The Diamond（菱形）所述，由于Java编译器可以从声明`OrderedPair`推断出K和V类型，因此可以使用菱形表示法来缩短这些语句：

```java
OrderedPair<String, Integer> p1 = new OrderedPair<>("Even", 8);
OrderedPair<String, String>  p2 = new OrderedPair<>("hello", "world");
```

要创建通用接口，请遵循与创建通用类相同的约定。

#### Parameterized Types（参数化类型）

你还可以用参数化类型（即`List`）替换类型参数（即K或V）。例如，使用`OrderedPair`示例：

```java
OrderedPair<String, Box<Integer>> p = new OrderedPair<>("primes", new Box<Integer>(...));
```

### Raw Types（原始类型）

原始类型是没有任何类型参数的泛型类或接口的名称。例如，给定通用Box类：

```java
public class Box<T> {
    public void set(T t) { /* ... */ }
    // ...
}
```

要创建`Box`的参数化类型，请为形式类型参数T提供一个实际的类型参数：

```java
Box<Integer> intBox = new Box<>();
```

如果省略实际的类型参数，则创建`Box`的原始类型：

```java
Box rawBox = new Box();
```

因此，Box是通用类型`Box`的原始类型。但是，非泛型类或接口类型不是原始类型。

原始类型显示在旧版代码中，因为在JDK 5.0之前，许多API类（例如`Collections`类）不是通用的。使用原始类型时，你实际上会获得泛型行为（Box为你提供对象）。为了向后兼容，允许将参数化类型分配给其原始类型：

```java
Box<String> stringBox = new Box<>();
Box rawBox = stringBox;               // OK
```

但是，如果将原始类型分配给参数化类型，则会收到警告：

```java
Box rawBox = new Box();           // rawBox is a raw type of Box<T>
Box<Integer> intBox = rawBox;     // warning: unchecked conversion
```

如果你使用原始类型来调用在相应的泛型类型中定义的泛型方法，也会收到警告：

```java
Box<String> stringBox = new Box<>();
Box rawBox = stringBox;
rawBox.set(8);  // warning: unchecked invocation to set(T)
```

该警告表明原始类型会绕过通用类型检查，从而将不安全代码的捕获推迟到运行时。因此，应避免使用原始类型。

[Type Erasure](https://docs.oracle.com/javase/tutorial/java/generics/erasure.html)部分提供了有关Java编译器如何使用原始类型的更多信息。

#### Unchecked Error Messages（未检查的错误消息）

如前所述，将旧代码与通用代码混合时，你可能会遇到类似于以下内容的警告消息：

```java
Note: Example.java uses unchecked or unsafe operations.
Note: Recompile with -Xlint:unchecked for details.
```

当使用旧的API操作原始类型时可能会出现这种情况，如下例所示：

```java
public class WarningDemo {
    public static void main(String[] args){
        Box<Integer> bi;
        bi = createBox();
    }

    static Box createBox(){
        return new Box();
    }
}
```

术语“未检查”表示编译器没有足够的类型信息来执行确保类型安全所需的所有类型检查。尽管编译器会给出提示，但默认情况下禁用“未检查”警告。要查看所有“未检查”的警告，请使用`-Xlint:unchecked`重新编译。

使用`-Xlint:unchecked`重新编译前面的示例将显示以下附加信息：

```text
WarningDemo.java:4: warning: [unchecked] unchecked conversion
found   : Box
required: Box<java.lang.Integer>
        bi = createBox();
                      ^
1 warning
```

要完全禁用未检查的警告，请使用`-Xlint:unchecked`标志。`@SuppressWarnings("unchecked")`注解禁止未检查的警告。如果你不熟悉`@SuppressWarnings`语法，请参阅[注解](https://docs.oracle.com/javase/tutorial/java/annotations/index.html)。

## Generic Methods（通用方法）

通用方法是指引入自己的类型参数的方法。这类似于声明一个泛型方法，但类型参数的范围仅限于声明它的方法。允许使用静态和非静态的泛型方法，也允许使用泛型类构造函数。

通用方法的语法包括类型参数列表，在尖括号内，该列表出现在方法的返回类型之前。对于静态泛型方法，类型参数部分必须出现在方法的返回类型之前。

Util类包含一个通用方法`compare`，该方法比较两个Pair对象：

```java
public class Util {
    public static <K, V> boolean compare(Pair<K, V> p1, Pair<K, V> p2) {
        return p1.getKey().equals(p2.getKey()) &&
               p1.getValue().equals(p2.getValue());
    }
}

public class Pair<K, V> {

    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public void setKey(K key) { this.key = key; }
    public void setValue(V value) { this.value = value; }
    public K getKey()   { return key; }
    public V getValue() { return value; }
}
```

调用此方法的完整语法为：

```java
Pair<Integer, String> p1 = new Pair<>(1, "apple");
Pair<Integer, String> p2 = new Pair<>(2, "pear");
boolean same = Util.<Integer, String>compare(p1, p2);
```

该类型已明确提供，如上所示。通常，可以将其忽略，编译器将推断出所需的类型：

```
Pair<Integer, String> p1 = new Pair<>(1, "apple");
Pair<Integer, String> p2 = new Pair<>(2, "pear");
boolean same = Util.compare(p1, p2);
复制代码
```

此功能称为类型推断，使你可以在不指定尖括号之间的类型的情况下，将通用方法作为普通方法调用。“[类型推断](https://docs.oracle.com/javase/tutorial/java/generics/genTypeInference.html)”将进一步讨论该主题。

## Bounded Type Parameters（限定类型参数）

有时你可能想限制可以在参数化类型中用作类型参数的类型。例如，对数字进行操作的方法可能只希望接受`Number`或其子类的实例。这就是限定类型参数的用途。

要声明一个限定的类型参数，请列出类型参数的名称，然后列出`extends`关键字，然后列出其上限（在本示例中为`Number`）。请注意，在这种情况下，`extends`通常用于表示“扩展”（如在类中）或“实现”（如在接口中）。

```java
public class Box<T> {

    private T t;          

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }

    public <U extends Number> void inspect(U u){
        System.out.println("T: " + t.getClass().getName());
        System.out.println("U: " + u.getClass().getName());
    }

    public static void main(String[] args) {
        Box<Integer> integerBox = new Box<Integer>();
        integerBox.set(new Integer(10));
        integerBox.inspect("some text"); // error: this is still String!
    }
}
```

通过修改通用方法以包含此限定类型参数，由于我们的`inspect`调用仍包含`String`，因此编译现在将失败：

```reStructuredText
Box.java:21: <U>inspect(U) in Box<java.lang.Integer> cannot
  be applied to (java.lang.String)
                        integerBox.inspect("10");
                                  ^
1 error
```

除了限制可用于实例化泛型类型的类型之外，限定类型参数还允许你调用在范围中定义的方法：

```java
public class NaturalNumber<T extends Integer> {

    private T n;

    public NaturalNumber(T n)  { this.n = n; }

    public boolean isEven() {
        return n.intValue() % 2 == 0;
    }

    // ...
}
```

`isEven`方法通过n调用`Integer`类中定义的`intValue`方法。

#### Multiple Bounds（多重限定）

前面的示例说明了使用带单个限定的类型参数，但是一个类型参数可以具有多个限定：

```java
<T extends B1 & B2 & B3>
```

具有多个限定的类型变量是范围中列出的所有类型的子类型。如果范围之一是类，则必须首先指定它。例如：

```java
Class A { /* ... */ }
interface B { /* ... */ }
interface C { /* ... */ }

class D <T extends A & B & C> { /* ... */ }
```

如果未首先指定绑定A，则会出现编译时错误：

```java
class D <T extends B & A & C> { /* ... */ }  // compile-time error
```

### Generic Methods and Bounded Type Parameters（通用方法和限定类型参数）

限定类型参数是实现通用算法的关键。考虑以下方法，该方法计算数组`T[]`中大于指定元素elem的元素数。

```java
public static <T> int countGreaterThan(T[] anArray, T elem) {
    int count = 0;
    for (T e : anArray)
        if (e > elem)  // compiler error
            ++count;
    return count;
}
```

该方法的实现很简单，但是不能编译，因为大于运算符（`>`）仅适用于基本类型，例如`short`、`int`、`double`、`long`、`float`、`byte`和`char`。你不能使用`>`运算符比较对象。要解决此问题，请使用`Comparable`接口限定的类型参数：

```java
public interface Comparable<T> {
    public int compareTo(T o);
}
```

结果代码将是：

```java
public static <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
    int count = 0;
    for (T e : anArray)
        if (e.compareTo(elem) > 0)
            ++count;
    return count;
}
```

## Generics, Inheritance, and Subtypes（泛型，继承和子类型）

众所周知，只要类型兼容，就可以将一种类型的对象分配给另一种类型的对象。例如，你可以将一个`Integer`分配给一个`Object`，因为`Object`是`Integer`的超类型之一：

```java
Object someObject = new Object();
Integer someInteger = new Integer(10);
someObject = someInteger;   // OK
```

在面向对象的术语中，这称为“`is a`”关系。由于`Integer`是一种`Object`，因此允许分配。但是`Integer`也是`Number`的一种，因此以下代码也有效：

```java
public void someMethod(Number n) { /* ... */ }

someMethod(new Integer(10));   // OK
someMethod(new Double(10.1));   // OK
```

泛型也是如此。你可以执行通用类型调用，将`Number`作为其类型参数传递，并且如果该参数与`Number`兼容，则可以随后进行`add`的任何后续调用：

```java
Box<Number> box = new Box<Number>();
box.add(new Integer(10));   // OK
box.add(new Double(10.1));  // OK
```

现在考虑以下方法：

```java
public void boxTest(Box<Number> n) { /* ... */ }
```

它接受哪种类型？通过查看其签名，你可以看到它接受一个类型为`Box`的单个参数。但是，这是什么意思？如你所料，你是否允许传递`Box`或`Box`？答案是“否”，因为`Box`和`Box`不是`Box`的子类型。

在使用泛型进行编程时，这是一个常见的误解，但它是一个重要的概念。



![img](data:image/gif;base64,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)



> 注意：给定两种具体的类型A和B（例如`Number`和`Integer`），无论A和B是否相关，`MyClass`与`MyClass`没有关系。`MyClass`和`MyClass`的公共父对象是`Object`。有关在类型参数相关时如何在两个泛型类之间创建类似子类型的关系的信息，请参见[通配符和子类型](https://docs.oracle.com/javase/tutorial/java/generics/subtyping.html)。

### Generic Classes and Subtyping（通用类和子类型）

你可以通过扩展或实现来泛型通用类或接口。一个类或接口的类型参数与另一类或接口的类型参数之间的关系由`extends`和`implements`子句确定。

以`Collections`类为例，`ArrayList`实现`List`，而`List`扩展`Collection`。因此，`ArrayList`是`List`的子类型，而`List`是`Collection`的子类型。只要你不改变类型参数，子类型关系就保留在类型之间。



![img](data:image/gif;base64,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)



现在假设我们要定义自己的列表接口PayloadList，该接口将泛型P的可选值与每个元素相关联。它的声明可能看起来像：

```java
interface PayloadList<E,P> extends List<E> {
  void setPayload(int index, P val);
}
```

PayloadList的以下参数化是`List`的子类型：

- `PayloadList<String,String>`
- `PayloadList<String,Integer>`
- `PayloadList<String,Exception>`



![img](data:image/gif;base64,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)



## Type Inference（类型推断）

类型推断是Java编译器查看每个方法调用和相应声明以确定使调用适用的类型参数的能力。推断算法确定参数的类型，以及确定结果是否被分配或返回的类型（如果有）。最后，推断算法尝试找到与所有参数一起使用的最具体的类型。

为了说明最后一点，在下面的示例中，推断确定传递给`pick`方法的第二个参数的类型为`Serializable`：

```java
static <T> T pick(T a1, T a2) { return a2; }
Serializable s = pick("d", new ArrayList<String>());
```

#### Type Inference and Generic Methods（类型推断和通用方法）

通用方法为你引入了类型推断，使你可以像调用普通方法一样调用通用方法，而无需在尖括号之间指定类型。考虑下面的示例BoxDemo，它需要Box类：

```java
public class BoxDemo {

  public static <U> void addBox(U u, 
      java.util.List<Box<U>> boxes) {
    Box<U> box = new Box<>();
    box.set(u);
    boxes.add(box);
  }

  public static <U> void outputBoxes(java.util.List<Box<U>> boxes) {
    int counter = 0;
    for (Box<U> box: boxes) {
      U boxContents = box.get();
      System.out.println("Box #" + counter + " contains [" +
             boxContents.toString() + "]");
      counter++;
    }
  }

  public static void main(String[] args) {
    java.util.ArrayList<Box<Integer>> listOfIntegerBoxes =
      new java.util.ArrayList<>();
    BoxDemo.<Integer>addBox(Integer.valueOf(10), listOfIntegerBoxes);
    BoxDemo.addBox(Integer.valueOf(20), listOfIntegerBoxes);
    BoxDemo.addBox(Integer.valueOf(30), listOfIntegerBoxes);
    BoxDemo.outputBoxes(listOfIntegerBoxes);
  }
}
```

以下是此示例的输出：

```reStructuredText
Box #0 contains [10]
Box #1 contains [20]
Box #2 contains [30]
```

通用方法`addBox`定义了一个名为U的类型参数。通常，Java编译器可以推断出通用方法调用的类型参数。因此，在大多数情况下，你不需要指定这些类型参数。例如，为了调用泛型方法`addBox`，你可以用一个类型见证来指定类型参数，如下所示。

```java
BoxDemo.<Integer>addBox(Integer.valueOf(10), listOfIntegerBoxes);
```

或者，如果你省略了类型见证，Java编译器会自动推断出（从方法的参数（实参）中）类型参数是`Integer`。

```java
BoxDemo.addBox(Integer.valueOf(20), listOfIntegerBoxes);
```

#### Type Inference and Instantiation of Generic Classes（泛型类的类型推断和实例化）

你可以用一组空的类型参数（`<>`）替换调用通用类的构造函数所需的类型参数，只要编译器可以从上下文中推断类型参数即可。这对尖括号被非正式地称为菱形。

例如，考虑以下变量声明：

```java
Map<String, List<String>> myMap = new HashMap<String, List<String>>();
```

你可以用一组空的类型参数（`<>`）代替构造函数的参数化类型：

```java
Map<String, List<String>> myMap = new HashMap<>();
```

请注意，要在泛型类实例化过程中利用类型推断的优势，必须使用菱形。在以下示例中，编译器生成未经检查的转换警告，因为`HashMap()`构造函数引用的是`HashMap`原始类型，而不是`Map>`类型：

```java
Map<String, List<String>> myMap = new HashMap(); // unchecked conversion warning
```

#### Type Inference and Generic Constructors of Generic and Non-Generic Classes（泛型和非泛型类的类型推断和泛型构造函数）

请注意，构造函数在通用和非通用类中都可以是通用的（换句话说，声明自己的形式类型参数）。考虑以下示例：

```java
class MyClass<X> {
  <T> MyClass(T t) {
    // ...
  }
}
```

考虑类MyClass的以下实例化：

```java
new MyClass<Integer>("")
```

该语句创建参数化类型`MyClass`的实例；该语句为泛型类`MyClass`的形式类型参数X明确指定类型`Integer`。请注意，此泛型类的构造函数包含一个形式类型参数T。编译器会为该泛型类的构造函数的形式类型参数T推断`String`类型（因为该构造函数的实际参数是`String`对象）。

Java SE 7之前的发行版中的编译器能够推断泛型构造函数的实际类型参数，类似于泛型方法。但是，如果使用菱形（`<>`），则Java SE 7和更高版本中的编译器可以推断要实例化的泛型类的实际类型参数。考虑以下示例：

```java
MyClass<Integer> myObject = new MyClass<>("");
```

在此示例中，编译器为通用类`MyClass`的形式类型参数X推断类型`Integer`。它为该泛型类的构造函数的形式类型参数T推断类型`String`。

> 注意：值得注意的是，推断算法仅使用调用参数，目标类型，并且可能使用明显的预期返回类型。推断算法不使用程序后面的结果。

#### Target Types（目标类型）

Java编译器利用目标类型来推断通用方法调用的类型参数。表达式的目标类型是Java编译器期望的数据类型，具体取决于表达式出现的位置。考虑方法`Collections.emptyList`，该方法声明如下：

```java
static <T> List<T> emptyList();
```

考虑以下赋值语句：

```java
List<String> listOne = Collections.emptyList();
```

该语句需要`List`的实例；此数据类型是目标类型。因为方法`emptyList`返回类型为`List`的值，所以编译器推断类型参数T必须为值`String`。这在Java SE 7和8中都可以使用。或者，你可以使用类型见证并按如下所示指定T的值：

```java
List<String> listOne = Collections.<String>emptyList();
```

但是，在这种情况下，这不是必需的。不过，在其它情况下这是必要的。请考虑以下方法：

```java
void processStringList(List<String> stringList) {
    // process stringList
}
```

假设你要使用空列表调用方法`processStringList`。在Java SE 7中，以下语句不会编译：

```java
processStringList(Collections.emptyList());
```

Java SE 7编译器生成类似于以下内容的错误消息：

```java
List<Object> cannot be converted to List<String>
```

编译器需要类型参数T的值，因此它以值`Object`开头。因此，对`Collections.emptyList`的调用返回类型为`List`的值，该值与方法`processStringList`不兼容。因此，在Java SE 7中，必须指定type参数值的值，如下所示：

```java
processStringList(Collections.<String>emptyList());
```

这在Java SE 8中已经没有必要了，目标类型的概念已经扩展到包括方法参数，例如方法`processStringList`的参数。在这种情况下，`processStringList`需要一个`List`类型的参数。方法`Collections.emptyList`返回一个`List`的值，因此，使用`List`的目标类型，编译器会推断出类型参数T的值为`String`。因此，在Java SE 8中，编译器会编译出以下语句：

```java
processStringList(Collections.emptyList());
```

有关更多信息，请参见[Lambda表达式](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html)中的[目标类型](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html#target-typing)。

## Wildcards（通配符）

在通用代码中，称为通配符的问号（`?`）表示未知类型。通配符可以在多种情况下使用：作为参数，字段或局部变量的类型；有时作为返回类型（尽管更具体的做法是更好的编程习惯）。通配符从不用作泛型方法调用，泛型类实例创建或超类型的类型参数。

以下各节将更详细地讨论通配符，包括上界通配符，下界通配符和通配符捕获。

### Upper Bounded Wildcards（上限通配符）

你可以使用上限通配符来放宽对变量的限制。例如，假设你要编写一种适用于`List`，`List`和`List`的方法；可以使用上限通配符来实现。

要声明上限通配符，请使用通配符（`?`），后跟`extends`关键字，然后是其上限。请注意，在这种情况下，`extends`通常用于表示“扩展”（如在类中）或“实现”（如在接口中）。

要编写在`Number`类型的列表和`Number`的子类型（如`Integer`、`Double`和`Float`）上工作的方法，你会指定`List`。术语`List`比`List`更有限制性，因为前者只匹配`Number`类型的列表，而后者匹配`Number`类型的列表或其任何子类。

考虑以下处理方法：

```java
public static void process(List<? extends Foo> list) { /* ... */ }
```

上限通配符，``，其中Foo是任何类型，匹配Foo和Foo的任何子类型。`process`方法可以以类型Foo的形式访问列表元素。

```java
public static void process(List<? extends Foo> list) {
    for (Foo elem : list) {
        // ...
    }
}
```

在`foreach`子句中，elem变量对列表中的每个元素进行迭代。在Foo类中定义的任何方法现在都可以在elem上使用。

`sumOfList`方法返回一个列表中的数字之和：

```java
public static double sumOfList(List<? extends Number> list) {
    double s = 0.0;
    for (Number n : list)
        s += n.doubleValue();
    return s;
}
```

下面的代码，使用一个`Integer`对象的列表，打印出sum = 6.0。

```java
List<Integer> li = Arrays.asList(1, 2, 3);
System.out.println("sum = " + sumOfList(li));
```

一个`Double`值的列表可以使用同样的`sumOfList`方法。下面的代码打印出sum = 7.0。

```java
List<Double> ld = Arrays.asList(1.2, 2.3, 3.5);
System.out.println("sum = " + sumOfList(ld));
```

### Unbounded Wildcards（无限通配符）

无界通配符类型使用通配符（`?`）来指定，例如`List`。这就是所谓的未知类型的列表。有两种情况下，无界通配符是一种有用的方法。

- 如果你正在编写一个可以使用`Object`类中提供的功能实现的方法。
- 当代码使用通用类中不依赖于类型参数的方法时。例如，`List.size`或`List.clear`。事实上，`Class`之所以这么经常使用，是因为`Class`中的大部分方法都不依赖于T。

考虑以下方法，`printList`：

```java
public static void printList(List<Object> list) {
    for (Object elem : list)
        System.out.println(elem + " ");
    System.out.println();
}
```

`printList`的目标是打印任何类型的列表，但未能实现该目标（它仅打印`Object`实例的列表）；它不能打印`List`、`List`、`List`等，因为它们不是`List`的子类型。要编写通用的`printList`方法，请使用`List`：

```java
public static void printList(List<?> list) {
    for (Object elem: list)
        System.out.print(elem + " ");
    System.out.println();
}
```

因为对于任何具体类型A，`List`是`List`的子类型，所以可以使用`printList`打印任何类型的列表：

```java
List<Integer> li = Arrays.asList(1, 2, 3);
List<String>  ls = Arrays.asList("one", "two", "three");
printList(li);
printList(ls);
```

> 注意：本课的示例中均使用`Arrays.asList`方法。此静态工厂方法将转换指定的数组并返回固定大小的列表。

重要的是要注意`List`和`List`不同。你可以将`Object`或`Object`的任何子类型插入`List`。但是你只能将`null`插入`List`。[通配符使用准则](https://docs.oracle.com/javase/tutorial/java/generics/wildcardGuidelines.html)部分提供了有关如何确定在给定情况下应使用哪种通配符（如果有）的更多信息。

### Lower Bounded Wildcards（下界通配符）

“上限通配符”部分显示，上限通配符将未知类型限制为特定类型或该类型的子类型，并使用`extends`关键字表示。以类似的方式，下限通配符将未知类型限制为特定类型或该类型的超类型。

下限通配符使用通配符（`?`）表示，后跟`super`关键字，后跟下限, 如：``

> 注意：你可以为通配符指定一个上限，也可以指定一个下限，但不能同时指定两者。

假设你要编写一个将`Integer`对象放入列表的方法。为了最大程度地提高灵活性，你希望该方法可用于`List`，`List`和`List`（可以容纳`Integer`值的任何内容）。

要编写对`Integer`的列表和`Integer`的超类型（如`Integer`、`Number`和`Object`）的方法，你可以指定`List`。术语`List`比`List`的限制性更强，因为前者只匹配`Integer`类型的列表，而后者则匹配作为`Integer`的超类型的任何类型的列表。

以下代码将数字1到10添加到列表的末尾：

```java
public static void addNumbers(List<? super Integer> list) {
    for (int i = 1; i <= 10; i++) {
        list.add(i);
    }
}
```

[通配符使用准则](https://docs.oracle.com/javase/tutorial/java/generics/wildcardGuidelines.html)部分提供了有关何时使用上限通配符以及何时使用下限通配符的指南。

### Wildcards and Subtyping（通配符和子类型）

如泛型，继承和子类型中所述，泛型类或接口不仅仅因为它们的类型之间存在关系而相关。但是，你可以使用通配符在通用类或接口之间创建关系。

给定以下两个常规（非泛型）类：

```java
class A { /* ... */ }
```

编写以下代码是合理的：

```
B b = new B();
A a = b;
复制代码
```

此示例显示常规类的继承遵循此子类型规则：如果B扩展了A，则类B是类A的子类型。此规则不适用于通用类型：

```java
List<B> lb = new ArrayList<>();
List<A> la = lb;   // compile-time error
```

假定`Integer`是`Number`的子类型，则`List`和`List`之间是什么关系？



![img](data:image/gif;base64,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)



尽管`Integer`是`Number`的子类型，但`List`不是`List`的子类型，实际上，这两种类型无关。`List`和`List`的公共父级是`List`。

为了在这些类之间创建关系，以便代码可以通过`List`的元素访问`Number`的方法，请使用上限通配符：

```java
List<? extends Integer> intList = new ArrayList<>();
List<? extends Number>  numList = intList;  // OK. List<? extends Integer> is a subtype of List<? extends Number>
```

由于`Integer`是`Number`的子类型，并且numList是`Number`对象的列表，因此intList（一个`Integer`对象的列表）和numList之间现在存在关系。下图显示了使用上下限通配符声明的几个`List`类之间的关系。



![img](data:image/gif;base64,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)



[通配符使用准则](https://docs.oracle.com/javase/tutorial/java/generics/wildcardGuidelines.html)部分提供了有关使用上下限通配符的后果的更多信息。

### Wildcard Capture and Helper Methods（通配符捕获和帮助方法）

在某些情况下，编译器会推断通配符的类型。例如，可以将列表定义为`List`，但是在评估表达式时，编译器会从代码中推断出特定类型。这种情况称为通配符捕获。

在大多数情况下，你无需担心通配符捕获，除非你看到包含短语“capture of”的错误消息。

WildcardError示例在编译时产生捕获错误：

```java
import java.util.List;

public class WildcardError {

    void foo(List<?> i) {
        i.set(0, i.get(0));
    }
}
```

在此示例中，编译器将i输入参数处理为`Object`类型。当`foo`方法调用`List.set(int, E)`时，编译器无法确认要插入列表中的对象的类型，并产生错误。当发生这种类型的错误时，通常意味着编译器认为你正在将错误的类型分配给变量。为此，将泛型添加到Java语言中（以便在编译时强制类型安全）。

由Oracle的JDK 7 javac实现编译时，WildcardError示例将生成以下错误：

```reStructuredText
WildcardError.java:6: error: method set in interface List<E> cannot be applied to given types;
    i.set(0, i.get(0));
     ^
  required: int,CAP#1
  found: int,Object
  reason: actual argument Object cannot be converted to CAP#1 by method invocation conversion
  where E is a type-variable:
    E extends Object declared in interface List
  where CAP#1 is a fresh type-variable:
    CAP#1 extends Object from capture of ?
1 error
```

在此示例中，代码正在尝试执行安全操作，那么如何解决编译器错误？你可以通过编写捕获通配符的私有帮助器方法来修复它。在这种情况下，你可以通过创建私有帮助器方法`fooHelper`来解决此问题，如WildcardFixed中所示：

```java
public class WildcardFixed {

    void foo(List<?> i) {
        fooHelper(i);
    }


    // Helper method created so that the wildcard can be captured
    // through type inference.
    private <T> void fooHelper(List<T> l) {
        l.set(0, l.get(0));
    }

}
```

由于使用了辅助方法，编译器在调用中使用推断来确定T是CAP＃1（捕获变量）。该示例现在可以成功编译。

按照约定，辅助方法通常命名为`originalMethodNameHelper`。

现在考虑一个更复杂的示例WildcardErrorBad：

```java
import java.util.List;

public class WildcardErrorBad {

    void swapFirst(List<? extends Number> l1, List<? extends Number> l2) {
      Number temp = l1.get(0);
      l1.set(0, l2.get(0)); // expected a CAP#1 extends Number,
                            // got a CAP#2 extends Number;
                            // same bound, but different types
      l2.set(0, temp);	    // expected a CAP#1 extends Number,
                            // got a Number
    }
}
```

在此的示例代码正在尝试不安全的操作。例如，考虑对swapFirst方法的以下调用：

```java
List<Integer> li = Arrays.asList(1, 2, 3);
List<Double>  ld = Arrays.asList(10.10, 20.20, 30.30);
swapFirst(li, ld);
```

虽然`List`和`List`都满足了`List`的条件，但从`Integer`值列表中提取一个项并试图将其放入`Double`值列表中显然是不正确的。

使用Oracle的JDK javac编译器编译代码会产生以下错误：

```reStructuredText
WildcardErrorBad.java:7: error: method set in interface List<E> cannot be applied to given types;
      l1.set(0, l2.get(0)); // expected a CAP#1 extends Number,
        ^
  required: int,CAP#1
  found: int,Number
  reason: actual argument Number cannot be converted to CAP#1 by method invocation conversion
  where E is a type-variable:
    E extends Object declared in interface List
  where CAP#1 is a fresh type-variable:
    CAP#1 extends Number from capture of ? extends Number
WildcardErrorBad.java:10: error: method set in interface List<E> cannot be applied to given types;
      l2.set(0, temp);      // expected a CAP#1 extends Number,
        ^
  required: int,CAP#1
  found: int,Number
  reason: actual argument Number cannot be converted to CAP#1 by method invocation conversion
  where E is a type-variable:
    E extends Object declared in interface List
  where CAP#1 is a fresh type-variable:
    CAP#1 extends Number from capture of ? extends Number
WildcardErrorBad.java:15: error: method set in interface List<E> cannot be applied to given types;
        i.set(0, i.get(0));
         ^
  required: int,CAP#1
  found: int,Object
  reason: actual argument Object cannot be converted to CAP#1 by method invocation conversion
  where E is a type-variable:
    E extends Object declared in interface List
  where CAP#1 is a fresh type-variable:
    CAP#1 extends Object from capture of ?
3 errors
```

没有解决此问题的辅助方法，因为代码根本上是错误的。

### Guidelines for Wildcard Use（通配符使用准则）

在学习使用泛型编程时，更令人困惑的方面之一是确定何时使用上限的通配符以及何时使用下限的通配符。此页面提供了一些在设计代码时要遵循的准则。

为了便于讨论，将变量视为提供以下两个功能之一将很有帮助：

**“输入”变量**：输入变量将数据提供给代码。想象一个具有两个参数的复制方法：`copy(src, dest)`。src参数提供要复制的数据，因此它是输入参数。

**“输出”变量**：输出变量保存要在其它地方使用的数据。在复制示例`copy(src, dest)`中，dest参数接受数据，因此它是输出参数。

当然，某些变量既用于“输入”又用于“输出”目的（准则中也解决了这种情况）。

在决定是否使用通配符以及哪种类型的通配符时，可以使用“输入”和“输出”原理。以下列表提供了要遵循的准则：

> 通配符准则：
>
> - 使用上限通配符定义输入变量，使用`extends`关键字。
> - 使用下限通配符定义输出变量，使用`super`关键字。
> - 如果可以使用`Object`类中定义的方法访问输入变量，请使用无界通配符（`?`）。
> - 如果代码需要同时使用输入和输出变量来访问变量，则不要使用通配符。

这些准则不适用于方法的返回类型。应该避免使用通配符作为返回类型，因为这会迫使程序员使用代码来处理通配符。

由`List`定义的列表可以被非正式地认为是只读的，但这并不是一个严格的保证。假设你有以下两个类。

```java
class NaturalNumber {

    private int i;

    public NaturalNumber(int i) { this.i = i; }
    // ...
}

class EvenNumber extends NaturalNumber {

    public EvenNumber(int i) { super(i); }
    // ...
}
```

考虑以下代码：

```java
List<EvenNumber> le = new ArrayList<>();
List<? extends NaturalNumber> ln = le;
ln.add(new NaturalNumber(35));  // compile-time error
```

因为`List`是`List`的一个子类型，所以可以将le赋给ln。但不能用ln将自然数添加到偶数列表中。可以对该列表进行以下操作。

- 可以添加`null`。
- 可以调用`clear`。
- 可以获取迭代器（`iterator`）和调用`remove`。
- 可以捕获通配符和写入从列表中读取的元素。

你可以看到由`List`定义的列表不是最严格意义上的只读，但你可能会这样想，因为你不能在列表中存储一个新的元素或改变一个现有的元素。

## Type Erasure（类型擦除）

Java语言引入了泛型，以在编译时提供更严格的类型检查并支持泛型编程。 为了实现泛型，Java编译器将类型擦除应用于：

- 如果类型参数不受限制，则将通用类型中的所有类型参数替换为其边界（上下限）或`Object`。因此，产生的字节码仅包含普通的类，接口和方法。
- 必要时插入类型转换，以保持类型安全。
- 生成桥接方法以在扩展的泛型类型中保留多态。

类型擦除可确保不会为参数化类型创建新的类；因此，泛型不会产生运行时开销。

### Erasure of Generic Types（泛型类型的擦除）

在类型擦除过程中，Java编译器将擦除所有类型参数，如果类型参数是有界的，则将每个参数替换为其第一个边界；如果类型参数是无界的，则将其替换为`Object`。

考虑以下表示单个链接列表中的节点的通用类：

```java
public class Node<T> {

    private T data;
    private Node<T> next;

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() { return data; }
    // ...
}
```

由于类型参数T是无界的，因此Java编译器将其替换为`Object`：

```java
public class Node {

    private Object data;
    private Node next;

    public Node(Object data, Node next) {
        this.data = data;
        this.next = next;
    }

    public Object getData() { return data; }
    // ...
}
```

在下面的示例中，通用Node类使用限定类型参数：

```java
public class Node<T extends Comparable<T>> {

    private T data;
    private Node<T> next;

    public Node(T data, Node<T> next) {
        this.data = data;
        this.next = next;
    }

    public T getData() { return data; }
    // ...
}
```

Java编译器将绑定类型参数T替换为第一个绑定类`Comparable`：

```java
public class Node {

    private Comparable data;
    private Node next;

    public Node(Comparable data, Node next) {
        this.data = data;
        this.next = next;
    }

    public Comparable getData() { return data; }
    // ...
}
```

### Erasure of Generic Methods（通用方法的擦除）

Java编译器还会擦除通用方法参数中的类型参数。考虑以下通用方法：

```java
// Counts the number of occurrences of elem in anArray.
//
public static <T> int count(T[] anArray, T elem) {
    int cnt = 0;
    for (T e : anArray)
        if (e.equals(elem))
            ++cnt;
        return cnt;
}
```

由于T是无界的，因此Java编译器将其替换为`Object`：

```java
public static int count(Object[] anArray, Object elem) {
    int cnt = 0;
    for (Object e : anArray)
        if (e.equals(elem))
            ++cnt;
        return cnt;
}
```

假设定义了以下类：

```java
class Shape { /* ... */ }
class Circle extends Shape { /* ... */ }
class Rectangle extends Shape { /* ... */ }
```

你可以编写一个通用方法来绘制不同的形状：

```java
public static <T extends Shape> void draw(T shape) { /* ... */ }
```

Java编译器用Shape替换T：

```java
public static void draw(Shape shape) { /* ... */ }
```

### Effects of Type Erasure and Bridge Methods（类型擦除和桥接方法的影响）

有时类型擦除会导致可能无法预料的情况。以下示例显示了这种情况的发生方式。该示例（在“[桥接方法](https://docs.oracle.com/javase/tutorial/java/generics/bridgeMethods.html#bridgeMethods)”中进行了介绍）展示了编译器有时如何创建一个综合方法，称为桥接方法，作为类型擦除过程的一部分。

给定以下两类：

```java
public class Node<T> {

    public T data;

    public Node(T data) { this.data = data; }

    public void setData(T data) {
        System.out.println("Node.setData");
        this.data = data;
    }
}

public class MyNode extends Node<Integer> {
    public MyNode(Integer data) { super(data); }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }
}
```

考虑以下代码：

```java
MyNode mn = new MyNode(5);
Node n = mn;            // A raw type - compiler throws an unchecked warning
n.setData("Hello");     
Integer x = mn.data;    // Causes a ClassCastException to be thrown.
```

类型擦除后，此代码变为：

```java
MyNode mn = new MyNode(5);
Node n = (MyNode)mn;         // A raw type - compiler throws an unchecked warning
n.setData("Hello");
Integer x = (String)mn.data; // Causes a ClassCastException to be thrown.
```

执行代码时会发生以下情况：

- `n.setData("Hello");`导致在MyNode类的对象上执行`setData(Object)`方法（MyNode类继承了Node的`setData(Object)`）。
- 在`setData(Object)`的主体中，将n引用的对象的数据字段分配给`String`。
- 可以访问通过mn引用的同一对象的数据字段，并且该字段应该是整数（因为mn是MyNode，它是`Node`）。
- 尝试将`String`分配给`Integer`会导致Java编译器在分配时插入的强制转换导致`ClassCastException`。

#### Bridge Methods（桥接方法）

在编译扩展参数化类或实现参数化接口的类或接口时，作为类型擦除过程的一部分，编译器可能需要创建一个称为桥接方法的综合方法。你通常不必担心桥接方法，但是如果其中一个出现在堆栈跟踪中，你可能会感到困惑。

类型擦除后，Node和MyNode类变为：

```java
public class Node {

    public Object data;

    public Node(Object data) { this.data = data; }

    public void setData(Object data) {
        System.out.println("Node.setData");
        this.data = data;
    }
}

public class MyNode extends Node {

    public MyNode(Integer data) { super(data); }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }
}
```

类型擦除后，方法签名不匹配。Node方法变为`setData(Object)`，而MyNode方法变为`setData(Integer)`。因此，MyNode的`setData`方法不会覆盖Node的`setData`方法。

为了解决此问题并在类型擦除后保留泛型类型的多态性，Java编译器生成了一个桥接方法来确保子类型能够按预期工作。对于MyNode类，编译器为`setData`生成以下桥接方法：

```java
class MyNode extends Node {

    // Bridge method generated by the compiler
    //
    public void setData(Object data) {
        setData((Integer) data);
    }

    public void setData(Integer data) {
        System.out.println("MyNode.setData");
        super.setData(data);
    }

    // ...
}
```

可以看到，在类型擦除后，MyNode类的桥接方法（`setData(Object)`）与Node类的`setData(Object)`方法具有相同的方法签名，它委托给原来的`setData(Integer)`方法。

### Non-Reifiable Types（不可具体化类型）

“[类型擦除](https://docs.oracle.com/javase/tutorial/java/generics/erasure.html)”部分讨论了编译器删除与类型参数和类型参数有关的信息的过程。类型擦除的结果与变量参数（也称为varargs）方法有关，这些方法的varargs形式参数具有不可更改的类型。有关varargs方法的更多信息，请参见[将信息传递给方法或构造方法](https://docs.oracle.com/javase/tutorial/java/javaOO/arguments.html)中的[任意参数数目](https://docs.oracle.com/javase/tutorial/java/javaOO/arguments.html#varargs)。

此页面涵盖以下主题：

- 不可具体化类型。
- 堆污染。
- 具有不可具体化形式参数的Varargs方法的潜在漏洞。
- 防止使用不可具体化形式参数的Varargs方法发出警告。

#### Non-Reifiable Types（不可具体化类型）

具体化类型是其类型信息在运行时完全可用的类型。这包括基本类型，非通用类型，原始（raw）类型以及未绑定通配符的调用。

非具体化类型是指在编译时通过类型擦除法删除了信息的类型（对通用类型的调用没有被定义为非绑定通配符）。非具体化类型在运行时并不具备所有的信息。非具体化类型的例子是`List`和`List`；JVM在运行时无法区分这些类型。正如[对通用类型的限制](https://docs.oracle.com/javase/tutorial/java/generics/restrictions.html)中所示，在某些情况下，非具体化类型不能使用：例如，在`instanceof`表达式中，或者作为数组中的元素。

#### Heap Pollution（堆污染）

当参数化类型的变量引用的对象不是该参数化类型的对象时，就会发生堆污染。如果程序执行某些操作会在编译时产生未经检查的警告，则会发生这种情况。如果在编译时（在编译时类型检查规则的范围内）或在运行时，无法确定涉及参数化类型的操作（例如，强制转换或方法调用）的正确性，则会生成未经检查的警告。例如，当混合原始（raw）类型和参数化类型时，或者执行未经检查的强制转换时，就会发生堆污染。

在正常情况下，当同时编译所有代码时，编译器会发出未经检查的警告，以引起你对潜在堆污染的注意。如果分别编译代码部分，则很难检测到堆污染的潜在风险。如果确保代码在没有警告的情况下进行编译，则不会发生堆污染。

#### Potential Vulnerabilities of Varargs Methods with Non-Reifiable Formal Parameters（具有不可具体化形式参数的Varargs方法的潜在漏洞）

包含vararg输入参数的泛型方法可能导致堆污染。

考虑以下ArrayBuilder类：

```java
public class ArrayBuilder {

  public static <T> void addToList (List<T> listArg, T... elements) {
    for (T x : elements) {
      listArg.add(x);
    }
  }

  public static void faultyMethod(List<String>... l) {
    Object[] objectArray = l;     // Valid
    objectArray[0] = Arrays.asList(42);
    String s = l[0].get(0);       // ClassCastException thrown here
  }

}
```

以下示例HeapPollutionExample使用ArrayBuiler类：

```java
public class HeapPollutionExample {

  public static void main(String[] args) {

    List<String> stringListA = new ArrayList<String>();
    List<String> stringListB = new ArrayList<String>();

    ArrayBuilder.addToList(stringListA, "Seven", "Eight", "Nine");
    ArrayBuilder.addToList(stringListB, "Ten", "Eleven", "Twelve");
    List<List<String>> listOfStringLists =
      new ArrayList<List<String>>();
    ArrayBuilder.addToList(listOfStringLists,
      stringListA, stringListB);

    ArrayBuilder.faultyMethod(Arrays.asList("Hello!"), Arrays.asList("World!"));
  }
}
```

编译后，`ArrayBuilder.addToList`方法的定义会产生以下警告：

```java
warning: [varargs] Possible heap pollution from parameterized vararg type T
```

当编译器遇到varargs方法时，它将varargs形式参数转换为数组。但是，Java编程语言不允许创建参数化类型的数组。在方法`ArrayBuilder.addToList`中，编译器将varargs形式参数`T ...`元素转换为形式参数`T[]`元素，即数组。但是，由于类型擦除，编译器将varargs形式参数转换为`Object[]`元素。因此，存在堆污染的可能性。

以下语句将varargs形式参数l分配给对象数组objectArgs：

```java
Object[] objectArray = l;
```

该语句可能会导致堆污染。可以将与varargs形式参数l的参数化类型匹配的值分配给变量objectArray，从而可以将其分配给l。但是，编译器不会在此语句上生成未经检查的警告。当编译器将varargs形式参数`List... l`转换为形式参数`List[] l`时，已经生成了警告。此声明有效；变量l具有类型`List[]`，它是`Object[]`的子类型。

因此，如果将任何类型的`List`对象分配给objectArray数组的任何数组组件，则编译器不会发出警告或错误，如以下语句所示：

```java
objectArray[0] = Arrays.asList(42);
```

该语句将`List`对象分配给objectArray数组的第一个数组组件，该`List`对象包含一个`Integer`类型的对象。

假设你使用以下语句调用`ArrayBuilder.faultyMethod`：

```java
ArrayBuilder.faultyMethod(Arrays.asList("Hello!"), Arrays.asList("World!"));
```

在运行时，JVM在以下语句中引发`ClassCastException`：

```java
// ClassCastException thrown here
String s = l[0].get(0);
```

存储在变量l的第一个数组组件中的对象的类型为`List`，但是此语句期望使用类型为`List`的对象。

#### Prevent Warnings from Varargs Methods with Non-Reifiable Formal Parameters（防止使用不可具体化形式参数的Varargs方法发出警告）

如果你声明具有参数化类型参数的varargs方法，并确保由于对varargs形式参数的处理不当，该方法的主体不会引发`ClassCastException`或其它类似的异常，则可以避免警告编译器通过为静态和非构造方法声明添加以下注解，为此类varargs方法生成：

```java
@SafeVarargs
```

`@SafeVarargs`注解是该方法契约的书面部分；该注解断言该方法的实现不会不适当地处理varargs形式参数。

尽管不太理想，但也可以通过在方法声明中添加以下内容来抑制此类警告：

```java
@SuppressWarnings({"unchecked", "varargs"})
```

但是，这种方法不能抑制从该方法的调用站点生成的警告。如果你不熟悉`@SuppressWarnings`语法，请参阅[注解](https://docs.oracle.com/javase/tutorial/java/annotations/index.html)。

## Restrictions on Generics（对泛型的限制）

为了有效地使用Java泛型，必须考虑以下限制：

- 无法实例化具有基本类型的泛型类型。
- 无法创建类型参数的实例。
- 无法声明类型为类型参数的静态字段。
- 无法将Casts或instanceof与参数化类型一起使用。
- 无法创建参数化类型的数组。
- 无法创建，捕获或抛出参数化类型的对象。
- 无法重载每个重载的形式参数类型都擦除为相同原始（raw）类型的方法。

#### Cannot Instantiate Generic Types with Primitive Types（无法实例化具有基本类型的泛型类型）

考虑以下参数化类型：

```java
class Pair<K, V> {

    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    // ...
}
```

创建对对象时，不能用基本类型替换类型参数K或V：

```java
Pair<int, char> p = new Pair<>(8, 'a');  // compile-time error
```

你只能将非基本类型替换为类型参数K和V：

```java
Pair<Integer, Character> p = new Pair<>(8, 'a');
```

请注意，Java编译器自动将`8`装箱为`Integer.valueOf(8)`，将`'a'`装箱为`Character('a')`：

```java
Pair<Integer, Character> p = new Pair<>(Integer.valueOf(8), new Character('a'));
```

有关自动装箱的更多信息，请参阅[数字和字符串](https://docs.oracle.com/javase/tutorial/java/data/index.html)课程中的[自动装箱和拆箱](https://docs.oracle.com/javase/tutorial/java/data/autoboxing.html)。

#### Cannot Create Instances of Type Parameters（无法创建类型参数的实例）

你不能创建类型参数的实例。例如，以下代码会导致编译时错误：

```java
public static <E> void append(List<E> list) {
    E elem = new E();  // compile-time error
    list.add(elem);
}
```

解决方法是，可以通过反射创建类型参数的对象：

```java
public static <E> void append(List<E> list, Class<E> cls) throws Exception {
    E elem = cls.newInstance();   // OK
    list.add(elem);
}
```

你可以按以下方式调用`append`方法：

```java
List<String> ls = new ArrayList<>();
append(ls, String.class);
```

#### Cannot Declare Static Fields Whose Types are Type Parameters（无法声明类型为类型参数的静态字段）

类的静态字段是该类的所有非静态对象共享的类级别变量。因此，不允许使用类型参数的静态字段。考虑以下类别：

```java
public class MobileDevice<T> {
    private static T os;

    // ...
}
```

如果允许使用类型参数的静态字段，那么以下代码将被混淆：

```java
MobileDevice<Smartphone> phone = new MobileDevice<>();
MobileDevice<Pager> pager = new MobileDevice<>();
MobileDevice<TabletPC> pc = new MobileDevice<>();
```

因为静态字段os由Smartphone、Pager和TabletPC共享，所以os的实际类型是什么？它不能同时是Smartphone、Pager和TabletPC。因此，你无法创建类型参数的静态字段。

#### Cannot Use Casts or instanceof With Parameterized Types（无法将Casts或instanceof与参数化类型一起使用）

因为Java编译器会擦除通用代码中的所有类型参数，所以你无法验证在运行时使用的是通用类型的参数化类型：

```java
public static <E> void rtti(List<E> list) {
    if (list instanceof ArrayList<Integer>) {  // compile-time error
        // ...
    }
}
```

传递给`rtti`方法的参数化类型的集合是：

```java
S = { ArrayList<Integer>, ArrayList<String> LinkedList<Character>, ... }
```

运行时不跟踪类型参数，因此无法区分`ArrayList`和`ArrayList`之间的区别。你最多可以做的是使用无界通配符来验证列表是否为ArrayList：

```java
public static void rtti(List<?> list) {
    if (list instanceof ArrayList<?>) {  // OK; instanceof requires a reifiable type
        // ...
    }
}
```

通常，除非使用不受限制的通配符对其进行参数化，否则无法将其转换为参数化类型。例如：

```java
List<Integer> li = new ArrayList<>();
```

但是，在某些情况下，编译器知道类型参数始终有效并允许强制转换。例如：

```java
List<String> l1 = ...;
ArrayList<String> l2 = (ArrayList<String>)l1;  // OK
```

#### Cannot Create Arrays of Parameterized Types（无法创建参数化类型的数组）

你不能创建参数化类型的数组。例如，以下代码无法编译：

```java
List<Integer>[] arrayOfLists = new List<Integer>[2];  // compile-time error
```

以下代码说明了将不同类型插入到数组中时发生的情况：

```java
Object[] strings = new String[2];
strings[0] = "hi";   // OK
strings[1] = 100;    // An ArrayStoreException is thrown.
```

如果你对通用列表尝试相同的操作，则会出现问题：

```java
Object[] stringLists = new List<String>[];  // compiler error, but pretend it's allowed
stringLists[0] = new ArrayList<String>();   // OK
stringLists[1] = new ArrayList<Integer>();  // An ArrayStoreException should be thrown,
                                            // but the runtime can't detect it.
```

如果允许参数化列表的数组，那么前面的代码将无法抛出所需的`ArrayStoreException`。

#### Cannot Create, Catch, or Throw Objects of Parameterized Types（无法创建，捕获或抛出参数化类型的对象）

泛型类不能直接或间接扩展`Throwable`类。例如，以下类将无法编译：

```java
// Extends Throwable indirectly
class MathException<T> extends Exception { /* ... */ }    // compile-time error

// Extends Throwable directly
class QueueFullException<T> extends Throwable { /* ... */ // compile-time error
```

方法无法捕获类型参数的实例：

```java
public static <T extends Exception, J> void execute(List<J> jobs) {
    try {
        for (J job : jobs)
            // ...
    } catch (T e) {   // compile-time error
        // ...
    }
}
```

但是，你可以在`throws`子句中使用类型参数：

```java
class Parser<T extends Exception> {
    public void parse(File file) throws T {     // OK
        // ...
    }
}
```

#### Cannot Overload a Method Where the Formal Parameter Types of Each Overload Erase to the Same Raw Type（无法重载每个重载的形式参数类型都擦除为相同原始（raw）类型的方法）

一个类不能有两个重载的方法，这些方法在类型擦除后将具有相同的签名。

```java
public class Example {
    public void print(Set<String> strSet) { }
    public void print(Set<Integer> intSet) { }
}
```

重载将共享相同的类文件表示形式，并且将生成编译时错误。
