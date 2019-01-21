cmake_minimum_required(VERSION 3.0.0)
project(ezloml VERSION 0.0.0)
set(CMAKE_CXX_STANDARD 14)

set (CMAKE_REQUIRED_INCLUDES "/usr/local/include" "/usr/include" "/usr/lib/x86_64-linux-gnu/" )

#run cmake like this to turn on debug option:
#sudo cmake -DDEBUG_OPTION=ON ..

option(DEBUG_OPTION "DEBUG_OPTION" ON)

if(DEBUG_OPTION)
    set(BUILD_TYPE "Debug")
else()
    set(BUILD_TYPE "Release")
endif()

add_definitions(-DWEBRTC_POSIX=1)
add_definitions(-DWEBRTC_LINUX=1)
add_definitions(-DWITHGPERFTOOLS=1)


set( SYSTEM_ROOT "$ENV{PKG_CONFIG_SYSROOT_DIR}")
#rpclib
include_directories( ${SYSTEM_ROOT}/usr/include/rpc/)

#websocketapp
include_directories( ${SYSTEM_ROOT}/usr/include/websocketpp/)

#opencv
include_directories( ${SYSTEM_ROOT}/usr/include/opencv)
include_directories( ${SYSTEM_ROOT}/usr/include/opencv2/imgproc)

#include_directories( ${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/opencv/modules/imgproc/include/opencv2/imgproc)
#include_directories("/usr/local/include/opencv4")

#cppcodec
include_directories( ${SYSTEM_ROOT}/usr/include/cppcodec)
#include_directories( ${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/cppcodec)

#pushercpp
include_directories( ${SYSTEM_ROOT}/usr/include/pusher++)
include_directories( ${SYSTEM_ROOT}/usr/include/rapidjson)

#include_directories(${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/pusher-cpp/pusher++/include)
#include_directories(${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/pusher-cpp/rapidjson/rapidjson/include)

#spdlog
include_directories( ${SYSTEM_ROOT}/usr/include/)
#include_directories(${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/spdlog/include)

##below webrtc related
include_directories( /home/cube/dev/webrtc/webrtc-checkout/src)
include_directories( /home/cube/dev/webrtc-checkout/src/third_party/opus/src/include)
#include_directories(${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/webrtc-checkout/src)
#include_directories(${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/webrtc-checkout/src/third_party/opus/src/include)

#curl
include_directories(${SYSTEM_ROOT}/usr/include/curl)
#include_directories(${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/curl3_build/include)

#boringssl
include_directories(${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/boringssl/include)

include(CTest)

enable_testing()

set( boost_INCLUDE_DIR ${SYSTEM_ROOT}/usr/include)
set( boost_LIB_DIR ${SYSTEM_ROOT}/usr/lib/)

set( Boost_LIBRARIES
        ${boost_LIB_DIR}/libboost_filesystem.a
        ${boost_LIB_DIR}/libboost_system.a
        ${boost_LIB_DIR}/libboost_regex.a
        ${boost_LIB_DIR}/libboost_program_options.a
)

message( STATUS "Boost static libs: " ${Boost_LIBRARIES} )

SET(GCC_COVERAGE_COMPILE_FLAGS "-std=c++14  -O0 -g3 -pthread -fpermissive -fms-extensions  -Werror=return-type")

SET( CMAKE_CXX_FLAGS  "${CMAKE_CXX_FLAGS} ${GCC_COVERAGE_COMPILE_FLAGS}" )
SET( CMAKE_EXE_LINKER_FLAGS  "${CMAKE_EXE_LINKER_FLAGS} ${GCC_COVERAGE_LINK_FLAGS}" )

#set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14 -stdlib=libc++")
#set(CMAKE_EXE_LINKER_FLAGS "${CMAKE_EXE_LINKER_FLAGS} -stdlib=libc++ -lc++abi")
#set(CMAKE_CXX_COMPILER "clang++")
#/usr/bin/

FILE(GLOB SRCFILES *.c *.cpp *.h *.hpp webrtcstreamer/*.cpp webrtcstreamer/*.h pp/*.cpp pp/*.hpp pp/*.h pp/include/*.cpp pp/include/*.hpp pp/include/*.h)

list(REMOVE_ITEM SRCFILES ${PROJECT_SOURCE_DIR}/ezloml.cpp)
add_library (ezloml_lib OBJECT ${SRCFILES})
ADD_EXECUTABLE(${PROJECT_NAME} $<TARGET_OBJECTS:ezloml_lib> ezloml.cpp)

LINK_DIRECTORIES(/usr/local/lib)
LINK_DIRECTORIES(/usr/lib)

set(LINKLIBS
    #c++abi
    lzma
    #${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/webrtc-checkout/src/out/${BUILD_TYPE}/obj/api/audio_codecs/libbuiltin_audio_decoder_factory.a
    #${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/webrtc-checkout/src/out/${BUILD_TYPE}/obj/api/audio_codecs/g711/libaudio_decoder_g711.a
    #${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/webrtc-checkout/src/out/${BUILD_TYPE}/obj/api/audio_codecs/g722/libaudio_decoder_g722.a
    #${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/webrtc-checkout/src/out/${BUILD_TYPE}/obj/api/audio_codecs/ilbc/libaudio_decoder_ilbc.a
    #${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/webrtc-checkout/src/out/${BUILD_TYPE}/obj/api/audio_codecs/isac/libaudio_decoder_isac_float.a
    #${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/webrtc-checkout/src/out/${BUILD_TYPE}/obj/api/audio_codecs/L16/libaudio_decoder_L16.a
    #${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/webrtc-checkout/src/out/${BUILD_TYPE}/obj/api/audio_codecs/opus/libaudio_decoder_opus.a

    ${SYSTEM_ROOT}/usr/lib/webrtc/libwebrtc.a
    #${${PROJECT_NAME}_SOURCE_DIR}/ezloml_3rdparty/webrtc-checkout/src/out/${BUILD_TYPE}/obj/libwebrtc_common.a

    ${${PROJECT_NAME}_SOURCE_DIR}/facepc/facemodel.obj
    ${${PROJECT_NAME}_SOURCE_DIR}/objoldpc/objold.obj
    ${${PROJECT_NAME}_SOURCE_DIR}/soundpc/soundmodel.obj

    rpc
    tcmalloc
    profiler
    curl
    crypto
    ssl

    wiringPi
    viplnet
    seeta_fa_lib
    seeta_facedet_lib
    lapack blas
    ${Boost_LIBRARIES}
    opencv_core opencv_imgproc opencv_highgui opencv_imgcodecs
    opencv_face opencv_bgsegm
    swresample avformat avcodec avutil swscale EGL GLESv2 X11
    pthread
    z
    m dl
)

target_link_libraries(${PROJECT_NAME} ${LINKLIBS})

set(CPACK_PROJECT_NAME ${PROJECT_NAME})
set(CPACK_PROJECT_VERSION ${PROJECT_VERSION})

set(TEST_DIR ${PROJECT_SOURCE_DIR}/tests)
add_subdirectory(${TEST_DIR})
add_test(NAME ezloml_test COMMAND ezloml_test)
include(CPack)
