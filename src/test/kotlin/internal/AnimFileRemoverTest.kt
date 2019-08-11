package internal

import internal.filetype.AbstractRemover
import internal.filetype.AnimFileRemover
import internal.filetype.FileRemover
import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-09 13:21
 * description:
 */
class AnimFileRemoverTest {
  private val remover: FileRemover = AnimFileRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("fade_transition")
    var fileText = "R.anim.fade_transition\n"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.anim.fade_transition)"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.anim.fade_transition\""
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@anim/fade_transition<"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.animator.fade_transition\""
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.anim.scale_transition"
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@animator/fade_transition"
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("anim", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("anim", remover.resourceName)
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.type)
  }

}