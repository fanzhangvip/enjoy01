cmake_minimum_required(VERSION 3.4.1)
# 定义了一个libgif_src
file(GLOB libgif_src ${CMAKE_SOURCE_DIR}/giflib/*.c)#libgif_src 存储了giflib/*.c

include_directories(${CMAKE_SOURCE_DIR}/giflib)

# so
add_library(
        native-lib#so的名称
        SHARED#静态库.a  动态库 .so
        ${libgif_src}
        JavaInputStream.cpp
        GifFrame.cpp
        native-lib.cpp)

find_library(
        log-lib
        log)
# NDK
find_library(
        android-lib
        android
)#bitmap
find_library(
        jnigraphics-lib
        jnigraphics
)



target_link_libraries(
        native-lib
        ${android-lib}
        ${jnigraphics-lib}
        ${log-lib})