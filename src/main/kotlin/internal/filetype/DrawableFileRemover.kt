package internal.filetype

import util.SearchPattern

class DrawableFileRemover constructor(
  override val fileType: String = "drawable",
  override val resourceName: String = "drawable",
  override val type: SearchPattern.Type = SearchPattern.Type.DRAWABLE
) : FileRemover(
    fileType,
    resourceName,
    type
)

