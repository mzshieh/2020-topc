fun main() {
    val n = readLine()!!.toInt()
    // val r = 77068225L // capable for n=2700
    val r = 5928325L
    val r2 = r*r
    var squares = ArrayList<Long>()
    var output = HashSet<Pair<Long,Long>>()
    for (i in 0L..r) {
        val q = i*i
        val p = r2 - q
        squares.add(q)
        val j = squares.binarySearch(p).toLong()
        if (j >= 0) {
            output.add(Pair(i, j))
            output.add(Pair(j, i))
            output.add(Pair(-i, j))
            output.add(Pair(-j, i))
            output.add(Pair(i, -j))
            output.add(Pair(j, -i))
            output.add(Pair(-i, -j))
            output.add(Pair(-j, -i))
        }
        if (output.size >= n) break
    }
    println(r)
    output.take(n).forEach{println("${it.first} ${it.second}")}
}
