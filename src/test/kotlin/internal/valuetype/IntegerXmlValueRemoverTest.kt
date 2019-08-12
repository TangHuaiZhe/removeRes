package internal.valuetype

import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang created on: 2019-08-12 13:20 description:
 */
class IntegerXmlValueRemoverTest {

  private val remover = IntegerXmlValueRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("max_length")
    var fileText = "R.integer.max_length;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.integer.max;"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@integer/max_length\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@integer/max_length<"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@integer/max_length2\""
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@integer/max_length"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("integer", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("integer", remover.resourceName)
  }

  @Test
  fun getTagName() {
    Assert.assertEquals("integer", remover.tagName)
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.type)
  }
}