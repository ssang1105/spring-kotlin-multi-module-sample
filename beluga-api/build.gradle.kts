import constant.LIBS.MOCKITO_KOTLIN_VERSION

plugins {
    id("kotlin-conventions")
    id("kotlin-spring-conventions")
    id("kotlin-testing-conventions")
    kotlin("plugin.jpa").version(constant.JVM.KOTLIN_VERSION)
}

dependencies {
    api(project(":beluga-common"))
    api(project(":beluga-api-client"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:${MOCKITO_KOTLIN_VERSION}")
}

tasks.jacocoTestReport {
    reports {
        html.required
    }
}

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            element = "CLASS"

            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = "0.80".toBigDecimal()
            }

            excludes = listOf(
                "*BaseEntity*"
            )
        }
    }
}