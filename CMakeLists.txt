cmake_minimum_required(VERSION 3.6)

set( CMAKE_CXX_STANDARD 17 )
set( CMAKE_POSITION_INDEPENDENT_CODE ON )

set(BDN_BUILD_TESTS OFF CACHE BOOL "Don't include boden tests")
set(BDN_BUILD_EXAMPLES OFF CACHE BOOL "Don't include boden examples")
set(BDN_WARNINGS_AS_ERRORS OFF CACHE BOOL "Don't treat warnings as errors")

project(MarkProf)

add_subdirectory(.. boden)

file(GLOB_RECURSE _sources source/*.cpp source/*.h)

ios_configure_asset(_assets ./ios-assets/Base.lproj/LaunchScreen.storyboard)
ios_configure_asset(_assets DemoIcon/black.png)

roger_add(
    MarkProf
    _assets
    ${CMAKE_CURRENT_LIST_DIR}/DemoIcon/demo_icon.json)

set(ANDROID_ICON "android:icon=\"@mipmap/ic_launcher_png\"")

add_universal_executable(MarkProf SOURCES ${_sources} ${_assets})

ios_configure_app_info(TARGET MarkProf
    LAUNCHSCREEN "LaunchScreen"
    TARGETED_DEVICES IPHONE IPAD
    IPHONE_ORIENTATIONS ALL
    IPAD_ORIENTATIONS ALL)

use_boden_template_info_plist(
    TARGET MarkProf
    BUNDLE_NAME "MarkProf"
    BUNDLE_ID "io.mycompany.MarkProf"
    )
set(ANDROID_APP_ID "io.mycompany.MarkProf")

set_xcode_appicon_asset(MarkProf "ApplicationIcon")
use_boden_template_info_plist(TARGET MarkProf
                              BUNDLE_NAME "MarkProf"
                              BUNDLE_ID "com.mycompany.MarkProf")

set_target_version(TARGET MarkProf VERSION "1.0.0" SHORTVERSION "1.0" LONGVERSION "1.0.0.0" ANDROID_VERSION_ID 1)

target_link_libraries(MarkProf Boden::All)
