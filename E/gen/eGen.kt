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

typealias Point = Pair<Long,Long>

infix fun Point.dot(x: Point): Long = first * x.first + second * x.second

fun Random.nextPoint(r: Long): Point {
    var ret = Pair(Random.nextLong(-r, r+1), Random.nextLong(-r, r+1))
    while (ret dot ret > r * r)
        ret = Pair(Random.nextLong(-r, r+1), Random.nextLong(-r, r+1))
    return ret
}

fun Point.compareTo(x: Point): Int = 
    if (first == x.first) second.compareTo(x.second) 
    else first.compareTo(x.first)

class PointCmp(): Comparator<Point> {
    override fun compare(a: Point, b: Point): Int {
        var res = (a dot a).compareTo(b dot b)
        if (res!=0) return res
        return a.compareTo(b)
    }
}

fun main(args: Array<String>) {
    val (n, dx, dy) = nextInts()
    val r = 5928325L
    var output = TreeSet<Point>(PointCmp())
    output.add(Pair(0,r))
    output.add(Pair(0,-r))
    output.add(Pair(r,0))
    while (output.size < n)
        output.add(Random.nextPoint(r))
    println("$n $r")
    for ((x,y) in output) println("${x+dx} ${y+dy}")
}
