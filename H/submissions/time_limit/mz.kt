import java.util.TreeSet
import kotlin.math.max

private fun nextLine() = readLine()!!
private fun nextToks() = nextLine().split(" ")
private fun nextInt() = nextLine().toInt()
private fun nextInts() = nextToks().map{it.toInt()}


private fun isValley(h: List<Int>, i: Int) = i > 0 && i + 1 < h.size && h[i-1] > h[i] && h[i] < h[i+1]

private fun solve() {
    val n = nextInt()
    var h = nextInts().toMutableList()
    val q = nextInt()
    var valleys = TreeSet<Int>()
    var buf = StringBuilder()
    for (i in 1 until n-1)
        if (isValley(h,i))
            valleys.add(i)
    for (i in 1..q) {
        val (op, L, R) = nextInts()
        if (op == 1) {
            h[L] += R
            for (j in L-1 .. L+1)
                if (isValley(h,j))
                    valleys.add(j)
                else
                    valleys.remove(j)
        }
        else if(R<(valleys.ceiling(L)?:R+1)) { // no valleys in [L, R]
            //println("${R-L+1}")
            buf.append("${R-L+1}\n")
        }
        else {
            var ans = 1
            ans = max(ans, (valleys.ceiling(L)?: R) - L + 1)
            ans = max(ans, R - (valleys.floor(R)?: L) + 1)
            var pos = L
            var nextPos = valleys.higher(pos)?: R+1
            while (R >= nextPos) {
                ans = max(ans, nextPos - pos + 1)
                pos = nextPos
                nextPos = valleys.higher(pos)?: R+1
            }
            //println("$ans")
            buf.append("$ans\n")
        }
    }
    print(buf)
}

fun main() = (1..nextInt()).forEach{solve()}
