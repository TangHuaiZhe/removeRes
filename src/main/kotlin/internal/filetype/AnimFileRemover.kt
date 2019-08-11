package internal.filetype

class AnimFileRemover constructor(
  override val fileType: String = "anim",
  override val resourceName: String = "anim"
) : FileRemover(
    fileType,
    resourceName
)