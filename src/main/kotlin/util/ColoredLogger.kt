package util

object ColoredLogger {

  private const val ANSI_RESET = "\u001B[0m"
  private const val ANSI_GREEN = "\u001B[32m"
  private const val ANSI_YELLOW = "\u001B[33m"
  private const val ANSI_BLUE = "\u001B[34m"

  fun logGreen(text: String) {
    println(ANSI_GREEN + text + ANSI_RESET)
  }

  fun logYellow(text: String) {
    println(ANSI_YELLOW + text + ANSI_RESET)
  }

  fun logBlue(text: String) {
    println(ANSI_BLUE + text + ANSI_RESET)
  }

  fun log(text: String) {
    println(text)
  }
}
