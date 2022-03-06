@file:Suppress("UnstableApiUsage")

import constant.JVM.JDK_VERSION
import constant.LIBS.MICRO_UTILS_KOTLIN_LOGGING_VERSION

println("Applying KOTLIN-CONVENTIONS in project ${project.name}...")

plugins {
    id("java-conventions")
    kotlin("jvm")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.github.microutils:kotlin-logging:${MICRO_UTILS_KOTLIN_LOGGING_VERSION}")
}

tasks.compileKotlin {
    println("Configuring KotlinCompile $name in project ${project.name}...")
    kotlinOptions {
        @Suppress("SpellCheckingInspection")
        freeCompilerArgs = listOf("-Xjsr305=strict")  // JSR-305 에 정의된 null 허용성 어노테이션 강제
        jvmTarget = JDK_VERSION
        languageVersion = "1.6"
        apiVersion = "1.6"
    }
}

tasks.compileTestKotlin {
    println("Configuring KotlinTestCompile  $name in project ${project.name}...")
    kotlinOptions {
        @Suppress("SpellCheckingInspection")
        freeCompilerArgs = listOf("-Xjsr305=strict") // JSR-305 에 정의된 null 허용성 어노테이션 강제
        jvmTarget = JDK_VERSION
        languageVersion = "1.6"
        apiVersion = "1.6"
    }
}
repositories {
    mavenCentral()
}

