package internal.valuetype

/**
 * author: tang
 * created on: 2019-08-12 10:27
 * description:
 */
class AttrXmlValueRemover constructor(
  fileType: String = "attr",
  resourceName: String = "styleable",
  /**
   * Tag name to extract value from xml like <`dimen` name="width">, <`string` name="app_name">
   */
  override var tagName: String = "declare-styleable"
) : XmlValueRemover(
  fileType,
  resourceName,
  tagName
)