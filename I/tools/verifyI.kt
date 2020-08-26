import java.io.File
import java.math.BigInteger
import kotlin.system.exitProcess

private fun nextLine() = readLine()!!
private fun nextToks() = nextLine().split(" ").filter{it.length > 0 && it.length <= 30}
private fun nextInt() = nextLine().toInt()
private fun nextInts() = nextToks().map{it.toInt()}
private fun nextLongs() = nextToks().map{it.toLong()}
private fun nextBigs() = nextToks().map{it.toBigInteger()}

fun verdict(v: Boolean, msg: String = "Should be EOF") {
    if (!v) throw Exception(msg)
}

fun verify(input: List<String>, ans: List<String>) {
    val n = input[0].toInt()
    var out: MutableSet<String> = mutableSetOf()
    var r = nextBigs()[0]
    for (rnd in 1..n) {
        var cand = nextBigs()
        verdict(cand.size == 2, "Not two numbers: $cand")
        val (x, y) = cand
        verdict(x*x + y*y == r*r, "Wrong distance")
        verdict(!out.contains("$x $y"), "Repeated: $x $y")
        out.add("$x $y")
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
