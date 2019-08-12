package util

import org.junit.Assert
import org.junit.Test

/**
 * author: tang created on: 2019-08-12 16:32 description:
 */
class SearchPatternTest {

  @Test
  fun toCamelCaseWithUnderscore() {
    var tagName = "TextTitle"
    Assert.assertEquals("TextTitle", SearchPattern.toCamelCaseWithUnderscore(tagName))

    tagName = "TextTitle.long"
    Assert.assertEquals("TextTitle_Long", SearchPattern.toCamelCaseWithUnderscore(tagName))

    tagName = "TextTitle.Long"
    Assert.assertEquals("TextTitle_Long", SearchPattern.toCamelCaseWithUnderscore(tagName))

    tagName = "textTitle.long"
    Assert.assertEquals("textTitle_Long", SearchPattern.toCamelCaseWithUnderscore(tagName))

    tagName = "text_title.long"
    Assert.assertEquals("text_title_Long", SearchPattern.toCamelCaseWithUnderscore(tagName))

    tagName = "TextTitle.Long.Gray"
    Assert.assertEquals("TextTitle_Long_Gray", SearchPattern.toCamelCaseWithUnderscore(tagName))
  }
}