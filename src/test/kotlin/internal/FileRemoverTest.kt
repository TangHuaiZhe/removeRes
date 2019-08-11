package internal

import internal.filetype.DrawableFileRemover
import internal.filetype.FileRemover
import org.junit.Assert
import org.junit.Test

/**
 * author: tang
 * created on: 2019-08-09 15:34
 * description:
 */
class FileRemoverTest {
  private val remover: FileRemover = DrawableFileRemover()

  @Test
  fun checkExcludeNames() {

    var excludeNames: List<String> = listOf("ic_launcher.xml")
    remover.excludeNames.clear()
    remover.excludeNames.addAll(excludeNames)
    Assert.assertTrue(remover.isMatchedExcludeNames("/res/mipmap/ic_launcher.xml"))

    excludeNames = listOf("ic_launcher")
    remover.excludeNames.clear()
    remover.excludeNames.addAll(excludeNames)
    Assert.assertTrue(remover.isMatchedExcludeNames("/res/mipmap/ic_launcher.xml"))


    excludeNames = listOf("ic_settings.xml", "ic_launcher.xml")
    remover.excludeNames.clear()
    remover.excludeNames.addAll(excludeNames)
    Assert.assertTrue(remover.isMatchedExcludeNames("/res/mipmap/ic_launcher.xml"))
  }

}