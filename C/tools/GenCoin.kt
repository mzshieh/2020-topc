import kotlin.random.Random
private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextToks() = nextLine().split(" ").filter{it!=""}
private fun nextInts() = nextToks().map{it.toInt()}

fun solve(coin: ArrayList<Int>): Int {
    var dp = ArrayList<Int>(listOf(0))
    var g = ArrayList<Int>(listOf(0))
    for(i in 1..100000) {
        dp.add( coin.filter{it <= i}.map{dp[i-it]+1}.min() ?: 0 )
        g.add( g[i-coin.filter{it <= i}.max()!!]+1 )
        if (dp[i] != g[i]) return i
    }
    return -1
}

fun main() {
    val n = nextInt()
    var coin = ArrayList<Int>(nextInts())
    while (coin.size < n) {
        var x = solve(coin)
        if (x==-1) x = coin.last() * 2 + Random.nextInt(coin.last() * 10)
        coin.add(x)
    }
    println(n)
    println(coin.joinToString(" "))
}
