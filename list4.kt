fun fib(i : Int = 0): Int {
    tailrec fun fibonacci(n: Int, a: Int, b: Int): Int {
        if (n == 0) return a;
        else if (n == 1) return b;
        else return fibonacci(n-1, b, a+b);
    }
    return fibonacci(i, 0, 1);
}

fun formatResult(name: String, n: Int, f: (Int) -> Int): String {
    val value = f(n);
    return name + n + " = " + value;
}

val <T> List<T>.tail: List<T> 
    get() = drop(1)

val <T> List<T>.head: T
    get() = this[0]

fun<A> isSorted(aa: List<A>, order: (A, A) -> Boolean): Boolean {
    val aa_s = aa.sortedWith(Comparator<A>{ x, y -> when {
        order(x, y) -> -1
        else -> 1;
    }});

    return aa == aa_s;
}

fun suma(a: Array<Int>): Int {
    // map filter, reduce
    return a.filter { it > 0 }.toIntArray().reduce { suma1, element -> suma1 + element }
}

fun<T> countElements(a: Array<Array<T>>): Map<T, Int>{
    var map = mutableMapOf<T, Int>();
    a.forEach { 
        it.forEach { 
            if (map.containsKey(it)) map[it] = map[it]!!.plus(1)
            else map.set(it, 1)
        }
    }
    return map
}

fun main() {
    // zad 1
    println("\nZadanie 1:")
    val nstring : (String, Int) -> String = { s, i -> s.repeat(i) }
    println(nstring("io ", 5))
    

    // zad 2 
    println("\nZadanie 2:")
    val f : (String) -> String = { it + "!"}
    println(f("aaa")) 
    

    // zad 3
    println("\nZadanie 3:")
    println(fib(10));
    

    // zad 4  
    println("\nZadanie 4:")
    fun log2(n: Int): Int = (Math.log(n.toDouble()) / Math.log(2.0)).toInt()
    println(log2(10))
    

    // zad 5
    println("\nZadanie 5:")
    println(formatResult("Log", 10, ::log2));
    println(formatResult("Fibonacci: element ciągu: ", 10, ::fib));
    

    // zad 6
    println("\nZadanie 6:")
    val lista = listOf('a', 'b', 'c', 'd', 'e')
    println(lista.head)
    println(lista.tail)


    // zad 7
    println("\nZadanie 7:")
    val listb = listOf(1, 2, 3, 4)
    val listc = listOf(1, 1, 1, 1)
    val listd = listOf("ahyyhh", "bkjn", "cnn", "duu")
    println(isSorted(listb, {i: Int, j: Int -> i < j}))
    println(isSorted(listc, {i: Int, j: Int -> i == j}))
    println(isSorted(listd, {i: String, j: String -> i.first() < j.first()}))


    // zad 8
    println("\nZadanie 8:")
    println(suma(arrayOf(1, -4, 12, 0, -3, 29, -150)))
    

    // zad 9
    println("\nZadanie 9:")
    println(countElements(arrayOf(
        arrayOf('a', 'b', 'c'), 
        arrayOf('c', 'd', 'f'), 
        arrayOf('d', 'f', 'g') )))

    println(countElements(arrayOf(
        arrayOf(true, 12, false),
        arrayOf(true, "a", 12),
        arrayOf("a", null, "a")
    )))
}