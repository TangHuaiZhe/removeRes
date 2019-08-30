package internal.filetype

class AnimFileRemover constructor(
  override val fileType: String = "anim",
  override val resType: String = "anim"
) : FileRemover(
  fileType,
  resType
)