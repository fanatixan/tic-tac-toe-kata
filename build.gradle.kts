plugins {
    java
    id("com.adarshr.test-logger") version "2.1.1"
}

group = "com.ibm.xpfarm"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.18.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.wrapper {
    gradleVersion = "6.7"
}
