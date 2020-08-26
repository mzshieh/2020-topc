import java.math.BigInteger
import java.math.BigDecimal
import java.util.TreeSet
import java.util.TreeMap
import kotlin.random.Random

private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextToks() = nextLine().split(" ")
private fun nextInts() = nextToks().map{it.toInt()}
private fun nextLongs() = nextToks().map{it.toLong()}

fun main() {
    val (n, dx, dy) = nextInts()
    val r = 5928325L
    val r2 = r * r
    var squares = TreeMap<Long,Long>((0L..r).map{it*it to it}.toMap())
    var output = HashSet<Pair<Long,Long>>()
    for (x in 1L..r) {
        val y = squares[r2-x*x]?:-1L
        if (y >= 0) {
            output.add(Pair(x,y))
            output.add(Pair(x,-y))
            output.add(Pair(-x,y))
            output.add(Pair(-x,-y))
        }
    }
    output.add(Pair(-r, 0))
    while (output.size < n) {
        val x = Random.nextLong(1L, r)
        val y = squares.lowerEntry(r2-x*x)?.value ?: 0
        when (Random.nextInt(4)) {
            0 -> output.add(Pair(x,y))
            1 -> output.add(Pair(x,-y))
            2 -> output.add(Pair(-x,y))
            3 -> output.add(Pair(-x,-y))
        }
    }
    println("$n ${r+1}")
    for ((x,y) in output) println("${x+dx} ${y+dy}")
}
