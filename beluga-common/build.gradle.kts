plugins {
    id("kotlin-conventions")
    id("kotlin-spring-conventions")
    id("kotlin-testing-conventions")
}

dependencies {
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}