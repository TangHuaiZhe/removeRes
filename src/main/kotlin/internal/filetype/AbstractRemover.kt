package internal.filetype

import RemoveResExt
import org.gradle.api.Project
import org.gradle.internal.impldep.com.google.common.annotations.VisibleForTesting
import util.ColoredLogger
import util.SearchPattern
import java.io.File
import java.util.ArrayList
import java.util.regex.Pattern

/**
 * author: tang
 * created on: 2019-08-07 16:48
 * description:
 */
abstract class AbstractRemover(
  /**
   * directory/file name to find files like drawable, dimen, string
   */
  open val fileType: String,
  /**
   * Resource name to check its existence like @`string`/app_name, $.`string`/app_name
   * 资源名，检查是否在代码和xml中有引用
   */
  open val resourceName: String,
  /**
   * Search pattern ex) theme should specified to Type.STYLE
   */
  open val type: SearchPattern.Type
) {
  var scanTargetFileTexts = ""
  var excludeNames: ArrayList<String> = ArrayList()
  var dryRun = false

  abstract fun removeEach(resDirFile: File)

  /**
   * @param target is file name or attribute name
   * @return pattern string to grep src
   */
  @VisibleForTesting
  fun createSearchPattern(target: String): String {
    val searchPattern = SearchPattern.create(resourceName, target, type)
    println("匹配正则: $searchPattern")
    return searchPattern
  }

  /**
   * 根据fileType 删除多余文件
   */
  fun remove(project: Project, extension: RemoveResExt) {
    this.dryRun = extension.dryRun
    this.excludeNames = extension.excludeNames

    val moduleSrcDirs: List<String> = project.rootProject.allprojects.filter {
      it.name != project.rootProject.name
    }.map {
      it.projectDir.path
    }
    scanTargetFileTexts =
        createScanTargetFileTexts(moduleSrcDirs)

    ColoredLogger.log("[$fileType] ======== Start $fileType checking ========")

    //todo 兼容多渠道
    moduleSrcDirs.forEach {
      ColoredLogger.log("Checking [$fileType] in $it")

      var resDirFile = File("$it/src/main/res")
      if (resDirFile.exists()) {
        removeEach(resDirFile)
      }

      resDirFile = File("$it/res")
      if (resDirFile.exists()) {
        removeEach(resDirFile)
      }
    }
  }

  fun checkTargetTextMatches(targetText: String): Boolean {
    ColoredLogger.logGreen("检查 $fileType 类型文件是否被使用，文件名: $targetText.$fileType")
    val pattern = createSearchPattern(targetText)
    return isPatternMatched(
        scanTargetFileTexts,
        pattern
    )
  }

  fun isMatchedExcludeNames(filePath: String): Boolean {
//        println("isMatchedExcludeNames filePath: $filePath")
//        println("isMatchedExcludeNames excludeNames: $excludeNames")
    return excludeNames.count {
      filePath.contains(it)
    } > 0
  }

  override fun toString(): String {
    return ("fileType: "
        + fileType
        + ", resourceName: "
        + resourceName
        + ", type: "
        + type.toString())
  }

  fun isDryRun(): Boolean {
    return dryRun
  }

  companion object {
    /**
     * 拼接需要查找的文件中的所有text内容
     */
    private fun createScanTargetFileTexts(moduleSrcDirs: List<String>): String {
      val stringBuilder = StringBuilder()

      moduleSrcDirs.map { File(it) }.filter { it.exists() }.forEach { srcDirFile ->
        srcDirFile.walk().forEach {
          //                    println("deal with file ${it.name} , prepare to filter")
          if (it.name.matches(
                  Regex(
                      FILE_TYPE_FILTER
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

    @VisibleForTesting
    fun isPatternMatched(fileText: String, pattern: String): Boolean {
      println("isPatternMatched pattern: $pattern")
      val matcher = Pattern.compile(pattern).matcher(fileText);
      val result = matcher.find()
      println("isPatternMatched result: $result")
      return result
    }

    private const val FILE_TYPE_FILTER = """(.*\.xml)|(.*\.kt)|(.*\.java)|(.*\.gradle)"""
  }
}
