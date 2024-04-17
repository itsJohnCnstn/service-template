plugins {
    java
    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.flywaydb.flyway") version "10.11.0"
}

apply(from = "gradle/lombok.gradle.kts")

group = "com.johncnstn"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation("org.postgresql:postgresql:42.7.3")
    implementation("org.flywaydb:flyway-core:10.11.0")
	implementation("org.zalando:problem-spring-web:0.29.1")
    implementation("org.apache.commons:commons-lang3")
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("com.google.guava:guava:33.1.0-jre")

    runtimeOnly("org.flywaydb:flyway-database-postgresql:10.11.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:testcontainers:1.19.7")
}

tasks.bootRun {
//    args = listOf("--spring.profiles.active=local")
    jvmArgs = listOf("-server", "-Xms256m", "-Xmx512m", "-Duser.country=US", "-Duser.language=en", "-Duser.timezone=UTC")
}

tasks.test {
    useJUnitPlatform()
//    with(testLogging) {
//        events = setOf(PASSED, SKIPPED, FAILED)
//    }
}


//tasks.withType<Test> {
//    useJUnitPlatform()
//}
