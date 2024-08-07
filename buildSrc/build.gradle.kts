plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

kotlin.sourceSets["main"].kotlin.srcDirs("main")
sourceSets["main"].java.srcDirs("main")
sourceSets["main"].resources.srcDirs("resources")
