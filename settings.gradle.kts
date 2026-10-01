pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven { url = uri(rootDir.resolve("repo")) }
        google()
        mavenCentral()
    }
}

rootProject.name = "provision_jatt_frp_android"
include(":provisioner-jatt-frp")
include(":dummyfrp")
