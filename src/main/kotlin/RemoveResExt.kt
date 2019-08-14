import internal.filetype.AbstractRemover
import java.util.ArrayList

/**
 * author: tang
 * created on: 2019-08-07 16:13
 * description:
 */
open class RemoveResExt {
  val NAME = "RemoveResExt"
  var extraRemovers: List<AbstractRemover> = ArrayList()
  var excludeNames: ArrayList<String> = ArrayList()
  var dryRun = false
  var openRemoveFile = true
  var openRemoveXmlValues = true

  companion object {
    const val name = "RemoveRes"
  }
}