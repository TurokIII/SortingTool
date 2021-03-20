package sorting
import java.util.Scanner

val scan = Scanner(System.`in`)

fun main(args: Array<String>) {

    var operation = "-dataType"
    var dataType = "word"

    if (args.contains("-sortIntegers")) operation = "-sortIntegers"
    if (args.size == 2) dataType = args[1]
    
    executeOperation(operation, dataType)
}

fun executeOperation(operation: String, dataType: String) {
    when (operation) {
        "-sortIntegers" -> sortIntegers()
        "-dataType" -> executeProcessing(dataType)
    }
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

