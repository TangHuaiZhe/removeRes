package internal.fileType

import internal.filetype.ColorFileRemover
import internal.filetype.FileRemover
import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-09 15:03
 * description:
 */
class ColorFileRemoverTest {

  private val remover: FileRemover = ColorFileRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("primary")
    var fileText = "R.color.primary;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.color.primary\n"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.color.primary)"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@color/primary\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@color/primary<"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

//        fileText = "@color/primary2"
//        Assert.assertFalse(FileRemover.isPatternMatched(fileText, pattern))

    fileText = "R.color.secondary"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@style/primary\""
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.mainType)
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("color", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("color", remover.resType)
  }
}