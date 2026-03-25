import java.net.URI

plugins {
	id("net.fabricmc.fabric-loom")
	`maven-publish`
}

version = providers.gradleProperty("mod_version").get()
group = providers.gradleProperty("maven_group").get()

base {
	archivesName = providers.gradleProperty("archives_base_name")
}

loom {
	accessWidenerPath = file("src/main/resources/kaleidoscope_cookery.accesswidener")
}

repositories {
	maven {
		name = "Greenhouse Maven"
		url = URI("https://repo.greenhouse.house/releases/")
	}
	maven {
		name = "Greenhouse Maven"
		url = URI("https://repo.greenhouse.house/snapshots/") // Porting Lib Hotfixes
	}
	maven { url = URI("https://mvn.devos.one/snapshots/") } // Porting Lib Betas
	maven {
		url = URI("https://jitpack.io/") // Fabric ASM
		content {
			excludeGroup ("io.github.fabricators_of_create")
		}
	}
	maven {
		url = URI("https://cursemaven.com")
	}
	maven {
		name = "cassian's maven"
		url = URI("https://maven.cassian.cc")
	}
	maven {
		name = "Fuzs Mod Resources"
		url = URI("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/")
	}
	maven { url = URI("https://api.modrinth.com/maven") }
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")
	implementation("vectorwing:FarmersDelight:${providers.gradleProperty("fdrf_version").get()}") {
		exclude(group = "net.fabricmc")
	}
	implementation ("curse.maven:create-fly-1346281:7752013")
	implementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")
	implementation("maven.modrinth:jade:${providers.gradleProperty("jade_version").get()}")
	// Fabric API. This is technically optional, but you probably want it anyway.
	implementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}")
	implementation ("fuzs.forgeconfigapiport:forgeconfigapiport-fabric:26.1.0.1")
}

tasks.processResources {
	inputs.property("version", version)

	filesMatching("fabric.mod.json") {
		expand("version" to version)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release = 25
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_25
	targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
	inputs.property("archivesName", base.archivesName)

	from("LICENSE") {
		rename { "${it}_${base.archivesName.get()}" }
	}
}

// configure the maven publication
publishing {
	publications {
		register<MavenPublication>("mavenJava") {
			artifactId = base.archivesName.get()
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}
