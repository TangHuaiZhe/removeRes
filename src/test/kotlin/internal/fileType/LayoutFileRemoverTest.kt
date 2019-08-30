package internal.fileType

import internal.filetype.FileRemover
import internal.filetype.LayoutFileRemover
import org.junit.Assert
import org.junit.Test
import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-09 11:08
 * description:
 */
class LayoutFileRemoverTest {

  private val remover: FileRemover = LayoutFileRemover()

  @Test
  fun testPatternMatches() {
    //1：@layout/开头，必定以"结尾,或<结尾 如<item name="android:windowEnterAnimation">@anim/wifipay_anim_pw_push_bottom_in</item>
    //2: R.layout.开头，要么接name后)结尾，要么,符号,考虑到kotlin，也有可能是换行符
    var pattern = remover.createSearchPattern("activity_main")

    var fileText = "R.layout.activity_main);getPayCa"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "myLayout = R.layout.activity_main;"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "myLayout = R.layout.activity_main\n"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))


    fileText = "inflater.inflate(R.layout.activity_main, null);"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "inflater.inflate(R.layout.activity_main2, null);"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "R.layout.fragment_main"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))


    fileText = "@layout/activity_main\""
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@layout/activity_main<"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "ActivityMainBinding"
    Assert.assertTrue(remover.isPatternMatched(fileText, pattern))

    fileText = "@layout/activity_main2\""
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))

    fileText = "@menu/activity_main"
    Assert.assertFalse(remover.isPatternMatched(fileText, pattern))
  }

  @Test
  fun getFileType() {
    Assert.assertEquals("layout", remover.fileType)
  }

  @Test
  fun getResourceName() {
    Assert.assertEquals("layout", remover.resType)
  }

  @Test
  fun getType() {
    Assert.assertEquals(SearchPattern.Type.LAYOUT, remover.mainType)
  }
}