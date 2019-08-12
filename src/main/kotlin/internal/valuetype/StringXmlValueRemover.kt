package internal.valuetype

/**
 * author: tang
 * created on: 2019-08-12 11:21
 * description:
 */
class StringXmlValueRemover(
  fileType: String="string",
  resourceName: String="string",
  tagName: String="string"
) : XmlValueRemover(fileType, resourceName, tagName)