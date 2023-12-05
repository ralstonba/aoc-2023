fun main() {

    data class Card(val number: Int, val mine: Set<Int>, val winning: Set<Int>) {
        fun points(): Int {
            return (1 shl (numberOfWinningNumbers() - 1)).coerceAtLeast(0)
        }
        
        fun numberOfWinningNumbers(): Int {
            return mine.filter { winning.contains(it) }.size
        }
    }

    fun parse(input: String): Card {
        val (card: String, have: String, winning: String) = input.split(":", "|")
        val cardNumber = "\\d+".toRegex().find(card)?.value?.toInt() ?: 0
        val myNumbers = have.split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()
        val winningNums = winning.split(" ").filter { it.isNotBlank() }.map { it.toInt() }.toSet()
        return Card(cardNumber, myNumbers, winningNums)
    }

    fun part1(input: List<String>): Int {
        return input.sumOf { parse(it).points() }
    }

    fun part2(input: List<String>): Int {
        val cards = input.map { parse(it) }
            .associate { it.number to mutableListOf(it) }
            .toMutableMap()

        for (card in cards.keys) {
            val numWins = cards[card]?.first()?.numberOfWinningNumbers() ?: 0
            if (numWins > 0) {
                for (j in 1..cards[card]?.size!!) {
                    for (i in 1..numWins) {
                        cards[card + i]?.add(cards[card]!!.first())
                    }
                }
            }
        }

        return cards.values.flatten().count()

//        for (idx in cards.indices) {
//            if (cards[idx].numberOfWinningNumbers() > 0) {
//                val cardsToAdd = mutableListOf<Card>()
//                for (cnt in 1..cards[idx].numberOfWinningNumbers()) {
//                    cardsToAdd.add(cards[idx + cnt])
//                }
//                cardsToAdd.forEachIndexed { index, card -> cards.add(idx + index + 1, card) }
//            }
//        }
//
//        return cards.count { it.numberOfWinningNumbers() > 0 }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = listOf(
        "Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53",
        "Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19",
        "Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1",
        "Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83",
        "Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36",
        "Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11"
    )

    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("day04")
    part1(input).println()
    part2(input).println()
}
