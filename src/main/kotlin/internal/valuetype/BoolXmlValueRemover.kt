package internal.valuetype

/**
 * author: tang
 * created on: 2019-08-12 11:15
 * description:
 */
class BoolXmlValueRemover(
  fileType: String = "bool",
  resourceName: String = "bool",
  tagName: String = "bool"
) : XmlValueRemover(fileType, resourceName, tagName)