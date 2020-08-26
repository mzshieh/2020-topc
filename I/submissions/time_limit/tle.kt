import java.math.BigInteger
import java.util.TreeSet

fun Long.isSq(): Boolean {
    val (_, rem) = this.toBigInteger().sqrtAndRemainder()
    return rem.toString() == "0"
}

fun Long.sqrt(): Long {
    val (root, _) = this.toBigInteger().sqrtAndRemainder()
    return root.toLong()
}

fun Long.log(): Double = kotlin.math.log(this.toDouble(),2.0)

data class Frac(val num: BigInteger, val den: BigInteger = BigInteger.ONE)

operator fun Frac.compareTo(x: Frac) = (num*x.den).compareTo(den*x.num)

fun main() {
    var s = HashMap<Long, TreeSet<Frac>>()
    val n = readLine()?.toInt() ?: 10000
    for (i in 1..n) {
        for (j in i+1..n) {
            if (i.toBigInteger().gcd(j.toBigInteger())!=BigInteger.ONE) continue
            val c = (i.toLong() * i + j.toLong() * j)
            if (c > n.toLong() * n || !c.isSq()) continue
            val root = c.sqrt()
            val tree = s.getOrPut(root){TreeSet<Frac>(Comparator<Frac>{x, y -> x.compareTo(y)})}
            //s.put(root, new_value)
            tree.add(Frac(i.toBigInteger(),j.toBigInteger()))
            tree.add(Frac(j.toBigInteger(),i.toBigInteger()))
        }
    }
    //s.toList().sortedBy{(k, v) -> (v.size.toLong() shl 30) - k}.takeLast(10).forEach{println("$it")}
    val cand = s.toList().sortedBy{(k, v) -> (k.log() / v.size.toDouble())}.take(100)
    println(cand.map{(_, v) -> v.size}.sum())
    println(cand.map{(k, _) -> k.log()}.sum())
    println(cand.map{(k, _) -> k.toBigInteger()}.reduce{x, y -> x*y/x.gcd(y)})
    //println("$cand")
}
