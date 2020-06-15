import com.eaglesakura.armyknife.runtime.extensions.encodeMD5
import java.nio.charset.Charset

buildscript {
    repositories {
        maven(url = "https://dl.bintray.com/eaglesakura/maven/")
    }
    dependencies {
        classpath("com.eaglesakura.armyknife.armyknife-runtime:armyknife-runtime:1.3.7")
    }
}

rootProject.extra["artifact_name"] = project.name
rootProject.extra["artifact_group"] =
    "com.eaglesakura.armyknife.${rootProject.extra["artifact_name"]}"
rootProject.extra["bintray_user"] = "eaglesakura"
rootProject.extra["bintray_labels"] = arrayOf("android", "kotlin")
rootProject.extra["bintray_vcs_url"] = "https://github.com/eaglesakura/${project.name}"

val buildOnCi = System.getenv("CIRCLE_BUILD_NUM") != null
val buildTag = System.getenv("CIRCLE_TAG") ?: ""
val buildNumberFile = rootProject.file(".configs/secrets/build-number.env")
val buildNumber = when {
    hasProperty("install_snapshot") -> 99999
    buildNumberFile.isFile -> buildNumberFile.readText(Charset.forName("UTF-8")).trim().toInt()
    else -> System.getenv("CIRCLE_BUILD_NUM")?.toInt() ?: 1
}

/**
 * Auto configure.
 */
rootProject.extra["artifact_version"] = when {
    !buildOnCi -> "${rootProject.extra["base_version"] as String}.snapshot"
    buildTag.startsWith("v") -> buildTag.substring(1)
    else -> "${rootProject.extra["base_version"] as String}.build-${buildNumber}"
}.trim()

val gradleHashFileText = listOf(
    rootProject.projectDir
        .listFiles()!!
        .filter { it.isFile }
        .filter { it.name.contains(".gradle") },
    rootProject.projectDir
        .listFiles()!!
        .filter { it.isDirectory }
        .map { it.listFiles()!!.toList() }
        .flatten()
        .filter { it.name.contains(".gradle") }
).asSequence().flatten()
    .filter {
        it.isFile && it.name.contains(".gradle")
    }
    .toSet()
    .toList()
    .sortedWith { a, b -> a.absolutePath.compareTo(b.absolutePath) }.toList()
    .let { fileList ->
        val root = rootProject.rootDir
        buildString {
            fileList
                .map { it to it.readText() }
                .map { it.first to it.second.replace("\n", "").replace("\r", "") }
                .forEach { (file, text) ->
                    append(text.toByteArray().encodeMD5())
                    append(" : ")
                    append(
                        file.canonicalPath.substring(root.canonicalPath.length)
                            .replace("\\", "/")
                    )
                    append("\n")
                }
        }
    }
    .also { hashText ->
        val dst = rootProject.file(".circleci/hash-gradle")
        dst.delete()
        dst.writeText(hashText)
    }

println("====================================== Project Configuration ==============================================")
println("artifact         : ${rootProject.extra["artifact_version"]}")
println("===========================================================================================================")


