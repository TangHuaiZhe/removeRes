package internal

import RemoveResExt
import util.LogUtil
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
  open val resType: String,
  open val mainType: SearchPattern.Type
) {
  var excludeNames: ArrayList<String> = ArrayList()
  var dryRun = false

  abstract fun removeEach(resDirFile: File, scanTargetFileTexts: String)

  fun createSearchPattern(resName: String): String {
    //    println("匹配正则: $searchPattern")
    return SearchPattern.create(resType, resName, mainType)
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
      LogUtil.blue("2,Checking [$fileType] in $it")

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
//    LogUtil.green("检查 $fileType 类型文件是否被使用，文件名: $targetText.$fileType")
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
        + ", resType: "
        + resType
        + ", mainType: "
        + mainType.toString())
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
}
