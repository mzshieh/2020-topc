const val MAXN = 100000

class Seg(var n: Int, var ans: Int, var lv: Int, var ln: Int, var rv: Int, var rn: Int)

val a = IntArray(MAXN) {0}
val seg = Array<Seg>(MAXN*4) {Seg(0,0,0,0,0,0)}

fun merge(l: Seg, r: Seg): Seg {
    val ret = Seg(
        l.n+r.n,
        maxOf(l.ans, r.ans),
        l.lv, l.ln,
        r.rv, r.rn
    )
    if (l.rv>0 || r.lv<0) {
        ret.ans = maxOf(ret.ans, l.rn+r.ln)
        if (l.n==l.ln) ret.ln += r.ln
        if (r.n==r.rn) ret.rn += l.rn
    }
    return ret
}

fun build(L: Int, R: Int, x: Int) {
    if (L==R) {
        seg[x] = Seg(1,1,a[L],1,a[L],1)
        return
    }
    val M = (L+R)/2
    build(L, M, x*2)
    build(M+1, R, x*2+1)
    seg[x] = merge(seg[x*2], seg[x*2+1])
}

fun update(p: Int, v: Int, L: Int, R: Int, x: Int) {
    if (L==R) {
        seg[x].lv += v
        seg[x].rv += v
        return
    }
    val M = (L+R)/2
    if (p<=M) update(p, v, L, M, x*2)
    if (p>M)  update(p, v, M+1, R, x*2+1)
    seg[x] = merge(seg[x*2], seg[x*2+1])
}

fun query(l: Int, r: Int, L: Int, R: Int, x: Int): Seg {
    if (l<=L&&R<=r) return seg[x]
    val M = (L+R)/2
    if (r<=M) return query(l, r, L, M, x*2)
    if (l>M)  return query(l, r, M+1, R, x*2+1)
    return merge(
        query(l, r, L, M, x*2),
        query(l, r, M+1, R, x*2+1)
    )
}

fun main() {
    var t = readLine()!!.toInt()
    while (t-- !=0) {
        val n = readLine()!!.toInt()
        val org = readLine()!!.split(" ").map{it.toInt()}.toIntArray()
        var last = 0
        for (i in 0 until n) {
            a[i] = org[i] - last
            last = org[i]
        }
        build(1, n-1, 1)
        var q = readLine()!!.toInt()
        val anss = ArrayList<Int>()
        while (q-- !=0) {
            val (op, x, y) = readLine()!!.split(" ").map{it.toInt()}
            if (op==1) {
                if (x!=0)   update(x, y, 1, n-1, 1)
                if (x!=n-1) update(x+1, -y, 1, n-1, 1)
            } else {
                anss.add( query(x+1, y, 1, n-1, 1).ans+1 )
            }
        }
        println(anss.joinToString("\n"))
    }
}
