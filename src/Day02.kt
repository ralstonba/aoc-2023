fun main() {

    data class Round(val red: Int, val green: Int, val blue: Int)
    data class Game(val number: Int, val rounds: List<Round>)

    fun parseInput(input: String): Game {
        val number = "\\d+".toRegex().find(input)!!.value.toInt()
        val game = input.split(":").last().trim()
        val rounds = game.split("; ").map {
            val colors = it.split(", ")
            var red = 0
            var green = 0
            var blue = 0
            for (color in colors) {
                if (color.contains("red")) {
                    red = "\\d+".toRegex().find(color)!!.value.toInt()
                } else if (color.contains("green")) {
                    green = "\\d+".toRegex().find(color)!!.value.toInt()
                } else {
                    blue = "\\d+".toRegex().find(color)!!.value.toInt()
                }
            }
            Round(red, green, blue)
        }
        return Game(number, rounds)
    }

    fun part1(input: List<String>): Int {
        return input.map { parseInput(it) }
            .filter {
                it.rounds.none { r ->
                    r.red > 12 || r.green > 13 || r.blue > 14
                }
            }.sumOf { it.number }
    }

    fun part2(input: List<String>): Int {
        return input.map { parseInput(it) }
            .map { game ->
                var r = 0
                var g = 0
                var b = 0
                for (round in game.rounds) {
                    r = r.coerceAtLeast(round.red)
                    g = g.coerceAtLeast(round.green)
                    b = b.coerceAtLeast(round.blue)
                }
                Round(r, g, b)
            }.sumOf { it.red * it.green * it.blue }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = listOf(
        "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
        "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
        "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
        "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
        "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green"
    )

    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("day02")
    part1(input).println()
    part2(input).println()
}
