import java.io.File
import java.util.TreeMap
import java.math.BigDecimal
import java.math.BigInteger
import kotlin.system.exitProcess

private fun nextLine() = readLine()!!
private fun nextToks() = nextLine().split(" ").filter{0 < it.length && it.length <= 100}
private fun nextInts() = nextToks().map{it.toInt()}
private fun nextBigDecs() = nextToks().map{it.toBigDecimal().setScale(256)!!}

fun verdict(v: Boolean, msg: String = "Should be EOF") {
    if (!v) throw Exception(msg)
}

fun dist2(p: List<BigDecimal>, q: List<BigDecimal>): BigDecimal {
    val dx = p[0]-q[0]
    val dy = p[1]-q[1]
    return dx*dx+dy*dy
}

fun verify(input: List<String>, ans: List<String>) {
    if (ans[0] == "-1") {
        val x = nextInts()
        verdict(x.size == 1 && x[0] == -1, "Should be -1")
        verdict(readLine() == null, "Should be EOF")
        return
    }
    val (n, r) = input[0].split(" ").filter{it != ""}.map{it.toInt()}
    val r2 = BigDecimal(r.toLong()*r.toLong()).setScale(256)!!
    val pts = (1..n).map{input[it].split(" ").map{it.toBigDecimal().setScale(256)!!}}.toHashSet()
    val safe = nextBigDecs()
    val explosion = nextBigDecs()
    verdict(safe in pts, "Not a valid point")
    verdict(r2 < dist2(safe,explosion), "Not safe")
    verdict(r2 >= pts.filter{it!=safe}.map{dist2(it,explosion)}.max()?:BigDecimal.ZERO,
            "Some point is not exploded")
    verdict(readLine() == null)
}

// usage: judge_in judge_ans feedback_dir [options] < team_out

fun main(args: Array<String>) {
    try{
        verify(File(args[0]).readLines(), File(args[1]).readLines())
    } catch (e: Exception) {
        println("Wrong Answer")
        println("$e")
        exitProcess(43)
    }
    println("Correct")
    exitProcess(42)
}
