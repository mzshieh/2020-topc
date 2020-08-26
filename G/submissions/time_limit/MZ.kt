private fun nextLine() = readLine()!!
private fun nextInt() = nextLine().toInt()
private fun nextToks() = nextLine().split(" ")
private fun nextInts() = nextToks().map{it.toInt()}

fun dfs(u: Int, v: Int, adj: ArrayList<IntArray>, 
        ans: Array<ArrayList<Int>>, visited: BooleanArray): Int {
    var ret = 1
    visited[v] = true
    val cand = adj[v].filter{!visited[it] && u in adj[it] && it !in ans[u]}
    if (cand.size>2) return -1
    ans[v].add(u)
    ans[v].addAll(cand)
    ans[v].sort()
    for (w in cand) 
        ret += dfs(v, w, adj, ans, visited)
    return ret
}

fun solve() {
    val n = nextInt()
    var adj = ArrayList<IntArray>()
    var leaf = -1
    var cap = 3
    for (v in 1..n) {
        adj.add(nextInts().drop(1).map{it-1}.toIntArray())
        if (adj.last().size <= cap) {
            leaf = v-1
            cap = adj.last().size
        }
    }
    for (v in adj[leaf]) {
        var ans = Array<ArrayList<Int>>(n){ArrayList<Int>()}
        var visited = BooleanArray(n){it==leaf}
        ans[leaf].add(v)
        val size = dfs(leaf, v, adj, ans, visited)
        if (size == n-1) {
            for (lst in ans) {
                println("${lst.size} ${lst.map{it+1}.joinToString(" ")}")
            }
            break
        }
    }
}

fun main() {
    (1..nextInt()).forEach{solve()}
}
