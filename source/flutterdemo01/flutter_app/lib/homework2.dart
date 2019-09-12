/**
 * 设计一个表示分数的类Fraction。这个类用两个int类型的变量分别表示分子和分母。这个类的构造函数是：Fraction(num a, num b)构造一个x/y的分数。这个类要提供以下的功能：
    将自己与另一个分数相加，产生一个新的Fraction的对象；
    将自己和另一个分数相乘，产生一个新的Fraction的对象；
    将自己以分子/分母的形式输出，如果分数是1/1，应该输出1；当分子大于分母时，不需要提出整数部分，即3/1是一个正确的输出；输出时需要为最简形式，如：2/4 应该是 1/2
 *
 */
class Fraction {
  num x;
  num y;

  Fraction(num x, num y) {
    this.x = x;
    this.y = y;
  }

  Fraction operator +(Fraction point) {
    Fraction temp = new Fraction(0, 0);
    temp.x = x * point.x;
    temp.x = temp.x / gcd(x, point.x);
    temp.y = (this.y * (temp.x / this.x) + point.y * (temp.x / point.x));
    return temp;
  }

  Fraction operator *(Fraction point) {
    Fraction temp = new Fraction((this.x * point.x), (this.y * point.y));
    return temp;
  }

  num gcd(num a, num b) {
    num temp;
    if (a < b) {
      temp = a;
      a = b;
      b = temp;
    }
    while (b != 0) {
      temp = b;
      b = a % b;
      a = temp;
    }
    return a;
  }

  void print1() {
    //分数在输出的时候进行化简
    num temp = gcd(this.x, this.y);
    this.x = this.x / temp;
    this.y = this.y / temp;
    if (x == y) {
      print("1");
    } else {
      print(x.toString() + "/" + y.toString());
    }
  }
}

void main() {
  Fraction a1 = new Fraction(3, 1);
  Fraction a2 = new Fraction(4, 2);
  Fraction fraction = a1 + a2;
  Fraction fraction2 = a1 * a2;

  fraction.print1();
  fraction2.print1();


//  var list = [1, 2, 3];
//  list.forEach(print);
//  var p = print;
//  list.forEach(p);
}
