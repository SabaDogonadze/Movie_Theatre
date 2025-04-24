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
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MovieTheatre"
include(":app")
include(":core")
include(":core:data")
include(":core:domain")
include(":core:presentation")
include(":resource")
include(":feature")
include(":feature:login")
include(":feature:login:data")
include(":feature:login:domain")
include(":feature:login:presentation")
include(":feature:register")
include(":feature:register:data")
include(":feature:register:domain")
include(":feature:register:presentation")
include(":feature:home")
include(":feature:home:data")
include(":feature:home:domain")
include(":feature:home:presentation")
include(":feature:movie_detail")
include(":feature:payment")
include(":feature:seat")
include(":feature:movie_detail:data")
include(":feature:movie_detail:domain")
include(":feature:movie_detail:presentation")
include(":feature:payment:data")
include(":feature:payment:domain")
include(":feature:payment:presentation")
include(":feature:seat:data")
include(":feature:seat:domain")
include(":feature:seat:presentation")
include(":feature:profile")
include(":feature:profile:data")
include(":feature:profile:domain")
include(":feature:profile:presentation")
include(":feature:shop")
include(":feature:splash")
include(":feature:shop:data")
include(":feature:shop:domain")
include(":feature:shop:presentation")
include(":feature:splash:data")
include(":feature:splash:domain")
include(":feature:splash:presentation")
include(":feature:movie_quiz")
include(":feature:movie_quiz:data")
include(":feature:movie_quiz:domain")
include(":feature:movie_quiz:presentation")
