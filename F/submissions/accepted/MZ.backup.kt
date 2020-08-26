import kotlin.random.Random

private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextToks() = nextLine().split(" ")
private fun nextInts() = nextToks().map{it.toInt()}
private fun nextLongs() = nextToks().map{it.toLong()}
private fun modPow(a: Long, x: Long, p: Long) = a.toBigInteger()
                                                 .modPow(x.toBigInteger(),
                                                         p.toBigInteger())
                                                 .toLong()
private fun modInv(a: Long, p: Long) = modPow(a,p-2,p)

data class Complex(val real: Long, val im: Long, val p: Long, val w2: Long) {
    fun mul(other: Complex): Complex {
        val r = (this.real * other.real + (this.im * other.im % p) * w2) % p
        val i = (this.real * other.im + this.im * other.real) %p
        // println("this $this")
        // println("otehr $other")
        // println("res ${Complex(r,i,this.p, this.w2)}$")
        return Complex(r, i, this.p, this.w2)
    }
    fun pow(x: Long): Complex {
        if (x == 0L) return Complex(1, 0, this.p, this.w2)
        val half = this.pow(x/2)
        var res = half.mul(half)
        if (x%2 == 1L) res = this.mul(res)
        // println("$this.pow($x) = $res")
        return res
    }
}

fun modSqrt(n: Long, p: Long): Long {
    if (n==0L) return 0L
    var a = Random.nextLong(1, p)
    while (modPow((a*a-n+p)%p,(p-1)/2, p)!=p-1)
        a = Random.nextLong(1, p)
    return Complex(a, 1, p, (a*a-n+p)%p).pow((1+p)/2).real
}

fun solve() {
    val (p, a0, a1) = nextLongs()
    if (p==2L) {
        if (a0 == 1L && a1 == 0L) println("1 1")
        else if (a0 == 0L) println("0 $a1")
        else println(-1)
        return
    }
    val n = (a1*a1 + (p-4)*a0) % p
    if (modPow(n,(p-1)/2,p)==p-1) {
        println(-1)
        return
    }
    val a2 = modSqrt(n, p)
    val inv2 = modInv(2, p)
    val b = listOf((a1+a2)*inv2%p, (a1-a2+p)*inv2%p)
    // println("a: $a n: $n w2: ${(a*a-n+p)%p} a0: $a0 a1: $a1 a2: $a2")
    println("${b.min()} ${b.max()}")
}

fun main() {
    (1..nextInt()).forEach{solve()}
}
