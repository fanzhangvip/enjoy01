cmake_minimum_required(VERSION 3.6.1)

set(LIB_SRC hello.c)

add_library(libhello ${LIB_SRC})

set_target_properties(libhello PROPERTIES OUTPUT_NAME "hello")
