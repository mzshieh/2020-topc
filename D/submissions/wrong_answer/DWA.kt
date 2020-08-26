private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextToks() = nextLine().split(" ")
private fun nextInts() = nextToks().map{it.toInt()}

fun main() {
    val (n,k) = nextInts()
    val a = nextInts().map{it-1}
    var cnt = IntArray(k)
    a.forEach{cnt[it] += 1}
    if (n/2 <= cnt.max()?:0) {
        println(-1)
        return
    }
    var used = BooleanArray(n)
    for (rnd in 1..(n/2)) {
        println("${2*rnd-1} ${rnd*2}")
    }
}
