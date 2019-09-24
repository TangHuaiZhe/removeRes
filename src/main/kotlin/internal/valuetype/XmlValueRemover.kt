package internal.valuetype

import internal.AbstractRemover
import internal.filetype.matchLast
import org.jdom2.Content
import org.jdom2.Document
import org.jdom2.Element
import org.jdom2.Namespace
import org.jdom2.Text
import org.jdom2.Verifier
import org.jdom2.input.SAXBuilder
import org.jdom2.output.EscapeStrategy
import org.jdom2.output.Format
import org.jdom2.output.LineSeparator
import org.jdom2.output.XMLOutputter
import util.LogUtil
import util.SearchPattern
import util.ThreadPoolManager
import java.io.File
import java.io.StringWriter

/**
 * author: tang
 * created on: 2019-08-11 21:08
 * description:
 */
open class XmlValueRemover constructor(
  /**
   * 资源文件名,
   * 如colors.xml的color
   * dimens.xml的dimens
   */
  fileType: String,
  /**
   * R.xx.name的xx
   */
  resourceName: String,
  /**
   * <`dimen` name="width">
   * <`string` name="app_name">
   */
  open var tagName: String,
  type: SearchPattern.Type = SearchPattern.Type.DEFAULT
) : AbstractRemover(fileType, resourceName, type) {

  override fun removeEach(resDirFile: File, scanTargetFileTexts: String) {
    resDirFile.walk().filter { it.isDirectory && it.matchLast("values") }.forEach {
      // 遍历 res-values 目录下的所有文件
      it.walk().filter { it1 -> !it1.isDirectory && it1.exists() }.forEach { it2 ->
        ThreadPoolManager.instance.execute(Runnable {
          if (isPatternMatched(it2.name, "$fileType.*")) {
            removeTagIfNeed(it2, scanTargetFileTexts)
          }
        })
      }
    }
  }

  private fun removeTagIfNeed(file: File, scanTargetFileTexts: String) {
    if (isMatchedExcludeNames(file.path)) {
      LogUtil.yellow("[" + fileType + "]   Ignore checking " + file.name)
      return
    }

    var isFileChanged = false

    val doc = SAXBuilder().build(file)
    val iterator = doc.rootElement.content.iterator()

    var isAfterRemoved = false

    while (iterator.hasNext()) {
      val content = iterator.next()

      // Remove line break after element is removed
      if (isAfterRemoved && content.cType == Content.CType.Text) {
        val text = content as Text
        if (text.text.contains("\n")) {
          if (!dryRun) {
            iterator.remove()
          }
        }
        isAfterRemoved = false
      } else if (content.cType == Content.CType.Element) {
        val element = content as Element
        if (element.name == tagName) {
          val attr = element.getAttribute("name")

          if (attr != null) {
            // Check the element has tools:override attribute
            val attribute = element.getAttribute("override", TOOLS_NAMESPACE)
            if (attribute?.value == "true") {
              LogUtil.yellow(
                "["
                    + fileType
                    + "]   Skip checking "
                    + attr.value
                    + " which has tools:override in "
                    + file.name
              )
              continue
            }

            val isMatched = checkTargetTextMatches(attr.value, scanTargetFileTexts)

            if (!isMatched) {
              LogUtil.green(
                "[" + fileType + "]   Remove " + attr.value + " in " + file.name
              )
              if (!dryRun) {
                iterator.remove()
              }
              isAfterRemoved = true
              isFileChanged = true
            }
          }
        }
      }
    }

    if (isFileChanged) {
      if (!dryRun) {
        saveFile(doc, file)
        removeFileIfNeed(file)
      }
    } else {
      LogUtil.info("[" + fileType + "]   No unused tags in " + file.name)
    }
  }

  private fun removeFileIfNeed(file: File) {
    val doc = SAXBuilder().build(file)
    if (doc.rootElement.getChildren(tagName).size == 0) {
      LogUtil.green("[" + fileType + "]   Remove " + file.name + ".")
      file.delete()
    }
  }

  companion object {

    private fun saveFile(doc: Document, file: File) {
      val stringWriter = StringWriter()


      XMLOutputter().apply {
        format = Format.getRawFormat()
        format.setLineSeparator(LineSeparator.SYSTEM)
        format.textMode = Format.TextMode.PRESERVE
        format.encoding = "utf-8"
        format.escapeStrategy = ESCAPE_STRATEGY
        output(doc, stringWriter)
//            output(doc, new FileWriter(file))
//            output(doc, System.out)
      }
      // TODO This is a temporary fix to remove extra spaces in last </resources>.
      file.writeText(
        stringWriter.toString().replaceFirst("""\n\s+<\/resources>""", """\n</resources>""")
      )
    }

    private val ESCAPE_STRATEGY = EscapeStrategy { ch ->
      // To support
      Verifier.isHighSurrogate(ch) || 60 == ch.toInt().ushr(10)
    }
    private val TOOLS_NAMESPACE =
      Namespace.getNamespace("tools", "http://schemas.android.com/tools")
  }
}
