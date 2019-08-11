package internal.filetype

import util.SearchPattern

class LayoutFileRemover constructor(
  override val fileType: String = "layout",
  override val resourceName: String = "layout",
  override val type: SearchPattern.Type = SearchPattern.Type.LAYOUT
) : FileRemover(
    fileType,
    resourceName,
    type
)
