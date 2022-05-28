fun positiveNum(l: List<Number>): List<Number> {
    var new_list: MutableList<Number> = mutableListOf()
    for (i in 0 until l.size) {
        if (i % 2 == 1) {
            if (l[i].toDouble() > 0.0) new_list.add(l[i].toDouble() * l[i].toDouble())
        }
    }
    return new_list
}

fun sortByFChar(l: List<String>): List<List<String>> {
    val sorted = l.filter { it.length % 2 == 0 }.sortedBy { it.first() }
    val grouped = sorted.groupBy { it.first() }
    val first_letters = mutableListOf<Char>()
    l.sortedBy { it.first() }.forEach {
        if (!first_letters.contains(it.first())) first_letters.add(it.first())
    }
    var return_list = mutableListOf<List<String>>()

    for (i in 0 until first_letters.size) {
        return_list.add(listOf(first_letters[i].toString(), grouped[first_letters[i]].toString()))
    }

    return return_list
}

fun permutations(vararg args: Int): List<List<Int>> {
    var ins = args.toList()
    if (args.size == 0) return listOf();
    else if (ins.size == 1) return listOf(args.toList())
    
    var perms = mutableListOf<MutableList<Int>>()
    val toInsert = ins[0]
    
    for (perm in permutations(*ins.drop(1).toIntArray())) {
        for (i in 0..perm.size) {
            val new_p = perm.toMutableList()
            //println("toInsert: ${toInsert}  new_p: ${new_p}  i: $i")
            new_p.add(i, toInsert)      // wpisuje toInsert w rozne id co daje rozne permutacje
            //println("toInsert: ${toInsert}  new_p: ${new_p}  i: $i")
            //println("perms: ${perms}")
            perms.add(new_p)
        }
    }
    return perms
}

fun check(N: Int, list: List<Int>): Int {
    var nextToPream : Int;
    for (i in N..list.size-1) {
        nextToPream = list[i]
        var tmp_l = list.slice((i-N)..(i-1))
        var tmp_diff = tmp_l.map { nextToPream - it }
        if (!tmp_l.any{ it in tmp_diff}) return nextToPream
    }
    return list.last();
}

fun main() {
    println("Zadanie 1: ")
    println("1.0, 2.0, 3, -12.0, -1, 0.0, 0.0, -3.0")
    println(positiveNum(listOf(1.0, 2.0, 3, -12.0, -1, 0.0, 0.0, -3.0)))

    println("\nZadanie 2: ")
    println("cherry, blueberry, citrus, apple, apricot, banana, coconut")
    println(sortByFChar(
                    listOf("cherry", "blueberry", "citrus", "apple", "apricot", "banana", "coconut")
            )
    ) // a [], b [banana], c [cherry, citrus]

    println("\nZadanie 3: ")
    println("1, 2, 3")
    println(permutations(1, 2, 3))

    println("\nZadanie 4: ")
    println(check(5, listOf(35, 25, 15, 25, 47, 40, 62, 55, 65, 95, 102, 117, 150, 182, 127, 219, 299, 277, 309, 576))) // output 127

}
