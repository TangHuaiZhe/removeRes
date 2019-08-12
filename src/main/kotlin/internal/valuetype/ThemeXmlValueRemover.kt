package internal.valuetype

import util.SearchPattern

/**
 * author: tang
 * created on: 2019-08-12 11:23
 * description:
 */
class ThemeXmlValueRemover(
  fileType: String = "theme",
  resourceName: String = "style",
  tagName: String = "style",
  type: SearchPattern.Type = SearchPattern.Type.STYLE
) : XmlValueRemover(fileType, resourceName, tagName, type)