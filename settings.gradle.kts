pluginManagement {
    repositories {
        google {
            content {
                // Only include relevant Android/Google/AndroidX plugins
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
    // Disallow repositories in individual build.gradle files
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        // Main repositories for project dependencies
        google()
        mavenCentral()
    }
}

// Root project configuration
rootProject.name = "LankaMart"

// Include modules
include(":app")
