private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextToks() = nextLine().split(" ")
private fun nextInts() = nextToks().map{it.toInt()}

fun main() {
    val (n,k) = nextInts()
    val a = nextInts().map{it-1}
    var cnt = IntArray(k)
    a.forEach{cnt[it] += 1}
    if (n/2 < cnt.max()?:0) {
        println(-1)
        return
    }
    var used = BooleanArray(n)
    for (rnd in 1..(n/2)) {
        val m = cnt.max()!!
        val t = cnt.indexOf(m)
        val pos = a.mapIndexed{i, x -> if(x==t) i else -1}
                   .filter{it>=0 && !used[it]}
        for (p in pos) {
            var q = (p+1) % n
            while (used[q]) q = (q+1) % n
            if (a[q] != a[p]) {
                used[q] = true
                used[p] = true
                cnt[t] -= 1
                cnt[a[q]] -= 1
                println("${p+1} ${q+1}")
                break
            }
        }
    }
}
