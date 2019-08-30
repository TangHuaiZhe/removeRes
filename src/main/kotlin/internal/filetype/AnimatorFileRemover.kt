package internal.filetype

class AnimatorFileRemover constructor(
  override val fileType: String = "animator",
  override val resType: String = "animator"
) : FileRemover(
  fileType,
  resType
)

