package internal

import internal.valuetype.AttrXmlValueRemover
import org.junit.Assert
import org.junit.Test

/**
 * author: tang created on: 2019-08-16 11:21 description:
 */
class AbstractRemoverTest{
  val remover = AttrXmlValueRemover()

  @Test
  fun testIsPatternMatched(){
    Assert.assertTrue(
    remover.isPatternMatched("/Users/tang/Code/lianxin/zhangxin/social_main/imageeditengine/src/main/res/values/attrs.xml",
      "attr.*"))
  }

}