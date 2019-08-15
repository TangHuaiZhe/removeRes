package internal.filetype

class MenuFileRemover constructor(
  override val fileType: String = "menu",
  override val resourceName: String = "menu"
) :
  FileRemover(fileType, resourceName) {
}
