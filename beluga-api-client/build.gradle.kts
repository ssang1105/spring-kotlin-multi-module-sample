import constant.LIBS.NETTY_RESOLVER_DNS_NATIVE_MACOS_VERSION
import constant.LIBS.OKHTTP3_MOCK_WEB_SERVER_VERSION

plugins {
    id("kotlin-conventions")
    id("kotlin-spring-conventions")
    id("kotlin-testing-conventions")
    kotlin("kapt") // https://kotlinlang.org/docs/kapt.html
}

dependencies {
    api(project(":beluga-common"))
    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    kapt("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.squareup.okhttp3:mockwebserver:${OKHTTP3_MOCK_WEB_SERVER_VERSION}") {
        exclude("com.squareup.okhttp3", "okhttp")
    }
    testImplementation("com.squareup.okhttp3:okhttp:${OKHTTP3_MOCK_WEB_SERVER_VERSION}")
    testImplementation("io.netty:netty-resolver-dns-native-macos:${NETTY_RESOLVER_DNS_NATIVE_MACOS_VERSION}") // m1 맥북사용시 MockWebServer 사용하려면 필요

}

tasks.jar {
    enabled = true
}

tasks.bootJar {
    enabled = false
}

tasks.bootRun {
    enabled = false
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
        }
    }
}


