private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextToks() = nextLine().split(" ")
private fun nextInts() = nextToks().map{it.toInt()}

fun main() {
    try {
        while (true) nextInts()
    }
    catch (e: NullPointerException) {
        println("hello")
    }
}
