package internal.valuetype

import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang created on: 2019-08-12 13:20 description:
 */
class StyleXmlValueRemoverTest {

  private val remover = StyleXmlValueRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("TitleTextAppearance")

    var fileText = "R.style.TitleTextAppearance;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.style.TitleTextAppearance)"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.style.TitleTextAppearance:"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.style.TitleTextAppearance,"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.style.TitleTextAppearance\n"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.style.TitleTextAppearance ;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.style.TitleTextAppear,"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))


    fileText = "@style/TitleTextAppearance\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@style/TitleTextAppearance<"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@style/TitleTextAppearance."
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@style/TitleTextAppearance2\""
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@theme/TitleTextAppearance"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("style", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("style", remover.resType)
  }

  @Test
  fun getTagName() {
    Assert.assertEquals("style", remover.tagName)
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.STYLE, remover.mainType)
  }
}