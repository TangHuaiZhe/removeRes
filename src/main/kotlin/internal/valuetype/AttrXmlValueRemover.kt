package internal.valuetype

/**
 * author: tang
 * created on: 2019-08-12 10:27
 * description:
 */
class AttrXmlValueRemover constructor(
  fileType: String = "attrs",
  resourceName: String = "styleable",
  override var tagName: String = "declare-styleable"
) : XmlValueRemover(
  fileType,
  resourceName,
  tagName
)