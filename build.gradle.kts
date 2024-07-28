import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.util.Calendar

plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.cadixdev.licenser") version "0.6.1"
}

group = "com.github.alcoloid0"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
    implementation("com.github.Revxrsal.Lamp:common:3.2.1")
    implementation("com.github.Revxrsal.Lamp:bukkit:3.2.1")
}

license {
    header { file("HEADER.txt") }
    properties {
        set("author", "alcoloid (alcoloid0)")
        set("year", Calendar.getInstance().get(Calendar.YEAR))
    }
    exclude("**/*.yml")
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<JavaCompile> { options.compilerArgs.add("-parameters") }

tasks.build { dependsOn(tasks.shadowJar) }

tasks.shadowJar { archiveClassifier.set("") }

tasks.processResources {
    filesMatching("**/plugin.yml") { expand(project.properties) }
}

tasks.withType<KotlinJvmCompile> {
    compilerOptions { javaParameters = true }
}