import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
}

idea {
    module.isDownloadJavadoc = true
    module.isDownloadSources = true
}