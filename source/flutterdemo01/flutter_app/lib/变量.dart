import 'dart:io';

void main() {
  Object i = "sss";
  dynamic z = "";
  print(i);
}

void readFile2(void callback(s)) {
  String result;
  new File("/Users/xiang/enjoy/a.txt").readAsString().then((s) {
    result += s;
    return new File("/Users/xiang/enjoy/a.txt").readAsString();
  }).then((s) {
    result += s;
  }).whenComplete((){
    print(result);
    callback(result);
  });
}
