import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    groovy
    java
    kotlin("jvm") version "1.3.40"
    maven
}

group = "com.tangnb.plugin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("url" to "https://jitpack.io")
}

dependencies {
    compile("org.codehaus.groovy:groovy-all:2.3.11")
    implementation(kotlin("stdlib-jdk8"))
    compile(gradleApi())
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}