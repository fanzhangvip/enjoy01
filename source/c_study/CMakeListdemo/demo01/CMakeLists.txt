cmake_minimum_required(VERSION 3.6.1)

#set(CMAKE_C_FLAGS "-g -Wall -I D:\\Lance\\MinGW\\include -L D:\\Lance\\MinGW\\lib")

#set(CMAKE_CXX_FLAGS "-g -Wall -I D:\\Lance\\MinGW\\include -L D:\\Lance\\MinGW\\lib")

project(HELLO LANGUAGES C)


#find_package(LLVM REQUIRED CONFIG)

#SET (CMAKE_C_COMPILER             "D:\\LLVM\\bin\\clang")
#SET (CMAKE_C_FLAGS                "-Wall -std=c99")
#SET (CMAKE_C_FLAGS_DEBUG          "-g")
#SET (CMAKE_C_FLAGS_MINSIZEREL     "-Os -DNDEBUG")
#SET (CMAKE_C_FLAGS_RELEASE        "-O4 -DNDEBUG")
#SET (CMAKE_C_FLAGS_RELWITHDEBINFO "-O2 -g")
#
#SET (CMAKE_CXX_COMPILER             "D:\\LLVM\\bin\\clang++")
#SET (CMAKE_CXX_FLAGS                "-Wall")
#SET (CMAKE_CXX_FLAGS_DEBUG          "-g")
#SET (CMAKE_CXX_FLAGS_MINSIZEREL     "-Os -DNDEBUG")
#SET (CMAKE_CXX_FLAGS_RELEASE        "-O4 -DNDEBUG")
#SET (CMAKE_CXX_FLAGS_RELWITHDEBINFO "-O2 -g")
#
#SET (CMAKE_AR      "D:\\LLVM\\bin\\ar")
#SET (CMAKE_LINKER  "D:\\LLVM\\bin\\ld")
#SET (CMAKE_NM      "D:\\LLVM\\bin\\nm")
#SET (CMAKE_OBJDUMP "D:\\LLVM\\bin\\objdump")
#SET (CMAKE_RANLIB  "D:\\LLVM\\bin\\ranlib")

message(STATUS "This is BINARY dir " ${PROJECT_BINARY_DIR})
message(STATUS "This is SOURCE dir " ${PROJECT_SOURCE_DIR})
message(STATUS "Found LLVM ${LLVM_PACKAGE_VERSION}")
message(STATUS "Using LLVMConfig.cmake in: ${LLVM_DIR}")



#set(CMAKE_CXX_COMPILER "clang")
#set(CMAKE_C_COMPILER "clang")
set(CMAKE_C_STANDARD 99)
set(SRC_LIST main.c)

#使用cmake编译 一
#cmake .. -G "MinGW Makefiles" -D"CMAKE_MAKE_PROGRAM:PATH=D:\Lance\MinGW\bin\make.exe"
#make

#使用cmake编译 二
#cmake .. -G "Ninja"
#ninja


add_executable(hello main.c)


# Link against LLVM libraries
