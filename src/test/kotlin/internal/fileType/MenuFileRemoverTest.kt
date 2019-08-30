package internal.fileType

import internal.filetype.FileRemover
import internal.filetype.MenuFileRemover
import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-09 15:22
 * description:
 */
class MenuFileRemoverTest {

  private val remover: FileRemover = MenuFileRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("menu_main")
    var fileText = "R.menu.menu_main;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.menu.menu_main)"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.menu.menu_main)"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@menu/menu_main\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@menu/menu_main<"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "R.menu.menu_detail"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@menu/menu_main2\""
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@layout/menu_main"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.mainType)
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("menu", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("menu", remover.resType)
  }
}