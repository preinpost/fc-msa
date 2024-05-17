plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

group = "com.fastcampuspay"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // 필요시 추가 의존성 정의
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
