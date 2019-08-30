package internal.filetype

import util.SearchPattern

class LayoutFileRemover constructor(
  override val fileType: String = "layout",
  override val resType: String = "layout",
  override val mainType: SearchPattern.Type = SearchPattern.Type.LAYOUT
) : FileRemover(
  fileType,
  resType,
  mainType
)
