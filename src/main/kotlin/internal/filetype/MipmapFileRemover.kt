package internal.filetype

import util.SearchPattern

class MipmapFileRemover constructor(
  override val fileType: String = "mipmap",
  override val resType: String = "mipmap",
  override val mainType: SearchPattern.Type = SearchPattern.Type.DRAWABLE
) : FileRemover(
  fileType,
  resType,
  mainType
)
