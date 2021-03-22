package sorting
import java.lang.reflect.Type
import java.util.Scanner
import kotlin.math.roundToInt

val scan = Scanner(System.`in`)

data class IntPair(val num: Int, var count: Int)
data class StringPair(val text: String, var count: Int)

fun main(args: Array<String>) {

    var dataType = "word"
    var sortingType = "natural"

    for (i in args.indices) {
        if (args[i] == "-sortingType") sortingType = args[i+1]
        if (args[i] == "-dataType") dataType = args[i+1]
    }

    executeSort(sortingType, dataType)
}

fun executeSort(sortingType: String, dataType: String) {
    when (sortingType) {
        "natural" -> {
            when (dataType) {
                "long" -> {
                    val nums = readInts()
                    printTotal(nums.size, "numbers")
                    println("Sorted data: ${nums.sorted().joinToString(" ")}")
                }
                "word" -> {
                    val words = readWords()
                    printTotal(words.size, "words")
                    println("Sorted data: ${words.sorted().joinToString(" ")}")
                }
                "line" -> {
                    val lines = readLines()
                    printTotal(lines.size, "lines")
                    println("Sorted data: ${lines.sorted().joinToString(" ")}")
                }
            }
        }
        "byCount" -> {
            when (dataType) {
                "long" -> {
                    val sortedPairs = sortCountInt()
                    val total = sortedPairs.sumOf { it.count }
                    printTotal(total, "numbers")
                    printOccurrences(sortedPairs.map { StringPair(it.num.toString(), it.count) }, total)
                }
                "line" -> {
                    val sortedPairs = sortCountString(dataType)
                    val total = sortedPairs.sumOf { it.count }
                    printTotal(total, "numbers")
                    printOccurrences(sortedPairs, total)
                }
                "word" -> {
                    val sortedPairs = sortCountString(dataType)
                    val total = sortedPairs.sumOf { it.count }
                    printTotal(total, "numbers")
                    printOccurrences(sortedPairs, total)
                }
            }

        }
    }
}

fun sortCountInt(): List<IntPair> {
    val numbers = readInts()

    val countPairs = mutableListOf<IntPair>()

    for (uniqueInt in numbers.toSet()) {
        val count = numbers.count { it == uniqueInt}
        countPairs.add(IntPair(uniqueInt, count))
    }

    return countPairs.sortedWith(compareBy({ it.count }, { it.num }))
}

fun sortCountString(dataType: String): List<StringPair> {
    val strings = if (dataType == "line") readLines() else readWords()

    val countPairs = mutableListOf<StringPair>()

    for (uniqueString in strings.toSet()) {
        val count = strings.count { it == uniqueString}
        countPairs.add(StringPair(uniqueString, count))
    }

    return countPairs.sortedWith(compareBy({ it.count }, { it.text }))
}

fun readInts(): MutableList<Int> {
    val numbers = mutableListOf<Int>()

    while (scan.hasNextInt()) {
        numbers.add(scan.nextInt())
    }

    return numbers
}

fun readLines(): MutableList<String> {
    val lines = mutableListOf<String>()

    while (scan.hasNextLine()) {
        lines.add(scan.nextLine())
    }

    return lines
}

fun readWords(): MutableList<String> {
    val words = mutableListOf<String>()

    while (scan.hasNext()) {
        words.add(scan.next())
    }

    return words
}

fun executeProcessing(dataType: String) {
    when (dataType) {
        "long" -> processNumbers(scan)
        "line" -> processLines(scan)
        "word" -> processWords(scan)
    }
}

fun sortIntegers() {
    val numbers = mutableListOf<Int>()

    while (scan.hasNextInt()) {
        val num = scan.nextInt()
        numbers.add(num)
    }

    println("Total numbers: ${numbers.size}.")
    println("Sorted data: ${numbers.sorted().joinToString(" ")}")
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
    println("Total $dataType: $total.")
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

