plugins {
    java
    kotlin("jvm") version "1.3.61"
}

group = "org.joshua.ransom"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(fileTree(mapOf("dir" to "lib", "include" to listOf("*.jar"))))
    implementation(kotlin("stdlib-jdk8"))
    compile("org.jetbrains.kotlin", "kotlin-reflect", "1.3.61")
    compile("no.tornado", "tornadofx", "1.7.19")
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}