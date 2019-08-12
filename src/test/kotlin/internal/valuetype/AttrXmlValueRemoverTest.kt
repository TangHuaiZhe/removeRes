package internal.valuetype

import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang created on: 2019-08-12 13:20 description:
 */
class AttrXmlValueRemoverTest {

  private val remover: XmlValueRemover = AttrXmlValueRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("CustomView")
    var fileText = "R.styleable.CustomView;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))
    //todo 避免Android资源文件的干扰
//    fileText = "android.R.styleable.CustomView;"
//    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))
    fileText = "R.style.CustomView"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("attr", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("styleable", remover.resourceName)
  }

  @Test
  fun getTagName() {
    Assert.assertEquals("declare-styleable", remover.tagName)
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.type)
  }
}