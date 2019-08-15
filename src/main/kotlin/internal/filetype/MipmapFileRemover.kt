package internal.filetype

import util.SearchPattern

class MipmapFileRemover constructor(
  override val fileType: String = "mipmap",
  override val resourceName: String = "mipmap",
  override val type: SearchPattern.Type = SearchPattern.Type.DRAWABLE
) : FileRemover(
  fileType,
  resourceName,
  type
)
