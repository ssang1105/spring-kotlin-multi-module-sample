import constant.LIBS
import constant.PLUGINS
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED

println("Applying KOTLIN-TEST-CONVENTIONS in project ${project.name}...")

plugins {
    id("kotlin-conventions")
    id("jacoco")
}

dependencies {
    testImplementation("org.jeasy:easy-random-core:${LIBS.JEASY_EASY_RANDOM_CORE_VERSION}")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events = setOf(FAILED, PASSED)
        exceptionFormat = FULL
    }
    maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
}

jacoco {
    toolVersion = PLUGINS.JACOCO_VERSION
}
