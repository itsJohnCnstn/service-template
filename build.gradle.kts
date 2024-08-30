import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    kotlin("jvm") version Versions.kotlin
    id("org.jetbrains.kotlin.plugin.spring") version Versions.kotlin
    id("org.springframework.boot") version Versions.springBoot
    id("org.flywaydb.flyway") version Versions.flyway
    id("com.diffplug.spotless") version Versions.spotless
    checkstyle
    jacoco
    pmd
}

apply(plugin = "io.spring.dependency-management")

apply(from = "gradle/checkstyle.gradle")
apply(from = "gradle/jacoco.gradle")
apply(from = "gradle/lombok.gradle.kts")
apply(from = "gradle/mapstruct.gradle")
apply(from = "gradle/spotless.gradle")

group = "com.johncnstn"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    // Common dependencies
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    compileOnly("org.springframework.data:spring-data-commons:${Versions.springDataCommons}")

    implementation("com.fasterxml.jackson.module:jackson-module-afterburner")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.google.guava:guava:${Versions.guava}")
    implementation("io.hypersistence:hypersistence-utils-hibernate-63:${Versions.hibernateTypes}")
    implementation("io.swagger:swagger-annotations:${Versions.swaggerAnnotations}")

    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-starter-webmvc-ui
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    implementation("org.apache.commons:commons-lang3")
    implementation("org.postgresql:postgresql")
    implementation("org.flywaydb:flyway-core:${Versions.flyway}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.yaml:snakeyaml")
    implementation("org.zalando:problem-spring-web:${Versions.problemSpringWeb}")

    implementation("org.springframework.kafka:spring-kafka:${Versions.springKafka}")

    runtimeOnly("org.flywaydb:flyway-database-postgresql:${Versions.flyway}")
    runtimeOnly("javax.xml.bind:jaxb-api:2.3.1")

    // Test dependencies
    testImplementation("com.github.javafaker:javafaker:${Versions.javafaker}") {
        exclude("org.yaml")
    }
    testImplementation("org.awaitility:awaitility:${Versions.awaitility}")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:${Versions.junitJupiterEngine}")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude("org.junit.vintage:junit-vintage-engine")
    }
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:testcontainers:${Versions.testcontainers}")
    testImplementation("org.testcontainers:kafka")
}

configurations {
    compileOnly {
        extendsFrom(annotationProcessor.get())
    }
    implementation {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.bootRun {
    jvmArgs = listOf("-server", "-Xms256m", "-Xmx512m", "-Duser.country=US", "-Duser.language=en", "-Duser.timezone=UTC")
}

tasks.test {
    useJUnitPlatform()
    with(testLogging) {
        events = setOf(PASSED, SKIPPED, FAILED)
    }
}
