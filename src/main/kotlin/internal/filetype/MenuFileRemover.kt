package internal.filetype

class MenuFileRemover constructor(
  override val fileType: String = "menu",
  override val resType: String = "menu"
) :
  FileRemover(fileType, resType) {
}
