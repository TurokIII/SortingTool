package sorting
import java.util.Scanner

fun main() {
    val scan = Scanner(System.`in`)
    val nums = mutableListOf<Int>()

    while (scan.hasNextInt()) {
        val num = scan.nextInt()

        nums.add(num)
    }

    val total = nums.size
    val max = getMax(nums)
    val count = countMax(max, nums)

    printInfo(total, max, count)
}

fun getMax(nums: MutableList<Int>): Int {
    var max = 0

    for (num in nums) {
        if (num > max) max = num
    }

    return max
}

fun countMax(max: Int, nums: MutableList<Int>): Int {
    var count = 0

    for (num in nums) {
        if (num == max) count++
    }

    return count
}

fun printInfo(total: Int, max: Int, count: Int) {
    println("Total numbers: $total.")
    println("The greatest number: $max ($count time(s)).")
}
