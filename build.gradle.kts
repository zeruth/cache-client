/*
 * Copyright (c) 2019 Owain van Brakel <https://github.com/Owain94>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    java
    `maven-publish`
}

group = "com.openosrs"
version = "1.0-SNAPSHOT"
var rsversion = "187"

repositories {
    jcenter()
    mavenCentral()
    mavenLocal()
    maven(url = "https://raw.githubusercontent.com/open-osrs/hosting/master")
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.10")

    compileOnly("org.projectlombok:lombok:1.18.10")

    implementation("io.minio:minio:6.0.11")
    implementation("mysql:mysql-connector-java:8.0.18")
    implementation("org.springframework.boot:spring-boot-devtools:2.2.2.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter:2.2.2.RELEASE")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:2.2.2.RELEASE")
    implementation("org.sql2o:sql2o:1.6.0")
    implementation("com.openosrs:cache-client:1.0-SNAPSHOT")
    implementation("com.openosrs:protocol-api:1.0-SNAPSHOT")
    implementation("com.openosrs:cache:187.0-SNAPSHOT")
    implementation("com.openosrs:http-api:1.6.3-SNAPSHOT")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
    from(sourceSets.main.get().allSource)
}

publishing {
    repositories {
        maven {
            url = uri("$buildDir/repo")
        }
    }
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}
