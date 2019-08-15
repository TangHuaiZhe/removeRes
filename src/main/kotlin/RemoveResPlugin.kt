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
import java.io.File

/**
 * author: tang
 * created on: 2019-08-07 14:05
 * description:
 */
open class RemoveResPlugin : Plugin<Project> {
  override fun apply(project: Project) {

    project.extensions.create(RemoveResExt.name, RemoveResExt::class.java)

    val moduleSrcDirs = getModuleSrcDirs(project)
    ColoredLogger.logYellow("全部模块: $moduleSrcDirs")

    val scanTargetFileTexts = createScanTargetFileTexts(moduleSrcDirs)
    ColoredLogger.logBlue("createScanTargetFileTexts ok")

    project.task("RemoveRes").doLast {

      ColoredLogger.logBlue("this is RemoveResPlugin ${project.version},dealing with ${project.name}")

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
        ).parallelStream().forEach {
          ColoredLogger.logYellow("1,[${it.fileType}] ======== Start ${it.fileType} checking in ${project.name}========")
          it.remove(moduleSrcDirs, scanTargetFileTexts, extension)
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
        ).parallelStream().forEach {
          it.remove(moduleSrcDirs, scanTargetFileTexts, extension)
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

  private fun getModuleSrcDirs(project: Project): ArrayList<String> {
    return project.rootProject.allprojects.filter {
      it.name != project.rootProject.name
    }.map {
      it.projectDir.path
    } as ArrayList
  }

  /**
   * 拼接需要查找的文件中的所有text内容
   */
  private fun createScanTargetFileTexts(moduleSrcDirs: List<String>): String {
    val stringBuilder = StringBuilder()

    moduleSrcDirs.parallelStream().map { File(it) }.forEach { srcDirFile ->
      srcDirFile.walk().forEach {
        //                    println("deal with file ${it.name} , prepare to filter")
        if (it.name.matches(
            Regex(
              """(.*\.xml)|(.*\.kt)|(.*\.java)|(.*\.gradle)"""
            )
          )
        ) {
          stringBuilder.append(it.readText())
        }
      }
    }
//            ColoredLogger.logGreen("createScanTargetFileTexts: $stringBuilder")
    return stringBuilder.toString()
  }
}