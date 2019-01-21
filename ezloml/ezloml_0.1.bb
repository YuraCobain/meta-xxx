DESCRIPTION = "Ezlo ML"

LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = "file://readme.txt;md5=341d1b99129987168a9b63216fcbefd5"
# While this item does not require it, it depends on ffmpeg which does
LICENSE_FLAGS += "commercial"


EXTERNALSRC_pn-${PN} = "${HOME}/dev/ezloml"
EXTERNALSRC_BUILD_pn-${PN} = "${HOME}/dev/ezloml/build"
${S} = "${EXTERNALSRC}"
${B} = "${EXTERNALSRC_BUILD}"

inherit externalsrc cmake pkgconfig

DEPENDS += " \
    boost \
    boringssl \
    webrtc \
    spdlog \
    pusher-cpp \
    cppcodec \
    rpclib \
    ffmpeg \
    opencv \
    rapidjson \
    websocketpp \
    seeta-face-engine \
    cmake-native \
    ninja-native \
"

#do_configure() {
#    env
#    cd ${EXTERNALSRC}/
#    mkdir -p build && cd build && cmake -GNinja ..
#}
#
#do_compile() {
#    env
#    cd ${EXTERNALSRC}/build
#    ninja
#}

do_install() {
    install -d ${D}${includedir}
    cp -r  ${EXTERNALSRC}/pusher++/include/ ${D}${includedir}/
    cp -r  ${EXTERNALSRC}/rapidjson/rapidjson/include/ ${D}${includedir}/
}

