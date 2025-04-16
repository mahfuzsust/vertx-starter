import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.google.protobuf.gradle.id
import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
	java
	application
	id("com.github.johnrengelman.shadow") version "7.1.2"
	id("com.google.protobuf") version "0.9.5"
}

group = "com.example"
version = "1.0.0-SNAPSHOT"

repositories {
	mavenCentral()
}

val vertxVersion = "4.5.14"
val junitJupiterVersion = "5.9.1"
val protobufVersion = "4.30.2"

val mainVerticleName = "com.example.vertx_proto.MainVerticle"
val launcherClassName = "io.vertx.core.Launcher"

val watchForChange = "src/**/*"
val doOnChange = "${projectDir}/gradlew classes"

application {
	mainClass.set(launcherClassName)
}

dependencies {
	implementation(platform("io.vertx:vertx-stack-depchain:$vertxVersion"))
	implementation("io.vertx:vertx-service-proxy")
	implementation("io.vertx:vertx-health-check")
	implementation("io.vertx:vertx-web")
	implementation("io.vertx:vertx-grpc")
	implementation("com.google.protobuf:protobuf-java:$protobufVersion")
	implementation("com.google.protobuf:protobuf-java-util:$protobufVersion")
	implementation("javax.annotation:javax.annotation-api:1.3.2")
	testImplementation("io.vertx:vertx-junit5")
	testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
	compileOnly( "io.grpc", "grpc-all", "1.71.0")

	runtimeOnly("io.netty:netty-all:4.2.0.Final")
}

java {
	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<ShadowJar> {
	archiveClassifier.set("fat")
	manifest {
		attributes(mapOf("Main-Verticle" to mainVerticleName))
	}
	mergeServiceFiles()
}

tasks.withType<Test> {
	useJUnitPlatform()
	testLogging {
		events = setOf(PASSED, SKIPPED, FAILED)
	}
}

tasks.withType<JavaExec> {
	args = listOf(
		"run",
		mainVerticleName,
		"--redeploy=$watchForChange",
		"--launcher-class=$launcherClassName",
		"--on-redeploy=$doOnChange"
	)
}

protobuf {
	protoc {
		artifact = "com.google.protobuf:protoc:$protobufVersion:osx-x86_64"
	}
	plugins {
		id("grpc") {
			artifact = "io.grpc:protoc-gen-grpc-java:1.71.0:osx-x86_64"
		}
	}
	generateProtoTasks {
		all().forEach {
			it.plugins {
				id("grpc")
			}
		}
	}
}


sourceSets{
	main{
		proto{
			srcDirs("src/main/protos")
		}
		java{
			srcDirs("build/generated/source/proto/main/grpc" ,"build/generated/source/proto/main/java")

		}
	}
}
