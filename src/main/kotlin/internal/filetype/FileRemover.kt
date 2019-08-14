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

  override fun removeEach(resDirFile: File) {
    delFile(resDirFile)
  }

  private fun removeFileIfNeed(file: File): Boolean {
    if (isMatchedExcludeNames(file.path)) {
      ColoredLogger.logYellow("[$fileType]   Ignore checking ${file.name}")
      return false
    }

    val isMatched: Boolean = checkTargetTextMatches(
        file.nameWithoutExtension)

    return if (!isMatched) {
      ColoredLogger.logGreen("[$fileType]  ${file.name} 未使用；Remove!!")
      if (!dryRun) {
        file.delete()
      }
      true
    } else {
      false
    }

  }

  private fun delFile(file: File) {
    file.walk().forEach { it ->
      if (it.isDirectory && it.matchLast(fileType)) {
        ColoredLogger.logGreen("check Dir: ${it.name} , prepare to removeFileIfNeed")
        it.walk().filter { !it.isDirectory }.forEach {
          removeFileIfNeed(it)
        }
      }
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
  val file = File("/Users/tang/Code/Shengpay/wifi-pay-sdk/app/res/layout")
  print(file.matchLast("layout"))
}

