package internal.valuetype

import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-12 14:47
 * description:
 */
class DimenXmlValueRemoverTest {

  private val remover: XmlValueRemover = DimenXmlValueRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("text_medium")
    var fileText = "R.dimen.text_medium;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.dimen.text_medium,"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.dimen.text_medium)"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.dimen.text"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@dimen/text_medium\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@dimen/text_medium<"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@dimen/text_medium2\""
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@style/text_medium"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("dimen", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("dimen", remover.resType)
  }

  @Test
  fun getTagName() {
    Assert.assertEquals("dimen", remover.tagName)
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.mainType)
  }
}