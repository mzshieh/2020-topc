private fun nextLine() = readLine()!!
private fun nextToks() = nextLine().split(" ")
private fun nextInt() = nextLine().toInt()
private fun nextInts() = nextToks().map{it.toInt()}

fun verdict(predicate: Boolean, msg: String = "") {
    if (!predicate) throw Exception(msg)
}

fun width(h: MutableList<Int>, L: Int, R: Int): Int {
    var valleys = ArrayList<Int>()
    valleys.add(L)
    for (i in (L+1) until R)
        if (h[i-1] > h[i] && h[i] < h[i+1]) valleys.add(i)
    valleys.add(R)
	return valleys.mapIndexed{i, x -> if (i>0) x-valleys[i-1]+1 else 1}.max()?:0
}

var cnt = 0

fun solve() {
    val n = nextInt()
    var h = nextInts().toMutableList()
    val q = nextInt()
    for (i in 1..q) {
        val (op, L, R) = nextInts()
        if (op == 1) {
            h[L] += R
            verdict(h[L] >= 1 && h[L] <= 1_000_000_000, "h[$L]: ${h[L]} is out of range")
            if (L>0) verdict(h[L] != h[L-1], "${h[L]}=h[$L]==h[${L-1}]=${h[L-1]}")
            if (L<n-1) verdict(h[L] != h[L+1], "${h[L]}=h[$L]==h[${L+1}]=${h[L+1]}")
        }
        else {
            println("${width(h, L, R)}")
            cnt += 1
            // if (cnt in listOf(417260, 417879, 417992))
            //     println("query($L,$R): $h")
        }
    }
}

fun main() = (1..nextInt()).forEach{solve()}
