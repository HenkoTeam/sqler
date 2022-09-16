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
    api("com.zaxxer:HikariCP:4.0.3")
    api("org.jdbi:jdbi3-core:3.1.0")
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