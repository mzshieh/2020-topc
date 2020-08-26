import java.math.BigInteger
import java.math.BigDecimal

private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextToks() = nextLine().split(" ")
private fun nextInts() = nextToks().map{it.toInt()}

data class BigFrac(val num: BigInteger, val den: BigInteger = BigInteger.ONE)

fun BigFrac.conanical(): BigFrac {
    val gcd = num.gcd(den)
    if (den < BigInteger.ZERO)
        return BigFrac(-num/gcd, -den/gcd)
    return BigFrac(num/gcd, den/gcd)
}

operator fun BigFrac.unaryMinus() = BigFrac(-num, den)
operator fun BigFrac.plus(x: BigFrac): BigFrac =
    BigFrac(num*x.den + den*x.num, den*x.den).conanical()
operator fun BigFrac.minus(x: BigFrac): BigFrac =
    BigFrac(num*x.den - den*x.num, den*x.den).conanical()
operator fun BigFrac.times(x: BigFrac): BigFrac =
    BigFrac(num*x.num, den*x.den).conanical()
operator fun BigFrac.div(x: BigFrac): BigFrac = 
    BigFrac(num*x.den, den*x.num).conanical()
operator fun BigFrac.compareTo(x: BigFrac): Int = 
    (this - x).num.compareTo(BigInteger.ZERO)

// inner product: %
operator fun Pair<BigFrac,BigFrac>.rem(x: Pair<BigFrac,BigFrac>): BigFrac =
    first * x.first + second * x.second
// cross product: *
operator fun Pair<BigFrac,BigFrac>.times(x: Pair<BigFrac,BigFrac>): BigFrac =
    first * x.second - second * x.first
// scalar product: BigFrac * Pair<BigFrac, BigFrac>
operator fun BigFrac.times(x: Pair<BigFrac,BigFrac>): Pair<BigFrac,BigFrac> =
    Pair(this * x.first, this * x.second)
// scalar product: Pair<BigFrac, BigFrac> * BigFrac
operator fun Pair<BigFrac,BigFrac>.times(x: BigFrac): Pair<BigFrac,BigFrac> =
    Pair(first * x, second * x)

fun BigInteger.toBigFrac() = BigFrac(this)
fun Int.toBigFrac() = this.toBigInteger().toBigFrac()
fun BigFrac.toBigDecimal(scale: Int = 80) = 
    BigDecimal(num, scale) / BigDecimal(den)

private fun nextFracs() = nextToks().map{it.toBigInteger().toBigFrac()}

fun main() {
    val (n, _r) = nextInts()
    val r = _r.toBigFrac()
    var pts = (1..n).map{nextFracs()}
}
