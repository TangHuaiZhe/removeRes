import internal.filetype.AnimFileRemover
import internal.filetype.AnimatorFileRemover
import internal.filetype.ColorFileRemover
import internal.filetype.DrawableFileRemover
import internal.filetype.LayoutFileRemover
import internal.filetype.MenuFileRemover
import internal.filetype.MipmapFileRemover
import internal.valuetype.AttrXmlValueRemover
import internal.valuetype.BoolXmlValueRemover
import internal.valuetype.ColorXmlValueRemover
import internal.valuetype.DimenXmlValueRemover
import internal.valuetype.IdXmlValueRemover
import internal.valuetype.IntegerXmlValueRemover
import internal.valuetype.StringXmlValueRemover
import internal.valuetype.StyleXmlValueRemover
import internal.valuetype.ThemeXmlValueRemover
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

    ColoredLogger.logGreen("this is RemoveResPlugin,dealing with ${project.name}")

    project.extensions.create(RemoveResExt.name, RemoveResExt::class.java)

    project.task("RemoveRes").doLast {

      val extension: RemoveResExt = project.extensions.findByName(RemoveResExt.name) as RemoveResExt

      logExtensionInfo(extension)

      // Remove unused files
      if (extension.openRemoveFile) {
        ColoredLogger.logGreen("doing FileRemover")
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

      //Remove unused xml values
      if (extension.openRemoveXmlValues) {
        ColoredLogger.logGreen("doing XmlRemover")
        ArrayList(
            listOf(
                AttrXmlValueRemover(), BoolXmlValueRemover(),
                ColorXmlValueRemover(),
                DimenXmlValueRemover(), IdXmlValueRemover(),
                IntegerXmlValueRemover(),
                StringXmlValueRemover(), StyleXmlValueRemover(), ThemeXmlValueRemover()
            )
        ).forEach {
          it.remove(project, extension)
        }
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