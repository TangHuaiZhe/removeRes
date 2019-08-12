package internal.valuetype

import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-12 14:05
 * description:
 */
class BoolXmlValueRemoverTest {

  private val remover: XmlValueRemover = BoolXmlValueRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("pref_value")
    var fileText = "R.bool.pref_value;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))
    fileText = "R.bool.pref"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@bool/pref_value\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@bool/pref_value<"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@bool/pref_value2\""
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@bool/pref_value"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("bool", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("bool", remover.resourceName)
  }

  @Test
  fun getTagName() {
    Assert.assertEquals("bool", remover.tagName)
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.type)
  }
}