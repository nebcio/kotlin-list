
import java.math.BigInteger
fun convertToRoman(n: Int): String {
    if (n < 1 || n > 3999) return "!out from range!";
    var roman = "";
    val romans = mapOf<Int, String>(1000 to "M", 900 to "CM", 500 to "D", 400 to "CD", 100 to "C", 90 to "XC", 50 to "L", 40 to "XL", 10 to "X", 9 to "IX", 5 to "V", 4 to "IV", 1 to "I");

    var num = n;
    for ((m, nr) in romans.entries) {
        while (num >= m) {
            num -= m;       // odejmuje kolejne klucze
            roman += nr;    // dodaje wartosc klucza do zwracanej rzymskiej liczby
        }
    }
    return roman;
}

fun convertFromRoman(s: String): Int {
    var number : Int = 0
    val romans = mapOf<String, Int>("M" to 1000, "D" to 500, "C" to 100, "L" to 50, "X" to 10, "V" to 5, "I" to 1)
    val pairs = listOf(900, 400, 90, 40, 9, 4)

    for(i in 0..s.length) {
        if (i == (s.length-1)) {
            number += romans.get(s[i].toString())!!;
            return number;
        }

        if (romans.get(s[i].toString())!! < romans.get(s[i + 1].toString())!!) { // sprawdza czy aktualna jest mniejsza od nastepnej  

            if (pairs.contains(romans.get(s[i + 1].toString())!! - romans.get(s[i].toString())!!) ) {    // sprawdza czy roznica ich zawiera sie w parach             
                number -= romans.get(s[i].toString())!!
            }
            else {
                println("Wrong roman number!")
                return 0
            }
        }
        else if (s.contains(s[i].toString().repeat(4)) ) {  // sprawdza czy nei wychodzimy poza zakres i czy nie dajemy tego samego przed i po nastepnej 
            println("Wrong roman number!")
            return 0
        }
        else { 
            number += romans.get(s[i].toString())!!;
        }
    }
    return number;
}

fun isCyclic(i: String): Boolean {
    var n = i.length

    if (i.toBigIntegerOrNull() == null) {
        println("That's not a number")
        return false
    }
    val number = i.toBigInteger()                          
    val chars = i.split("").sorted().joinToString("") // jesli zero to tez tu bedzie
    for (j in 1..n) { 
        var tmp: BigInteger = j.toBigInteger() * number
        var nk = tmp.toString().split("").sorted().joinToString("")
        if (nk.length < chars.length) {
            nk = "0" + nk
        }
        if (nk != chars) {
            return false
        }
    }
    return true
}

fun main() {
    println("Zad 1: ")
    println(convertToRoman(2895));
    
    println("\nZad 2: ")
    println(convertToRoman(2909));
    println(convertFromRoman("MMCMIX"));    // 2909
    println(convertFromRoman("MMMCIIII"));

    println("\nZad 3: ")
    println(isCyclic("142857"))
    println(isCyclic("0588235294117647"))
    println(isCyclic("-4320"))
    println(isCyclic("ala"))
}