#include "Person.h"


void objTest(){
    int mArraySize = 2;

    Person myOperator(mArraySize);
    cout <<"=========="<<endl;

    Person person {23, "biter"};
}

void objTest1(){
    Person *parray = new Person[2];
    delete[] parray;
}

int main() {


    objTest();
//   objTest1();
    cout <<"main 尾部"<<endl;
//    myOperator[1] = 1;
//    myOperator[1] = "binny";

//    cout << person << endl;

//    Person person1(person);
//    cout << myOperator[0] << endl;
//    cout << person << endl;
//    myOperator[0];
//    myOperator[0] = person;
//    cout << myOperator[1] << endl;




//    myOperator[0] = myOperator[1];

//    myOperator.show();

//    std::cout << "myOperator[0] == myOperator[1] 是否相等？" << (myOperator[0] == myOperator[1]) << std::endl;
//    std::cout << "myOperator[0] == person1 是否相等？" << (myOperator[0] == person1) << std::endl;
//    std::cout << "myOperator[0] != myOperator[1] 是否不相等？" << (myOperator[0] != myOperator[1]) << std::endl;
//    std::cout << "myOperator[0] != person 是否不相等？" << (myOperator[0] != person) << std::endl;
    return 0;
}