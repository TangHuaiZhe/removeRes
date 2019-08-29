package internal.filetype

import internal.AbstractRemover
import util.ColoredLogger
import util.SearchPattern
import java.io.File

open class FileRemover constructor(
  fileType: String,
  resourceName: String,
  type: SearchPattern.Type = SearchPattern.Type.DEFAULT
) : AbstractRemover(fileType, resourceName, type) {

  override fun removeEach(resDirFile: File, scanTargetFileTexts: String) {
    resDirFile.walk().forEach { it ->
      if (it.isDirectory && it.matchLast(fileType)) {
        ColoredLogger.log("3,check Dir: ${it.path} , prepare to removeFileIfNeed")
        it.walk().filter { !it.isDirectory }.forEach {
          removeFileIfNeed(it, scanTargetFileTexts)
        }
      }
    }
  }

  private fun removeFileIfNeed(file: File, scanTargetFileTexts: String): Boolean {
    if (isMatchedExcludeNames(file.path)) {
      ColoredLogger.logYellow("4,[$fileType]   Ignore checking ${file.name}")
      return false
    }

    val isMatched: Boolean = checkTargetTextMatches(
      file.nameWithoutExtension, scanTargetFileTexts
    )

    return if (!isMatched) {
      ColoredLogger.logGreen("4,[$fileType]  ${file.name} 未使用；Remove!!")
      if (!dryRun) {
        file.delete()
      }
      true
    } else {
      false
    }

  }

  companion object {
    fun extractFileName(file: File): String {
      val lastIndexOf = file.name.lastIndexOf('.')
      return if (file.name.length <= lastIndexOf) {
        file.name
      } else {
        file.name.substring(0, lastIndexOf)
      }
    }
  }

}

fun File.matchLast(fileType: String): Boolean {
  // (\/${type}-.*$)|(\/${type}$)
  val regex = Regex("""(.*/$fileType-.*$)|(.*/$fileType$)""")
//  println("the regex is $regex")
//  println("file is $path")
  return this.path.matches(regex)
}

fun main() {
  val file = File("app/res/layout")
  print(file.matchLast("layout"))
}

