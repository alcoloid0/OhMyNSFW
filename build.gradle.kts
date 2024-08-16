import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.util.Calendar

plugins {
    kotlin("jvm") version "2.0.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("org.cadixdev.licenser") version "0.6.1"
}

group = "com.github.alcoloid0"
version = "1.3"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0-RC")
    implementation("com.github.Revxrsal.Lamp:common:3.2.1")
    implementation("com.github.Revxrsal.Lamp:bukkit:3.2.1")
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
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
    jvmToolchain(8)
}

tasks.withType<JavaCompile> { options.compilerArgs.add("-parameters") }

tasks.build { dependsOn(tasks.shadowJar) }

tasks.shadowJar { archiveClassifier.set("") }

tasks.processResources {
    filesMatching("**/plugin.yml") { expand(project.properties) }
}

tasks.shadowJar {
    relocate("net.kyori.adventure", "com.github.alcoloid0.nsfwplugin.shaded.adventure")
    relocate("net.kyori.option","com.github.alcoloid0.nsfwplugin.shaded.option")
}

tasks.withType<KotlinJvmCompile> {
    compilerOptions { javaParameters = true }
}