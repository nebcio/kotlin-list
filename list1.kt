fun missingNumber(tab: IntArray) : Int{
    tab.sort()  
    
    for (i in 0 until tab.size) {
        if (i != tab[i]) {
            return i
        }
    }
    
    return tab.size
}

fun main() {
    // Link do github: https://github.com/nebcio/kotlin-zadania.git

    // 1, 2, 3
    for (i in 1..100) {
        var podzielnosc: String = ""
        if (i%3 == 0) {
            podzielnosc += "trzy"
        }
        if (i%5 == 0) {
            podzielnosc += "piec"
        }
        if (i%7 == 0) {
            podzielnosc += "siedem"
        }
        if (i%11 == 0) {
            podzielnosc += "jedenascie"
        }
        if (i%13 == 0) {
            podzielnosc += "trzynascie"
        }
        if (podzielnosc == "") {
            println(i)
        }
        else {
            println(podzielnosc)
        }
    }

    // 4
    val ar: IntArray = intArrayOf(0, 1, 3)
    println("\n\nW tablicy: ")
    for (i in ar) print("$i ")
    val mN = missingNumber(ar)
    println("\nBrakuje: $mN")

    val ar2: IntArray = intArrayOf(0, 1, 3, 2)
    println("\n\nW tablicy: ")
    for (i in ar2) print("$i ")
    val mN2 = missingNumber(ar2)
    println("\nBrakuje: $mN2")

}