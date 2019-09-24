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
import util.LogUtil
import util.ThreadPoolManager
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
//    LogUtil.yellow("全部模块: $moduleSrcDirs")

    val scanTargetFileTexts = createScanTargetFileTexts(moduleSrcDirs)
//    LogUtil.blue("createScanTargetFileTexts ok")

    project.task("RemoveRes").doLast {

      LogUtil.blue("this is RemoveResPlugin,dealing with ${project.name}")

      val extension: RemoveResExt = project.extensions.findByName(RemoveResExt.name) as RemoveResExt

      logOpen = extension.logOpen

      logExtensionInfo(extension)

      val fileRemoverList = ArrayList(
        listOf(
          LayoutFileRemover(), MenuFileRemover(),
          MipmapFileRemover(),
          DrawableFileRemover(), AnimatorFileRemover(),
          AnimFileRemover(),
          ColorFileRemover()
        )
      )

      val valueRemoverList = ArrayList(
        listOf(
          AttrXmlValueRemover(), BoolXmlValueRemover(),
          ColorXmlValueRemover(),
          DimenXmlValueRemover(), IdXmlValueRemover(),
          IntegerXmlValueRemover(),
          StringXmlValueRemover(), StyleXmlValueRemover(), ThemeXmlValueRemover()
        )
      )

//      val threadList: ArrayList<Thread> = ArrayList()

      // Remove unused files
      if (extension.openRemoveFile) {
        LogUtil.green("doing FileRemover ${project.name}")
        fileRemoverList.forEach {
          val fileRemoverThread = Runnable {
            LogUtil.green("this is thread ${Thread.currentThread()}")
            LogUtil.yellow("1,[${it.fileType}] " +
                "======== Start ${it.fileType} checking in ${project.name}========")
            it.remove(moduleSrcDirs, scanTargetFileTexts, extension)
          }
          ThreadPoolManager.instance.execute(fileRemoverThread)
//          threadList.add(fileRemoverThread)
        }
      }

      //Remove unused xml values
      if (extension.openRemoveXmlValues) {
        LogUtil.green("doing XmlRemover ${project.name}")
        valueRemoverList.forEach {
          val valueRemoverThread = Runnable {
            LogUtil.green("this is thread ${Thread.currentThread()}")
            it.remove(moduleSrcDirs, scanTargetFileTexts, extension)
          }
          ThreadPoolManager.instance.execute(valueRemoverThread)
//          threadList.add(valueRemoverThread)
        }
      }

      while (!ThreadPoolManager.instance.isOver) {
        LogUtil.blue("${ThreadPoolManager.instance}")
        Thread.sleep(3000)
      }
//      threadList.forEach {
//        it.join()
//      }
    }
  }

  companion object {

    var logOpen = false

    const val string = ""
    val moduleSrcDir = ArrayList<String>()

    fun logExtensionInfo(extension: RemoveResExt) {
      if (extension.extraRemovers.isNotEmpty()) {
        LogUtil.info("extraRemovers:")
        extension.extraRemovers.forEach {
          LogUtil.info("  $it")
        }
      }

      if (extension.excludeNames.isNotEmpty()) {
        LogUtil.info("excludeNames:")
        extension.excludeNames.forEach {
          LogUtil.info("  $it")
        }
      }
      LogUtil.info("dryRun: " + extension.dryRun.toString())
    }
  }

  private fun getModuleSrcDirs(project: Project): ArrayList<String> {
    return if (moduleSrcDir.isEmpty()) {
      project.rootProject.allprojects.filter {
        it.name != project.rootProject.name
      }.map {
        it.projectDir.path
      } as ArrayList
    }else{
      moduleSrcDir
    }
  }

  var string = ""

  /**
   * 拼接需要查找的文件中的所有text内容
   */
  private fun createScanTargetFileTexts(moduleSrcDirs: List<String>): String {

    if (string.isNotEmpty()) {
      return string
    }

    val stringBuilder = StringBuilder()

    moduleSrcDirs.map { File(it) }.forEach { srcDirFile ->
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
    string = stringBuilder.toString()
    return string
  }
}