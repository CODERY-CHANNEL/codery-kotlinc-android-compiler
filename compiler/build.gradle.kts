import org.gradle.api.tasks.compile.JavaCompile

plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "com.github.CODERY-CHANNEL"
version = "1.0.10"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation("com.github.waelchateur:kotlinc-for-sketchware:2.1.21_rc3")
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(17)
}

publishing {
    publications {
        create<MavenPublication>("release") {
            from(components["java"])
        }
    }
}
