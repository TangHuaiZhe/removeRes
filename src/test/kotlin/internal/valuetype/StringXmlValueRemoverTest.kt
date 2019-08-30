package internal.valuetype

import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang created on: 2019-08-12 13:20 description:
 */
class StringXmlValueRemoverTest {

  private val remover = StringXmlValueRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("app_name")
    var fileText = "R.string.app_name;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.string.app_name)"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.string.app_name:"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.string.app_name,"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.string.app_name ;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.string.app_name\n"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@string/app_name<"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@string/app_name("
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@string/app_name)"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@string/app_name\n"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@string/app_name\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@string/app_name2\""
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@style/app_name"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

  }

  @Test
  fun getFileType() {
    Assert.assertEquals("string", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("string", remover.resType)
  }

  @Test
  fun getTagName() {
    Assert.assertEquals("string", remover.tagName)
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.mainType)
  }
}