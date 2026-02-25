pluginManagement {
    repositories {
        maven { setUrl("https://maven.google.com") }
        maven { setUrl("https://jitpack.io") }

        mavenCentral()
        gradlePluginPortal()
//        maven { url = uri("https://maven.aliyun.com/repository/public/") }
//        maven { url = uri("https://jitpack.io") }
//        maven { url = uri("https://repo1.maven.org/maven2/") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { setUrl("https://maven.google.com") }
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        google()
        //        maven { url = uri("https://maven.aliyun.com/repository/public/") }
//        maven { url = uri("https://repo1.maven.org/maven2/") }
    }
}

rootProject.name = "MyComposeTest"
include(":app")
include(":base")
