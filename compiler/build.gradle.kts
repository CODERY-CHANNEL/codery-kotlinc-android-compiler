import org.gradle.api.tasks.compile.JavaCompile

plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "com.github.CODERY-CHANNEL"
version = "1.0.7"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:2.1.21")
    implementation("org.jetbrains.kotlin:kotlin-daemon-embeddable:2.1.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:2.1.21")
    implementation("org.jetbrains.kotlin:kotlin-reflect:2.1.21")
    implementation("org.jetbrains.kotlin:kotlin-script-runtime:2.1.21")
    implementation("org.jetbrains.kotlin:kotlin-scripting-common:2.1.21")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm:2.1.21")
    implementation("org.jetbrains.kotlin:kotlin-scripting-compiler-impl-embeddable:2.1.21")
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
