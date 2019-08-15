package internal.filetype

class AnimatorFileRemover constructor(
  override val fileType: String = "animator",
  override val resourceName: String = "animator"
) : FileRemover(
  fileType,
  resourceName
)

