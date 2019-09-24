package util

object LogUtil {

  private const val ANSI_RESET = "\u001B[0m"
  private const val ANSI_GREEN = "\u001B[32m"
  private const val ANSI_YELLOW = "\u001B[33m"
  private const val ANSI_BLUE = "\u001B[34m"

  fun green(text: String) {
    if (RemoveResPlugin.logOpen) {
      println(ANSI_GREEN + text + ANSI_RESET)
    }
  }

  fun yellow(text: String) {
    if (RemoveResPlugin.logOpen) {
      println(ANSI_YELLOW + text + ANSI_RESET)
    }
  }

  fun blue(text: String) {
    if (RemoveResPlugin.logOpen) {
      println(ANSI_BLUE + text + ANSI_RESET)
    }
  }

  fun info(text: String) {
    if (RemoveResPlugin.logOpen) {
      println(text)
    }
  }
}
