buildscript {
    dependencies {
        classpath("io.spring.gradle:dependency-management-plugin:1.1.5")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:3.2.5")
    }
}

apply(plugin = "org.springframework.boot")

plugins {
    id("java")
    id("java-library")
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.5"
    id("com.palantir.docker") version "0.36.0"
}

group = "com.fastcampuspay.loggingconsumer"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation(project(path = ":common"))
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

}

tasks.test {
    useJUnitPlatform()
}

docker {
    println(tasks.bootJar.get().outputs.files)
    setName(rootProject.name + '-' + project.name + ':' + version)
    setDockerfile(file("../Dockerfile"))
    files(tasks.bootJar.get().outputs.files)
    buildArgs(mapOf("JAR_FILE" to tasks.bootJar.get().outputs.files.singleFile.name))
}