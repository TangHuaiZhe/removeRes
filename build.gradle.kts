import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    groovy
    java
    kotlin("jvm") version "1.3.40"
    maven
    id("com.gradle.plugin-publish") version "0.10.1"
    `java-gradle-plugin`
}

group = "com.tangnb.plugin"
version = "1.0.1-SNAPSHOT"

repositories {
    jcenter()
}

dependencies {
    compile("org.codehaus.groovy:groovy-all:2.4.15")
    implementation(kotlin("stdlib-jdk8"))
    compile(gradleApi())
    testCompile("junit", "junit", "4.12")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

// Use java-gradle-plugin to generate plugin descriptors and specify plugin ids
gradlePlugin {
    plugins {
        create("RemoveResPlugin") {
            id = "com.tangnb.plugin"
            implementationClass = "RemoveResPlugin"
        }
    }
}

pluginBundle {
    // These settings are set for the whole plugin bundle
    website = "https://github.com/TangHuaiZhe"
    vcsUrl = "https://github.com/TangHuaiZhe/removeRes"

    // tags and description can be set for the whole bundle here, but can also
    // be set / overridden in the config for specific plugins
    description = "Greetings from tangniubi!"

    // The plugins block can contain multiple plugin entries.
    //
    // The name for each plugin block below (greetingsPlugin, goodbyePlugin)
    // does not affect the plugin configuration, but they need to be unique
    // for each plugin.

    // Plugin config blocks can set the id, displayName, version, description
    // and tags for each plugin.

    // id and displayName are mandatory.
    // If no version is set, the project version will be used.
    // If no tags or description are set, the tags or description from the
    // pluginBundle block will be used, but they must be set in one of the
    // two places.

    (plugins) {

        "RemoveResPlugin" {
            // id is captured from java-gradle-plugin configuration
            displayName = "Gradle Greeting plugin tang"
            tags = listOf("individual", "tags", "per", "plugin")
            version = "1.0"
        }
    }
}

//tasks.named<Upload>("uploadArchives") {
//    repositories.withGroovyBuilder {
//        "mavenDeployer" {
//            "repository"("url" to "file://localhost/Users/tang/.m2/repository")
//            "pom" {
//                setProperty("version", version)
//                setProperty("artifactId", "removeRes")
//                setProperty("groupId", group)
//            }
//        }
//    }
//}

tasks {

    "uploadArchives"(Upload::class) {

        repositories {

            withConvention(MavenRepositoryHandlerConvention::class) {

                mavenDeployer {

                    withGroovyBuilder {
                        "repository"("url" to uri("/Users/tang/.m2/repository/"))
//                        "snapshotRepository"("url" to uri("file://localhost/Users/tang/.m2/repository/snapshots"))
                    }

                    pom.project {
                        withGroovyBuilder {
                            "parent" {
                                "groupId"(group)
                                "artifactId"("removeRes")
                                "version"(version)
                            }
                            "licenses" {
                                "license" {
                                    "name"("The Apache Software License, Version 2.0")
                                    "url"("http://www.apache.org/licenses/LICENSE-2.0.txt")
                                    "distribution"("repo")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
