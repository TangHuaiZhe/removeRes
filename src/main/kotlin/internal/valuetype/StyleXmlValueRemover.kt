package internal.valuetype

import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-12 11:22
 * description:
 */
class StyleXmlValueRemover(
  fileType: String = "style",
  resourceName: String = "style",
  tagName: String = "style",
  type: SearchPattern.Type = SearchPattern.Type.STYLE
) : XmlValueRemover(fileType, resourceName, tagName, type)