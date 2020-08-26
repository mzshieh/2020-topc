private fun nextLine() = readLine()!!
private fun nextToks() = nextLine().split(" ")
private fun nextInt() = nextLine().toInt()
private fun nextInts() = nextToks().map{it.toInt()}

data class Seg(val size: Int = 0, var b: Int = 1, var e: Int = 1, 
               val ans: Int = 1, val ba: Int = 1, val ea: Int = 1) {
    init {
        if (b==0 || e==0) throw Exception("GOD")
    }
}

infix fun Seg.concat(x: Seg): Seg {
    val sz = size + x.size
    val ok = e > 0 || x.b < 0
    var newBA = if (ba == size && ok) ba+x.ba else ba
    var newEA = if (x.ea == x.size && ok) ea+x.ea else x.ea
    val best = maxOf(ans, x.ans, if(ok) ea+x.ba else 0)
    return Seg(sz, b, x.e, best, newBA, newEA)
}

const val H = 18
var t = Array<ArrayList<Seg>>(H){ArrayList<Seg>()}

fun update(pos: Int, d: Int) {
    if (pos < 0 || pos >= t[0].size) return
    t[0][pos].b += d
    t[0][pos].e += d
    for (h in 1 until H) {
        val q = pos shr h
        if (q >= t[h].size) break
        t[h][q] = t[h-1][2*q] concat t[h-1][2*q+1]
    }
}

fun query(h: Int, L: Int, R: Int): Seg {
    if (L==R-1) return t[0][L]
    val ls = L.shr(h-1)
    val rs = (R-1).shr(h-1)
    if (ls==rs) return query(h-1, L, R)
    if (R==L+1.shl(h)) return t[h][L.shr(h)]
    val M = rs.shl(h-1)
    return query(h-1, L, M) concat query(h-1, M, R)
}

fun query(L: Int, R: Int): Int {
    if (L==R) return 1
    if (L+1==R) return 2
    return query(H-1, L, R).ans + 1
}

fun solve() {
    val n = nextInt()
    var seq = nextInts()
    var buf = ArrayList<String>()
    for (x in t) x.clear()
    for (i in 1 until n) 
        t[0].add(Seg(1, seq[i]-seq[i-1], seq[i]-seq[i-1]))
    for (i in 1 until H)
        for (j in 0 until t[i-1].size-1 step 2)
            t[i].add(t[i-1][j] concat t[i-1][j+1])
    val q = nextInt()
    for (i in 1..q) {
        val (op, L, R) = nextInts()
        if (op == 1) {
            update(L-1,R)
            update(L,-R)
        }
        else buf.add("${query(L,R)}")
    }
    println(buf.joinToString("\n"))
}

fun main() = (1..nextInt()).forEach{solve()}
