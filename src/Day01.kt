fun main() {

    val MAPPING = mapOf(
        "one" to "on1e",
        "two" to "tw2o",
        "three" to "thr3ee",
        "four" to "fo4ur",
        "five" to "fi5ve",
        "six" to "si6x",
        "seven" to "se7en",
        "eight" to "ei8ht",
        "nine" to "ni9ne"
    )

    fun calibrationData(input: String): Int {
        val first = input.first { it.isDigit() }
        val last = input.last { it.isDigit() }

        return "$first$last".toInt()
    }

    fun replaceTextualNumbers(input: String): String {
        var out = input
        for (e in MAPPING.entries) {
            out = out.replace(e.key, e.value)
        }

        return out
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { calibrationData(it) }
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { calibrationData(replaceTextualNumbers(it)) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = listOf("1abc2",
            "pqr3stu8vwx",
            "a1b2c3d4e5f",
            "treb7uchet")
    val testInputPart2 = listOf("two1nine",
            "eightwothree",
            "abcone2threexyz",
            "xtwone3four",
            "4nineeightseven2",
            "zoneight234",
            "7pqrstsixteen")

    check(part1(testInput) == 142)
    check(part2(testInputPart2) == 281)

    val input = readInput("day01")
    part1(input).println()
    part2(input).println()
}
