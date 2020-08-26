private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextToks() = nextLine().split(" ").filter{it!=""}
private fun nextInts() = nextToks().map{it.toInt()}

fun main() {
    var dp = ArrayList<Int>(listOf(0))
    var g = ArrayList<Int>(listOf(0))
    val n = nextInt()
    val coin = nextInts()
    if (n != coin.size) throw Exception("input incorrect")
    for(i in 1..100000) {
        dp.add( coin.filter{it <= i}.map{dp[i-it]+1}.min() ?: 0 )
        g.add( g[i-coin.filter{it <= i}.max()!!]+1 )
        if (dp[i] != g[i]) {
            println(i)
            return
        }
    }
    println(-1)
}
