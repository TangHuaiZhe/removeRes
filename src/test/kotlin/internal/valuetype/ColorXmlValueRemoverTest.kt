package internal.valuetype

import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-12 14:47
 * description:
 */
class ColorXmlValueRemoverTest {

  private val remover: XmlValueRemover = ColorXmlValueRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("primary")
    var fileText = "R.color.primary;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))
    fileText = "R.color.secondary"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@color/primary\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@color/primary<"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@color/primary:"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@color/primary"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))


    fileText = "@colorStateList/primary\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@color/primary2\""
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@style/primary"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("color", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("color", remover.resourceName)
  }

  @Test
  fun getTagName() {
    Assert.assertEquals("color", remover.tagName)
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.type)
  }
}