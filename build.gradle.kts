plugins {
    kotlin("jvm") version "1.9.23"
    id("maven-publish")
    id("signing")
    id("io.github.gradle-nexus.publish-plugin") version "2.0.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}

java {
    withSourcesJar()
    withJavadocJar()
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            username.set(System.getenv("MAVEN_USERNAME"))
            password.set(System.getenv("MAVEN_PASSWORD"))
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/thiagoddsilva/memorycache")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name.set("MemoryCache")
                description.set("A simple in-memory cache implementation.")
                url.set("https://github.com/thiagoddsilva/memorycache")
                developers {
                    developer {
                        id.set("thiago")
                        name.set("Thiago Silva")
                        email.set("thiagodd.silva@gmail.com")
                    }
                }

                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://github.com/thiagoddsilva/memorycache?tab=MIT-1-ov-file")
                    }
                }

                scm {
                    url.set("https://github.com/thiagoddsilva/memorycache")
                    connection.set("scm:git://github.com/thiagoddsilva/memorycache.git")
                    developerConnection.set("scm:git://github.com/thiagoddsilva/memorycache.git")
                }
            }
        }
    }
}

signing {
    val signingKey = System.getenv("GPG_SIGNING_KEY")
    val signingPassword = System.getenv("GPG_SIGNING_PASSPHRASE")

    useInMemoryPgpKeys(signingKey, signingPassword)

    sign(publishing.publications["maven"])
}