package internal.valuetype

/**
 * author: tang
 * created on: 2019-08-12 11:20
 * description:
 */
class IdXmlValueRemover(
  fileType: String = "id",
  resourceName: String = "id",
  tagName: String = "item"
) : XmlValueRemover(fileType, resourceName, tagName)