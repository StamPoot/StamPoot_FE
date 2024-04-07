pluginManagement {
    repositories {
        google()
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
        mavenCentral()
    }
}

rootProject.name = "FootStamp"
include(":app")
