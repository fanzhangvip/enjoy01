void main() {
  List num = [-1, 6, 8, 4, 4, 2, 3, 1, 1, 0, 9];
  bool flag = true;

  for (int i = 0; i < num.length - 1; i++) {
    flag = false;
    for (int j = 0; j < num.length - 1 - i; j++) {
      if (num[j] > num[j + 1]) {
        int temp = num[j];
        num[j] = num[j + 1];
        num[j + 1] = temp;
        flag = true;
      }
    }
    if (!flag) {
      break;
    }
  }
  print(num);
}
