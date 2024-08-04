buildscript {
    dependencies {
        classpath("io.spring.gradle:dependency-management-plugin:1.1.5")
        classpath("org.springframework.boot:spring-boot-gradle-plugin:3.2.5")
    }
}

plugins {
    id("java")
    id("java-library")
    id("org.springframework.boot") version "3.2.5"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "com.fastcampuspay"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    group = "com.fastcampuspay"
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-aop")
        implementation("org.springframework.kafka:spring-kafka")
//        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//        implementation("com.h2database:h2:2.2.220")

        testImplementation("org.springframework.boot:spring-boot-starter-test")

        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")
    }

    tasks.test {
        useJUnitPlatform()
    }
}
