cmake_minimum_required(VERSION 3.14)

set(EXECUTABLE_OUTPUT_PATH  ${PROJECT_BINARY_DIR})

aux_source_directory(src APP_SRC)

add_executable(day_1 ${APP_SRC})