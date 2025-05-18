plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "de.jardateien.smartchat"
version = providers.environmentVariable("VERSION").getOrElse("1.0.1")

labyMod {
    defaultPackageName = "de.jardateien.smartchat"

    addonInfo {
        namespace = "smartchat"
        displayName = "SmartChat"
        author = "JarDateien"
        description = "Lets you send information into chat using placeholders"
        minecraftVersion = "*"
        version = rootProject.version.toString()
    }

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    devLogin = true
                }
            }
        }
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    group = rootProject.group
    version = rootProject.version
}
