package internal.valuetype

/**
 * author: tang
 * created on: 2019-08-12 11:17
 * description:
 */
class DimenXmlValueRemover(
  fileType: String = "dimen",
  resourceName: String = "dimen",
  tagName: String = "dimen"
) : XmlValueRemover(fileType, resourceName, tagName)