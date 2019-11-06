cmake_minimum_required(VERSION 3.6.1)
project(c_study LANGUAGES C)

#手动编译
#cmake .. -G "MinGW Makefiles"
#make

#使用cmake编译 二
#cmake .. -G "Ninja"
#ninja

#cmake.exe" -DCMAKE_BUILD_TYPE=Debug -G "CodeBlocks - MinGW Makefiles" F:\CWork\testc

#set(CMAKE_C_FLAGS "-g -Wall -I D:\\Lance\\MinGW\\include -L D:\\Lance\\MinGW\\lib")

#set(CMAKE_CXX_FLAGS "-g -Wall -I D:\\Lance\\MinGW\\include -L D:\\Lance\\MinGW\\lib")

set(CMAKE_C_FLAGS "-g -Wall")

set(CMAKE_VERBOSE_MAKEFILE on) #显示执行构建过程中详细的信息
#修改cmakelsit.txt配置文件：
#MATH(EXPR stack_size "4*1024*1024")
#set(CMAKE_EXE_LINKER_FLAGS "-Wl,--stack,${stack_size}")

message(STATUS ${PROJECT_SOURCE_DIR})
message(STATUS ${PROJECT_BINARY_DIR})

set(CMAKE_C_STANDARD 99)

#add_subdirectory(iodemo/src)
add_subdirectory(vip_day_1/src)
#add_subdirectory(vip_day_2_1/src)
#add_subdirectory(vip_day_2_2/src)
#add_subdirectory(vip_day_2_2_homework/src)
#add_subdirectory(vip_day_3_1/src)
#add_subdirectory(vip_day_4_1/src)
#add_subdirectory(stringdemo)
#add_subdirectory(vip_day_5_1)
#add_subdirectory(filedemo)

#add_executable(c_study main.c.c)
#add_executable(c_study zhizhen.c)
#add_executable(demo01 iodemo/src/shuchu.c)
#add_executable(c_study goto.c)

## 假设你的静态库的名字是 StaticTest.a
##那么你就这么设置
## 1. 引入静态库
## IMPORTED: 表示静态库是以导入的形式添加进来(预编译静态库)
#add_library(StaticTest STATIC IMPORTED)
## 2. 设置静态库的导入路径
#set_target_properties(StaticTest PROPERTIES
#        IMPORTED_LOCATION ${CMAKE_SOURCE_DIR}/src/main/static/${CMAKE_ANDROID_ARCH_ABI}/libStaticTest.a)
## 3. 生成native-lib动态库需要用到Test StaticTest log动态或者静态库
#target_link_libraries(
#        native-lib
#        Test # libTest.so 可以去掉lib与.so
#        StaticTest
#        log)