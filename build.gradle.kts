plugins {
    kotlin("jvm") version "1.9.23"
    id("maven-publish")
    id("signing")
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

publishing {
    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
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