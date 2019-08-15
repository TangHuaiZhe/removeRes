package internal

import RemoveResExt
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
  open val fileType: String,
  /**
   *  @`string`/app_name, $.`string`/app_name
   * 资源名，检查是否在代码和xml中有引用
   */
  open val resourceName: String,
  open val type: SearchPattern.Type
) {
  var excludeNames: ArrayList<String> = ArrayList()
  var dryRun = false

  abstract fun removeEach(resDirFile: File, scanTargetFileTexts: String)

  fun createSearchPattern(target: String): String {
    //    println("匹配正则: $searchPattern")
    return SearchPattern.create(resourceName, target, type)
  }

  /**
   * 根据fileType 删除多余文件
   */
  fun remove(
    moduleSrcDirs: ArrayList<String>,
    scanTargetFileTexts: String,
    extension: RemoveResExt
  ) {
    this.dryRun = extension.dryRun
    this.excludeNames = extension.excludeNames

    //todo 兼容多渠道
    moduleSrcDirs.forEach {
      ColoredLogger.logBlue("2,Checking [$fileType] in $it")

      var resDirFile = File("$it/src/main/res")
      if (resDirFile.exists()) {
        removeEach(resDirFile, scanTargetFileTexts)
      }

      resDirFile = File("$it/res")
      if (resDirFile.exists()) {
        removeEach(resDirFile, scanTargetFileTexts)
      }
    }
  }

  fun checkTargetTextMatches(targetText: String, scanTargetFileTexts: String): Boolean {
//    ColoredLogger.logGreen("检查 $fileType 类型文件是否被使用，文件名: $targetText.$fileType")
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

  /**
   * 是否能部分匹配上?
   */
  fun isPatternMatched(fileText: String, pattern: String): Boolean {
    val matcher = Pattern.compile(pattern).matcher(fileText);
    return matcher.find()
  }

  companion object {

    var moduleSrcDirs = ArrayList<String>()

  }
}
