fun main() {

    fun getWord(
        input: List<String>,
        wordLength: Int,
        preCondition: () -> Boolean,
        x: Int,
        y: Int,
        directionX: Int,
        directionY: Int
    ): String? {
        if (!preCondition.invoke())
            return null
        var w = ""
        for (i in 0..<wordLength)
            w += input[x + i * directionX][y + i * directionY]
        return w;
    }

    fun diagonalRightDown(input: List<String>, wordLength: Int, x: Int, y: Int): String? {
        return getWord(
            input,
            wordLength,
            fun() = x <= input[y].length - wordLength && y <= input.size - wordLength,
            x,
            y,
            1,
            1
        )
    }

    fun diagonalLeftDown(input: List<String>, wordLength: Int, x: Int, y: Int): String? {
        return getWord(input, wordLength, fun() = x >= wordLength - 1 && y <= input.size - wordLength, x, y, -1, 1)
    }

    fun horizontal(input: List<String>, wordLength: Int, x: Int, y: Int): String? {
        return getWord(input, wordLength, fun() = x <= input[y].length - wordLength, x, y, 1, 0)
    }

    fun vertical(input: List<String>, wordLength: Int, x: Int, y: Int): String? {
        return getWord(input, wordLength, fun() = y <= input.size - wordLength, x, y, 0, 1)
    }

    fun checkWord(searchedWord: String, w: String?): Boolean = w != null && (w == searchedWord || w.reversed() == searchedWord)

    fun part1(input: List<String>): Int {
        val searchedWord = "XMAS"
        val wordLength = searchedWord.length
        var sum = 0
        for (y in 0..input.size - 1) {
            for (x in 0..input.size - 1) {
                sum += listOf(
                    diagonalLeftDown(input, wordLength, x, y),
                    diagonalRightDown(input, wordLength, x, y),
                    horizontal(input, wordLength, x, y),
                    vertical(input, wordLength, x, y)
                ).count { checkWord(searchedWord, it) }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Int {
        val searchedWord = "MAS"
        val wordLength = searchedWord.length
        var sum = 0
        for (y in 0..input.size - 3) {
            for (x in 0..input.size - 3) {
                val count = listOf(
                    diagonalLeftDown(input, wordLength, x + 2, y),
                    diagonalRightDown(input, wordLength, x, y),
                ).count { checkWord(searchedWord, it) }
                if (count == 2) sum++
            }
        }
        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
