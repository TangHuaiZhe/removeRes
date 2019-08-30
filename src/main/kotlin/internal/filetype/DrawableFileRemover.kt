package internal.filetype

import util.SearchPattern

class DrawableFileRemover constructor(
  override val fileType: String = "drawable",
  override val resType: String = "drawable",
  override val mainType: SearchPattern.Type = SearchPattern.Type.DRAWABLE
) : FileRemover(
  fileType,
  resType,
  mainType
)

