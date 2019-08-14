package internal.valuetype

import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang created on: 2019-08-12 13:20 description:
 */
class IdXmlValueRemoverTest {

  private val remover = IdXmlValueRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("view_id")
    var fileText = "R.id.view_id;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.id.view_id)"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.id.view;"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@id/view_id\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@id/view_id\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))
    fileText = "@id/view_id<"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))
    fileText = "@id/view_id2\""
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("id", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("id", remover.resourceName)
  }

  @Test
  fun getTagName() {
    Assert.assertEquals("item", remover.tagName)
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.type)
  }
}