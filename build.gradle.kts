plugins {
    java
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version("7.1.2")
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    compileOnly("org.jetbrains:annotations:23.0.0")
    compileOnly("com.google.code.gson:gson:2.9.0")
    api("com.zaxxer:HikariCP:5.0.1")
    api("org.jdbi:jdbi3-core:3.37.1")
}


java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(16))
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}