fun gray(p: Int): List<Int> {
    if (p == 1) return listOf(0,1)
    val code = gray(p-1)
    return ArrayList<Int>(code).apply{
        addAll(code.map{it+1.shl(p-1)}.reversed())}
    
}

fun main() {
    val code = gray(19)
    for (i in code) println(i)
}
