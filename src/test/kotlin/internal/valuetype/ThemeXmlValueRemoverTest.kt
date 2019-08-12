package internal.valuetype

import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang created on: 2019-08-12 13:20 description:
 */
class ThemeXmlValueRemoverTest {

  private val remover = ThemeXmlValueRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("AppTheme.Translucent")

    var fileText = "R.style.AppTheme_Translucent;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))


    fileText = "R.style.AppTheme_Translucent)"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.style.AppTheme.Trans"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@style/AppTheme.Translucent\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@style/AppTheme.Translucent<"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@style/AppTheme.Translucent."
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@style/AppTheme.Translucent2\""
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

  }

  @Test
  fun getFileType() {
    Assert.assertEquals("theme", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("style", remover.resourceName)
  }

  @Test
  fun getTagName() {
    Assert.assertEquals("style", remover.tagName)
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.STYLE, remover.type)
  }
}