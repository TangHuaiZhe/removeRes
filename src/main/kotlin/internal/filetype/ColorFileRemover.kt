package internal.filetype

class ColorFileRemover constructor(
  override val fileType: String = "color",
  override val resType: String = "color"
) : FileRemover(
  fileType,
  resType
)
