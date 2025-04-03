package Sudoko

import kotlin.math.sqrt

fun main() {
    // ✅ Valid Incomplete Sudoku (No conflicts, some empty cells)
    val validSudoku = listOf(
        "53--7----",
        "6--195---",
        "-98----6-",
        "8---6---3",
        "4--8-3--1",
        "7---2---6",
        "-6----28-",
        "---419--5",
        "----8--79"
    )

    // ❌ Row Conflict (Duplicate '5' in row 0)
    val invalidSudokuRow = listOf(
        "553--7----", // '5' appears twice in the first row
        "6--195---",
        "-98----6-",
        "8---6---3",
        "4--8-3--1",
        "7---2---6",
        "-6----28-",
        "---419--5",
        "----8--79"
    )

    // ❌ Column Conflict (Duplicate '5' in column 0)
    val invalidSudokuColumn = listOf(
        "53--7----",
        "6--195---",
        "-98----6-",
        "8---6---3",
        "4--8-3--1",
        "7---2---6",
        "56----28-", // '5' appears twice in column 0
        "---419--5",
        "----8--79"
    )

    // ❌ 3×3 Box Conflict (Duplicate '5' in the same subgrid)
    val invalidSudokuBox = listOf(
        "53--7----",
        "6--195---",
        "-98----6-",
        "8---6---3",
        "4--8-3--1",
        "7---2---6",
        "-6--5--28", // '5' appears twice in the bottom-right 3×3 subgrid
        "---412--5",
        "----8-759"
    )

    // ❌ Multiple Conflicts (Row + Column Conflict)
    val invalidSudokuMultiple = listOf(
        "53--7----",
        "6--195---",
        "-98----6-",
        "8---6---3",
        "4--8-3--1",
        "7---2---6",
        "-6--5--28",
        "---419--5",
        "55--8--79"  // '5' repeated in row 8 and column 0
    )

    // ❌ Empty Sudoku Board (All cells are empty)
    val emptySudoku = listOf(
        "---------",
        "---------",
        "---------",
        "---------",
        "---------",
        "---------",
        "---------",
        "---------",
        "---------"
    )

    // ❌ Out of Range Values (Contains '0' which is invalid)
    val invalidSudokuOutOfRange = listOf(
        "53--7----",
        "6--195---",
        "-98----6-",
        "8---6---3",
        "4--8-3--1",
        "7---2---6",
        "-6--0--28",  // '0' is an invalid number in Sudoku
        "---419--5",
        "----8--79"
    )

    // ❌ Incorrect Grid Size (Only 8 rows instead of 9)
    val invalidSudokuTooFewRows = listOf(
        "53--7----",
        "6--195---",
        "-98----6-",
        "8---6---3",
        "4--8-3--1",
        "7---2---6",
        "-6--5--28",
        "---419--5"  // Missing one row
    )

    // ❌ Extra Row (More than 9 rows, which is invalid)
    val invalidSudokuTooManyRows = listOf(
        "53--7----",
        "6--195---",
        "-98----6-",
        "8---6---3",
        "4--8-3--1",
        "7---2---6",
        "-6--5--28",
        "---419--5",
        "----8--79",
        "----9--79" // Extra 10th row
    )

    // ❌ Empty List (Empty List)
    val invalidSudokuEmptyList = listOf("")

    // ❌ Fully Invalid Sudoku (Invalid Char)
    val invalidSudokuInvalidChar = listOf(
        "534678912",
        "672195348",
        "19834256a", // there is "a" in third row, last column
        "859761423",
        "426853791",
        "713924856",
        "961537284",
        "287419635",
        "345286179"
    )


    // ✅ Fully Completed Valid Sudoku
    val validCompletedSudoku = listOf(
        "534678912",
        "672195348",
        "198342567",
        "859761423",
        "426853791",
        "713924856",
        "961537284",
        "287419635",
        "345286179"
    )

    // Running test cases
    println(
        check(
            "Valid Sudoku",
            isValidSudoku(validSudoku),
            true
        )
    )
    println(
        check(
            "Invalid Sudoku (Row Conflict)(Duplicate '5' in row 0)",
            isValidSudoku(invalidSudokuRow),
            false
        )
    )
    println(
        check(
            "Invalid Sudoku (Contains invalid char)",
            isValidSudoku(invalidSudokuInvalidChar),
            false
        )
    )
    println(
        check(
            "Invalid Sudoku (empty List)",
            isValidSudoku(invalidSudokuEmptyList),
            false
        )
    )
    println(
        check(
            "Invalid Sudoku (Column Conflict)(Duplicate '5' in column 0)",
            isValidSudoku(invalidSudokuColumn),
            false
        )
    )
    println(
        check(
            "Invalid Sudoku (3x3 Box Conflict)(Duplicate '5' in the same subgrid)",
            isValidSudoku(invalidSudokuBox),
            false
        )
    )
    println(
        check(
            "Invalid Sudoku (Multiple Conflicts)(5' repeated in row 8 and column 0)",
            isValidSudoku(invalidSudokuMultiple),
            false
        )
    )
    println(
        check(
            "Empty Sudoku",
            isValidSudoku(emptySudoku),
            true
        )
    )
    println(
        check(
            "Invalid Sudoku (Out of Range Value)",
            isValidSudoku(invalidSudokuOutOfRange),
            false
        )
    )
    println(
        check(
            "Invalid Sudoku (Too Few Rows)",
            isValidSudoku(invalidSudokuTooFewRows),
            false
        )
    )
    println(
        check(
            "Invalid Sudoku (Too Many Rows)",
            isValidSudoku(invalidSudokuTooManyRows),
            false
        )
    )
    println(
        check(
            "Valid Completed Sudoku",
            isValidSudoku(validCompletedSudoku),
            true
        )
    )
}


fun isValidSudoku(board: List<String>): Boolean {
    val size = board.size
    val boxSize = sqrt(size.toDouble()).toInt()

    // Ensure all rows have the correct length
    if (board.any { it.length != size }) return false

    val rows = Array(size) { mutableSetOf<Char>() }
    val cols = Array(size) { mutableSetOf<Char>() }
    val boxes = Array(size) { mutableSetOf<Char>() }

    for (r in 0 until size) {
        for (c in 0 until size) {
            if (c >= board[r].length) return false // Prevent out-of-bounds access

            val num = board[r][c]

            if (num == '-') continue // Ignore empty cells

            // Validate character range
            if (num !in '1'..('0' + size) || num == '0') return false
            /**
            0 0 0 | 1 1 1 | 2 2 2

            0 0 0 | 1 1 1 | 2 2 2

            0 0 0 | 1 1 1 | 2 2 2

            ------+-------+------

            3 3 3 | 4 4 4 | 5 5 5

            3 3 3 | 4 4 4 | 5 5 5

            3 3 3 | 4 4 4 | 5 5 5

            ------+-------+------

            6 6 6 | 7 7 7 | 8 8 8

            6 6 6 | 7 7 7 | 8 8 8

            6 6 6 | 7 7 7 | 8 8 8
             */

            //             ((what row box range)    *   (start row of the box)      +   which(what col box range)
            val boxIndex =      (r / boxSize)       *           boxSize             +        (c / boxSize)

            // Check for duplicates
            if (!rows[r].add(num) || !cols[c].add(num) || !boxes[boxIndex].add(num)) {
                return false
            }
        }
    }

    return true
}


fun check(description: String, result: Boolean, correctResult: Boolean): String {
    return if (correctResult == result) {
        "Success: $description"
    } else {
        "Failure: $description"
    }
}
