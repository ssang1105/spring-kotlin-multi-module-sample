@file:Suppress("UnstableApiUsage")

import constant.JVM.JDK_VERSION

println("Applying JAVA-CONVENTIONS in project ${project.name}...")

plugins {
    `java-library`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(JDK_VERSION))
    }
    withSourcesJar()
}

tasks.compileJava {
    @Suppress("SpellCheckingInspection")
    options.compilerArgs.addAll(
        listOf(
            "-Xlint:all", // Enables all recommended warnings.
            "-Werror" // Terminates compilation when warnings occur.
        )
    )
    options.encoding = "UTF-8"
}


repositories {
    mavenCentral()
}