
println("Applying KOTLIN-SPRING-CONVENTIONS in project ${project.name}...")

plugins {
    id("kotlin-conventions")
    id("org.springframework.boot") // https://docs.spring.io/spring-boot/docs/current/gradle-plugin/reference/html/
    kotlin("plugin.spring") // https://kotlinlang.org/docs/reference/compiler-plugins.html#spring-support
}

apply(plugin = "org.jetbrains.kotlin.plugin.spring")
apply(plugin = "org.springframework.boot")
apply(plugin = "io.spring.dependency-management")

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

springBoot {
    buildInfo() // Creates META-INF/build-info.properties for Spring Boot Actuator
}