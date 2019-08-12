package internal.valuetype

/**
 * author: tang
 * created on: 2019-08-12 11:17
 * description:
 */
class ColorXmlValueRemover(
  fileType: String = "color",
  resourceName: String = "color",
  tagName: String = "color"
) : XmlValueRemover(fileType, resourceName, tagName)