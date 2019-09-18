cmake_minimum_required(VERSION 3.6.1)

project(HELLO)
set(SRC_LIST main.c hello.c)
add_executable(hello ${SRC_LIST})