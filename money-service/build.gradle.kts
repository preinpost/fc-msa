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

group = "com.fastcampuspay.money"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.projectlombok:lombok")
    implementation(project(":banking-service"))
    annotationProcessor("org.projectlombok:lombok")

    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")


    implementation("com.h2database:h2:2.2.220")
    runtimeOnly("mysql:mysql-connector-java:8.0.33")

    implementation(project(path = ":common"))

//    testImplementation(platform("org.junit:junit-bom:5.10.0"))
//    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

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