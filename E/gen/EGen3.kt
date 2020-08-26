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
    for (x in 0L..r) {
        val y = squares[r2-x*x]?:-1L
        if (y >= 0) {
            output.add(Pair(x,y))
            output.add(Pair(x,-y))
            output.add(Pair(-x,y))
            output.add(Pair(-x,-y))
        }
    }
    val (X,Y) = Pair(3222300L, 4976125L)
    output = output.filter{(x,y) -> x*X + Y*y > 0L}.toHashSet()
    output.add(Pair(-4976124L, 3222301))
    while (output.size < n-1) {
        val x = Random.nextLong(1L, r)
        val y = squares.lowerEntry(r2-x*x)?.value ?: 0L
        var p = Pair(0L,0L)
        when (Random.nextInt(4)) {
            0 -> p = Pair(x,y)
            1 -> p = Pair(x,-y)
            2 -> p = Pair(-x,y)
            3 -> p = Pair(-x,-y)
        }
        if (p.first*X + p.second*Y > 0L) output.add(p)
    }
    output.add(Pair(-X,-Y))
    println("$n $r")
    for ((x,y) in output) println("${x+dx} ${y+dy}")
}
