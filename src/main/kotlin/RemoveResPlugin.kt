import internal.filetype.AnimFileRemover
import internal.filetype.AnimatorFileRemover
import internal.filetype.ColorFileRemover
import internal.filetype.DrawableFileRemover
import internal.filetype.LayoutFileRemover
import internal.filetype.MenuFileRemover
import internal.filetype.MipmapFileRemover
import org.codehaus.groovy.runtime.DefaultGroovyMethods
import org.gradle.api.Plugin
import org.gradle.api.Project
import util.ColoredLogger

/**
 * author: tang
 * created on: 2019-08-07 14:05
 * description:
 */
open class RemoveResPlugin : Plugin<Project> {
  override fun apply(project: Project) {

    println("this is tangniubi RemoveResPlugin")

    project.extensions.create(RemoveResExt.name, RemoveResExt::class.java)

    project.task("RemoveRes").doLast {

      val extension: RemoveResExt = DefaultGroovyMethods.asType(
          project.extensions.findByName(RemoveResExt.name),
          RemoveResExt::class.java
      )
      logExtensionInfo(extension)

      // Remove unused files
      ArrayList(
          listOf(
              LayoutFileRemover(), MenuFileRemover(),
              MipmapFileRemover(),
              DrawableFileRemover(), AnimatorFileRemover(),
              AnimFileRemover(),
              ColorFileRemover()
          )
      ).forEach {
        it.remove(project, extension)
      }
    }
  }

  companion object {
    fun logExtensionInfo(extension: RemoveResExt) {
      if (extension.extraRemovers.isNotEmpty()) {
        ColoredLogger.log("extraRemovers:")
        extension.extraRemovers.forEach {
          ColoredLogger.log("  $it")
        }
      }

      if (extension.excludeNames.isNotEmpty()) {
        ColoredLogger.log("excludeNames:")
        extension.excludeNames.forEach {
          ColoredLogger.log("  $it")
        }
      }
      ColoredLogger.log("dryRun: " + extension.dryRun.toString())
    }
  }
}