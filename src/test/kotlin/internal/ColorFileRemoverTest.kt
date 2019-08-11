package internal

import internal.filetype.AbstractRemover
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
    var fileText = "R.color.primary"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.color.primary\n"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.color.primary)"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@color/primary\""
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@color/primary<"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

//        fileText = "@color/primary2"
//        Assert.assertFalse(FileRemover.isPatternMatched(fileText, pattern))

    fileText = "R.color.secondary"
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@style/primary\""
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.type)
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("color", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("color", remover.resourceName)
  }
}