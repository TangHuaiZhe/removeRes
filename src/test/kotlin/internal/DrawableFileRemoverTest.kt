package internal

import internal.filetype.AbstractRemover
import internal.filetype.DrawableFileRemover
import internal.filetype.FileRemover
import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-09 15:25
 * description:
 */
class DrawableFileRemoverTest {

  private val remover: FileRemover = DrawableFileRemover()

  @Test
  fun testPatternMatches() {
    var pattern = remover.createSearchPattern("ic_settings")
    var fileText = "R.drawable.ic_settings\n"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.drawable.ic_settings)"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@drawable/ic_settings\""
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@drawable/ic_settings<"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.drawable.ic_setting"
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@mipmap/ic_settings"
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))


    pattern = remover.createSearchPattern("img_balloon.9")

    fileText = "@drawable/img_balloon\""
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

//        fileText = "@drawable/img_balloon2\""
//        Assert.assertFalse(FileRemover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("drawable", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("drawable", remover.resourceName)
  }

  @Test
  fun getType() {
    Assert.assertEquals(SearchPattern.Type.DRAWABLE, remover.type)
  }
}