package internal.filetype

class ColorFileRemover constructor(
  override val fileType: String = "color",
  override val resourceName: String = "color"
) : FileRemover(
    fileType,
    resourceName
)
