load("@io_bazel_rules_kotlin//kotlin:kotlin.bzl", "kt_android_library")

kt_android_library(
    name = "lib3",
    srcs = glob(["**/*.kt", "**/*.java"]),
    custom_package = "com.tokopedia.utils",
    manifest = "AndroidManifest.xml",
    visibility = ["//visibility:public"],
    resource_files = glob(['res/**']),    
    deps = [
        "@maven//:androidx_appcompat_appcompat",
        ":androidx_room_room_compiler_library",
        "@maven//:com_tokopedia_unify_principles",
        "@maven//:com_tokopedia_unify_components",
        "@maven//:com_tokopedia_unify_compositions_dialog"
    ],
)

android_binary(
    name = "app",
    custom_package = "com.tokopedia.utils",
    manifest = "AndroidManifest.xml",
    visibility = ["//visibility:public"],
    resource_files = glob(['res/**']),    
    deps = [
        ":lib3",
        "@maven//:androidx_appcompat_appcompat",
        "@maven//:androidx_constraintlayout_constraintlayout",
        ":androidx_room_room_compiler_library",
    ],
)


java_plugin(
    name = "androidx_room_room_compiler_plugin",
    processor_class = "androidx.room.RoomProcessor",
    deps = [
        "@maven//:androidx_room_room_compiler",
    ],
)

java_library(
    name = "androidx_room_room_compiler_library",
    exported_plugins = [
        ":androidx_room_room_compiler_plugin",
    ],
    neverlink = True,
    exports = [
        "@maven//:androidx_room_room_common",
        "@maven//:androidx_room_room_compiler",
        "@maven//:androidx_room_room_runtime",
        "@maven//:androidx_room_room_rxjava2",
    ],
)

