fun main() {

    fun parseLine(input: String): List<String> {
        return input.chunked(1)
    }

    data class Coordinate(val x: Int, val y: Int)
    data class PartNumber(val number: Int, val location: Pair<Coordinate, Coordinate>)

    fun part1(input: List<String>): Int {
        val struct = input.map { parseLine(it) }
            .mapIndexed { index, characters ->
                val parts = mutableListOf<PartNumber>()
                var left = 0
                var right = 0
                while (right < characters.size || left < characters.size) {
                    if (characters[left].none { c -> c.isDigit() }) {
                        // Slide forward because we are not looking at a digit
                        left++; right++
                        continue
                    }

                    val scanned = characters.subList(left, right).joinToString("")
                    if (scanned.all { c -> c.isDigit() }) {
                        // if everything we have is a digit, keep expanding
                        right++
                    } else {
                        // right side reached non-digit
                        val number = characters.subList(left, right - 1).joinToString("").toInt()
                        val start = Coordinate(left, index)
                        val end = Coordinate(right - 2, index)
                        parts.add(PartNumber(number, Pair(start, end)))
                        left = right
                    }
                }
                parts
            }
        return struct.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = listOf(
        "467..114..",
        "...*......",
        "..35..633.",
        "......#...",
        "617*......",
        ".....+.58.",
        "..592.....",
        "......755.",
        "...$.*....",
        ".664.598.."
    )

    check(part1(testInput) == 4361)
//    check(part2(testInput) == 2286)

    val input = readInput("day03")
    part1(input).println()
    part2(input).println()
}
