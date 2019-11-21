cmake_minimum_required(VERSION 3.4.1)

file(GLOB libgif_source src/main/cpp/giflib/*.c)

include_directories(src/main/cpp/giflib)

add_library(
             native-lib
             SHARED
             ${libgif_source}
             src/main/cpp/native-lib.cpp
             src/main/cpp/JavaInputStream.cpp
             src/main/cpp/GifFrame.cpp)

target_link_libraries(
                      native-lib
                      jnigraphics
                      log)