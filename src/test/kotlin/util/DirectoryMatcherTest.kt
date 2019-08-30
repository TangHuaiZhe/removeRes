package util

import internal.filetype.matchLast
import org.junit.Assert
import org.junit.Test
import java.io.File

/**
 * author: tang
 * created on: 2019-08-12 18:14
 * description:
 */
class DirectoryMatcherTest {

  @Test
  fun testDirMatch() {
    //test match mainType with the last part of dir name

    var dirName = "/values"
    var type = "values"
    Assert.assertTrue(File(dirName).matchLast(type))

    dirName = "/values-v21"
    type = "values"
    Assert.assertTrue(File(dirName).matchLast(type))

    dirName = "/drawable"
    type = "drawable"
    Assert.assertTrue(File(dirName).matchLast(type))

    dirName = "xxxx/drawable-hdpi"
    type = "drawable"
    Assert.assertTrue(File(dirName).matchLast(type))

    dirName = "/drawable-v21"
    type = "drawable"
    Assert.assertTrue(File(dirName).matchLast(type))

    dirName = "/anim"
    type = "anim"
    Assert.assertTrue(File(dirName).matchLast(type))

    dirName = "/animator"
    type = "animator"
    Assert.assertTrue(File(dirName).matchLast(type))

    dirName = "/xml/shortcuts.xml"
    type = "xml"
    Assert.assertFalse(File(dirName).matchLast(type))


    dirName = "/valuesdir"
    type = "values"
    Assert.assertFalse(File(dirName).matchLast(type))

    dirName = "values"
    type = "values"
    Assert.assertFalse(File(dirName).matchLast(type))

    dirName = "/animator"
    type = "anim"
    Assert.assertFalse(File(dirName).matchLast(type))

    dirName = "/anim"
    type = "animator"
    Assert.assertFalse(File(dirName).matchLast(type))

    dirName = "/values/strings.xml"
    type = "xml"
    Assert.assertFalse(File(dirName).matchLast(type))
  }
}