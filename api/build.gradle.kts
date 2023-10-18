plugins {
    id("sqler.common-conventions")
}

dependencies {
    api("com.zaxxer:HikariCP:5.0.1")
    api("org.jdbi:jdbi3-core:3.41.3")
    api("com.google.guava:guava:32.1.2-jre")
    compileOnlyApi("org.jetbrains:annotations:23.1.0")
}