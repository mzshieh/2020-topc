import java.math.BigInteger
import java.math.BigDecimal
import kotlin.random.Random
import kotlin.math.atan2
import java.util.TreeSet

private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextToks() = nextLine().split(" ")
private fun nextInts() = nextToks().map{it.toInt()}

data class BigFrac(val num: BigInteger, val den: BigInteger = BigInteger.ONE)

typealias Point = Pair<BigFrac, BigFrac>

val half = BigFrac(BigInteger.ONE, BigInteger.TWO)

fun BigFrac.conanical(): BigFrac {
    val gcd = num.gcd(den)
    if (den < BigInteger.ZERO)
        return BigFrac(-num/gcd, -den/gcd)
    return BigFrac(num/gcd, den/gcd)
}

operator fun BigFrac.unaryMinus() = BigFrac(-num, den)
operator fun BigFrac.plus(x: BigFrac): BigFrac =
    BigFrac(num*x.den + den*x.num, den*x.den)//.conanical()
operator fun BigFrac.minus(x: BigFrac): BigFrac =
    BigFrac(num*x.den - den*x.num, den*x.den)//.conanical()
operator fun BigFrac.times(x: BigFrac): BigFrac =
    BigFrac(num*x.num, den*x.den)//.conanical()
operator fun BigFrac.div(x: BigFrac): BigFrac = 
    BigFrac(num*x.den, den*x.num)//.conanical()
operator fun BigFrac.compareTo(x: BigFrac): Int = 
    (this - x).num.compareTo(BigInteger.ZERO)
fun BigFrac.sqrt(scale: Int = 100): BigFrac {
    val conan = conanical()
    val (x, rx) = conan.num.sqrtAndRemainder()
    val (y, ry) = conan.den.sqrtAndRemainder()
    if (rx == BigInteger.ZERO && ry == BigInteger.ZERO)
        return BigFrac(x, y)
    val frac = toBigDecimal(scale*2).sqrt(java.math.MathContext.DECIMAL128)
    val d = BigInteger.TEN.pow(frac.scale())
    val n = (frac * d.toBigDecimal()).toBigInteger()
    return BigFrac(n, d)
}

operator fun Point.plus(x: Point): Point =
    Pair(first + x.first, second + x.second)
operator fun Point.minus(x: Point): Point =
    Pair(first - x.first, second - x.second)
// inner product: %
operator fun Point.rem(x: Point): BigFrac =
    first * x.first + second * x.second
infix fun Point.dot(x: Point): BigFrac =
    first * x.first + second * x.second
// cross product: *
operator fun Point.times(x: Point): BigFrac =
    first * x.second - second * x.first
infix fun Point.cross(x: Point): BigFrac =
    first * x.second - second * x.first
// scalar product: BigFrac * Point
operator fun BigFrac.times(x: Point): Point =
    Pair(this * x.first, this * x.second)
// scalar product: Point * BigFrac
operator fun Point.times(x: BigFrac): Point =
    Pair(first * x, second * x)
fun Point.mag2(): BigFrac = this dot this
fun Point.mag(): BigFrac = mag2().sqrt()

fun BigInteger.toBigFrac() = BigFrac(this)
fun Int.toBigFrac() = this.toBigInteger().toBigFrac()
fun BigFrac.toBigDecimal(scale: Int = 80) = 
    BigDecimal(num, scale) / BigDecimal(den, scale)
fun BigFrac.toDouble() = 
    toBigDecimal().toDouble()

private fun nextFracs() = nextToks().map{it.toBigInteger().toBigFrac()}

fun ext(p: Point, q: Point, r: Point): Point {
    val (x1, y1) = p
    val (x2, y2) = q
    val (x3, y3) = r
    val m1 = p dot p
    val m2 = q dot q
    val m3 = r dot r
    val num1 = m1 * y2 + m2 * y3 + m3 * y1 - y1 * m2 - y2 * m3 - y3 * m1
    val num2 = x1 * m2 + x2 * m3 + x3 * m1 - m1 * x2 - m2 * x3 - m3 * x1
    val den = x1 * y2 + x2 * y3 + x3 * y1 - y1 * x2 - y2 * x3 - y3 * x1
    return Pair(half * num1 / den, half * num2 / den)
}

fun center(pts: ArrayList<Point>, pass: Int, n: Int): Point {
    if (pass == 3) return ext(pts[0], pts[1], pts[2])
    if (n == 1) return pts[0]
    var ret = half * (pts[0] + pts[1])
    if (n == 2) return ret
    var radius = half * (pts[0] - pts[1])
    var dist2 = radius dot radius
    for (i in 2 until n) {
        val r = pts[i]-ret
        if (dist2 < r dot r) {
            /*
            if (pass < 2) {
                // must shuffle here!
                for (j in pass+1 until i) {
                    val k = Random.nextInt(pass, j+1)
                    pts[j] = pts[k].also{pts[k] = pts[j]}
                }
            }
            */
            pts[pass] = pts[i].also{pts[i] = pts[pass]}
            ret = center(pts, pass+1, i+1)
            radius = pts[0] - ret
            dist2 = radius dot radius
        }
    }
    return ret
}

fun nextPoints(n: Int): ArrayList<Point> {
    var fracs: List<List<BigFrac>> = (1..n).map{nextFracs()}
    return ArrayList<Point>(fracs.map{Point(it[0],it[1])})
}

fun BigFrac.toReadable(scale: Int = 10): String =
    this.toBigDecimal(scale).toPlainString()
fun Point.toReadable(scale: Int = 10): String =
    first.toReadable(scale) + " " + second.toReadable(scale)

class PolarCmp(val c: Point): Comparator<Point> {
    override fun compare(a: Point, b: Point): Int {
        val da = a - c
        val db = b - c
        val angleA = atan2(da.second.toDouble(), da.first.toDouble())
        val angleB = atan2(db.second.toDouble(), db.first.toDouble())
        return angleA.compareTo(angleB)
    }
}

fun solve(pts: ArrayList<Point>, safe: Point, r2: BigFrac): String {
    var n = pts.size - 1
    val pos = pts.indexOf(safe)
    pts[n] = pts[pos].also{pts[pos] = pts[n]}
    var nc = center(pts, 0, n)
    var nr2 = (nc - pts[0]).mag2()
    if (nr2 > r2) return "-1" 
    return "${safe.first.num} ${safe.second.num}\n" +
           "${nc.first.toReadable(20)} ${nc.second.toReadable(20)}"
}

fun main() {
    val (n, _r) = nextInts()
    val r = _r.toBigFrac()
    var pts = nextPoints(n)
    pts.shuffle()
    var c = center(pts, 0, pts.size)
    val radius = c - pts[0]
    val r2 = radius dot radius
    var ans = "-1"

    if (r2 < r * r) {
        val tune = 16
        val epsilon = BigFrac(BigInteger.ONE,BigInteger.TEN.pow(tune))
        val one = BigFrac(BigInteger.ONE)
        c = c + (r/radius.mag()-one+epsilon) * radius
        ans = "${pts[0].first.num} ${pts[0].second.num}\n" +
              "${c.first.toReadable(tune*2)} ${c.second.toReadable(tune*2)}"
    }
    else {
        val cand = TreeSet<Point>(PolarCmp(c))
        pts.filter{val dx = it - c
                   val x2 = dx dot dx
                   x2 >= r2}.forEach{cand.add(it)}
        for (p in cand) {
            val s = cand.higher(p) ?: cand.first()
            val t = cand.lower(p) ?: cand.last()
            if (s - p dot t - p > BigInteger.ZERO.toBigFrac()) {
                // at most 3 points pass this
                val attemp = solve(pts, p, r*r)
                if (attemp != "-1") {
                    ans = attemp
                    break
                }
            }
        }
    }
    println(ans)
}
