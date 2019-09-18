cmake_minimum_required(VERSION 3.6.1)

project(HELLO)

#add_subdirectory(src)
#add_subdirectory(libhello)
#如果想让可执行文件在 bin 目录，库文件在 lib 目录
add_subdirectory(src bin)
add_subdirectory(libhello lib)