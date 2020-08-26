import java.util.HashMap

fun main() {
    val n = 10000
    var cnt = HashMap<Int,Int>()
    for (i in 1..n) {
        for (j in 1..n) {
            val s = i*i + j*j
            val x = cnt[s]?:0
            cnt[s] = x+1
        }
    }
    println(cnt.maxBy{it.value})
}
