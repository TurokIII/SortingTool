package sorting
import java.io.File
import java.util.Scanner
import kotlin.math.roundToInt

val scan = Scanner(System.`in`)
const val numberType = "long"
const val wordType = "word"
val validArgs = arrayOf("-sortingType", "-dataType")

data class IntPair(val num: Int, var count: Int)
data class StringPair(val text: String, var count: Int)

fun main(args: Array<String>) {

    var dataType = "word"
    var sortingType = "natural"
    var inputFile: String = ""
    var outputFile: String = ""

    val validInput = isInputValid(args)

    if (validInput) {
        for (i in args.indices) {
            if (args[i] == "-sortingType") sortingType = args[i+1]
            if (args[i] == "-dataType") dataType = args[i+1]
            if (args[i] == "-inputFile") inputFile = args[i+1]
            if (args[i] == "-outputFile") outputFile = args[i+1]
        }

        val values = readInput(dataType)
        executeSort(sortingType, dataType, values)
    }
}

fun isInputValid(args: Array<String>): Boolean {
    for (i in args.indices) {
        if (args[i] == "-sortingType") {
            if (args.getOrNull(i+1) == null) {
                println("No sorting type defined!")
                return false
            }
        }
        if (args[i] == "-dataType") {
            if (args.getOrNull(i+1) == null) {
                println("No data type defined!")
                return false
            }
        }
        if (args[i].contains("-") && args[i] !in validArgs) {
            println("${args[i]} is not a valid parameter. It will be skipped.")
        }
    }
    return true
}

fun executeSort(sortingType: String, dataType: String, values: MutableList<String>) {
    when (sortingType) {
        "natural" -> sortByNatural(dataType, values)
        "byCount" -> sortByCount(dataType, values)
    }
}

fun sortByNatural(dataType: String, values: MutableList<String>) {
    printTotal(values.size, dataType)
    if (dataType == "long") values.sortBy { it.toInt() } else values.sort()
    printNatural(values, dataType)
}

fun sortByCount(dataType: String, values: MutableList<String>) {
    val countPairs = mutableListOf<StringPair>()

    for (uniqueValue in values.toSet()) {
        val count = values.count { it == uniqueValue }
        countPairs.add(StringPair(uniqueValue, count))
    }

    val sortedPairs = if (dataType == "long") {
        countPairs.sortedWith(compareBy({ it.count }, { it.text.toInt()}))
    } else {
        countPairs.sortedWith(compareBy({ it.count }, { it.text }))
    }

    val total = sortedPairs.sumOf { pair -> pair.count}
    printTotal(total, dataType)
    printOccurrences(sortedPairs, total)
}

fun readInput(dataType: String): MutableList<String> {
    val values = mutableListOf<String>()

    while (scan.hasNext()) {
        val value = if (dataType == "line") scan.nextLine() else scan.next()
        if (value == "exit") break
        if (dataType == "long" && !isNumber(value)) {
            println("\"$value\" is not a long. It will be skipped.")
            continue
        }
        values.add(value)
    }

    return values
}

fun isNumber(value: String): Boolean {
    val regex = Regex("""-?\d+""")
    return value.matches(regex)
}

fun executeProcessing(dataType: String) {
    when (dataType) {
        "long" -> processNumbers(scan)
        "line" -> processLines(scan)
        "word" -> processWords(scan)
    }
}

fun processNumbers(scan: Scanner) {
    var max = 0
    var count = 0
    var maxCount = 0

    while (scan.hasNextInt()) {
        val num = scan.nextInt()
        if (num == 999) break

        if (num > max) {
            max = num
            maxCount = 1
        } else if (num == max) maxCount++

        count++
    }

    val occurRate = maxCount * 1.0 / count
    printNumberInfo(count, max, maxCount, occurRate)
}

fun processLines(scan :Scanner) {
    var longest = ""
    var count = 0
    var longestCount = 0

    while (scan.hasNextLine()) {
        val line = scan.nextLine()
        if (line == "exit") break

        if (line.length > longest.length) {
            longest = line
            longestCount = 1
        }

        count++
    }

    val occurRate = longestCount * 1.0 / count
    printLineInfo(count, longest, longestCount, occurRate)
}

fun processWords(scan: Scanner) {
    var longest = ""
    var count = 0
    var longestCount = 0

    while (scan.hasNext()) {
        val word = scan.next()
        if (word == "exit") break

        if (word.length > longest.length) {
            longest = word
            longestCount = 1
        }

        count++
    }

    val occurRate = longestCount * 1.0 / count
    printWordInfo(count, longest, longestCount, occurRate)
}

fun printTotal(total: Int, dataType: String) {
    val dataTypeText = when (dataType) {
        numberType -> "numbers"
        wordType -> "words"
        else -> "lines"
    }
    println("Total $dataTypeText: $total.")
}

fun printNatural(values : MutableList<String>,dataType: String) {
    println("Sorted data: ${values.joinToString(" ")}")
}

fun printOccurrences(sortedPairs: List<StringPair>, total: Int) {
    for (pair in sortedPairs) {
        val occurRate = (pair.count * 1.0 / total * 100).roundToInt()
        println("${pair.text}: ${pair.count} time(s), ${occurRate}%")
    }
}

fun printNumberInfo(total: Int, max: Int, count: Int, occurRate: Double) {
    println("Total numbers: $total.")
    println("The greatest number: $max ($count time(s), ${(occurRate * 100).toInt()}%).")
}

fun printLineInfo(total: Int, longest: String, count: Int, occurRate: Double) {
    println("Total lines: $total.")
    println("The longest line:\n$longest\n($count time(s), ${(occurRate * 100).toInt()}%).")
}

fun printWordInfo(total: Int, longest: String, count: Int, occurRate: Double) {
    println("Total words: $total.")
    println("The longest word: $longest ($count time(s), ${(occurRate * 100).toInt()}%).")
}