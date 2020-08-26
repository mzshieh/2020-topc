private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextToks() = nextLine().split(" ")
private fun nextInts() = nextToks().map{it.toInt()}

fun main() {
    val n = nextInt()
    val cans = HashSet<Int>(nextInts())
    println(cans.size)
}
