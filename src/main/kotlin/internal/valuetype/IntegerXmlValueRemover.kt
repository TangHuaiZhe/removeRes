package internal.valuetype

/**
 * author: tang
 * created on: 2019-08-12 11:20
 * description:
 */
class IntegerXmlValueRemover(
  fileType: String = "integer",
  resourceName: String = "integer",
  tagName: String = "integer"
) : XmlValueRemover(fileType, resourceName, tagName)