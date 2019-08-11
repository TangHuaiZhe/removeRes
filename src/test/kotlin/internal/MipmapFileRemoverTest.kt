package internal

import internal.filetype.AbstractRemover
import internal.filetype.FileRemover
import internal.filetype.MipmapFileRemover
import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-09 16:39
 * description:
 */
class MipmapFileRemoverTest {

  private val remover: FileRemover = MipmapFileRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("ic_launcher")
    var fileText = "R.mipmap.ic_launcher)"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.mipmap.ic_launcher\""
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@mipmap/ic_launcher\""
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@mipmap/ic_launcher<"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.drawable.ic_launch"
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@mipmap/ic_launcher_round\""
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))

  }

  @Test
  fun getFileType() {
    Assert.assertEquals("mipmap", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("mipmap", remover.resourceName)
  }

  @Test
  fun getType() {
    Assert.assertEquals(SearchPattern.Type.DRAWABLE, remover.type)
  }
}