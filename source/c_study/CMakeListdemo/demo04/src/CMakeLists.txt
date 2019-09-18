cmake_minimum_required(VERSION 3.6.1)

include_directories(${PROJECT_SOURCE_DIR}/libhello)

set(APP_SRC main.c)
add_executable(hello ${APP_SRC})

target_link_libraries(hello libhello)
