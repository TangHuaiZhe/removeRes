package internal

import internal.filetype.AbstractRemover
import internal.filetype.AnimatorFileRemover
import internal.filetype.FileRemover
import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-09 12:36
 * description:
 */
class AnimatorFileRemoverTest {
  private val remover: FileRemover = AnimatorFileRemover()

  @Test
  fun testPatternMatches() {
    val pattern = remover.createSearchPattern("fade_animation")
    var fileText = "R.animator.fade_animation\n"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

//        fileText = "R.animator.fade_animation"
//        Assert.assertTrue(FileRemover.isPatternMatched(fileText, pattern))

    fileText = "R.animator.fade_animation)"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.animator.fade_animation\""
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@animator/fade_animation\""
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@animator/fade_animation<"
    Assert.assertTrue(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@animator/fade_animation2"
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "R.animator.scale_animation"
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))

    fileText = "@anim/fade_animation"
    Assert.assertFalse(AbstractRemover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("animator", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("animator", remover.resourceName)
  }

  @Test
  fun getResourceType() {
    Assert.assertEquals(SearchPattern.Type.DEFAULT, remover.type)
  }

}