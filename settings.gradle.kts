pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

rootProject.name = "FitFlow"
include(":app")
include(":app-admin")
include(":core-designsystem")
include(":core-data")
include(":core-domain")
include(":core-network")
include(":core-database")
include(":core-common")
include(":feature-auth")
include(":feature-class")
include(":feature-booking")
include(":feature-payment")
include(":feature-checkin")
include(":feature-membership")
include(":feature-mypage")
include(":feature-admin-dashboard")
