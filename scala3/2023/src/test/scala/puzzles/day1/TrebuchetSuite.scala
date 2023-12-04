package puzzles.day1

class TrebuchetSuite extends munit.FunSuite:
    test("puzzle1 solution test that succeeds"):
        val input = """1abc2
            |pqr3stu8vwx
            |a1b2c3d4e5f
            |treb7uchet""".stripMargin
        assertEquals(Trebuchet.solvePuzzle1(input), 142)
    test("hand written puzzle1 solution test that succeeds"):
        val input = """v4
            |gqrnpz5sth
            |xcsmcfour3eightts
            |eight691seven8cxdbveightzv
            |onenjhcd9""".stripMargin
        assertEquals(Trebuchet.solvePuzzle1(input), 299)
    test("digits spelled with letters that succeeds"):
        // 29, 83, 13, 24, 42, 14, 76
        val input = """two1nine
            |eightwothree
            |abcone2threexyz
            |xtwone3four
            |4nineeightseven2
            |zoneight234
            |7pqrstsixteen""".stripMargin
        assertEquals(Trebuchet.solvePuzzle2(input), 281)
    test("hand written puzzle2 solution test that succeeds"):
        val input = """v4
                |gqrnpz5sth
                |xcsmcfour3eightts
                |eight691seven8cxdbveightzv
                |onenjhcd9""".stripMargin
        assertEquals(Trebuchet.solvePuzzle2(input), 254)
