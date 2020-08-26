import java.io.File
import java.util.TreeMap
import kotlin.system.exitProcess

private fun nextLine() = readLine()!!
private fun nextToks() = nextLine().split(" ").filter{it != ""}
private fun nextInts() = nextToks().map{it.toInt()}

fun verdict(v: Boolean, msg: String = "Should be EOF") {
    if (!v) throw Exception(msg)
}

fun verify(input: List<String>, ans: List<String>) {
    if (ans[0] == "-1") {
        val x = nextInts()
        verdict(x.size == 1 && x[0] == -1, "Should be -1")
        verdict(readLine() == null, "Should be EOF")
        return
    }
    val (_, _) = input[0].split(" ").filter{it != ""}.map{it.toInt()}
    val a = input[1].split(" ").filter{it != ""}.map{it.toInt()}
    var pos = TreeMap<Int,Int>()
    a.forEachIndexed{i, x -> pos[i+1] = x}
    while (pos.size > 0) {
        val (p, q) = nextInts()
        val nextP = pos.higherKey(p) ?: pos.firstKey()
        val nextQ = pos.higherKey(q) ?: pos.firstKey()
        verdict(nextP == q || nextQ == p, "Not adjacent")
        verdict(pos[p]!=pos[q], "Same value")
        // println("remove pos[$p]=${pos[p]} and pos[$q]=${pos[q]}")
        pos.remove(p)
        pos.remove(q)
    }
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
