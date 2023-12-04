package puzzles.day2

import Color.*

class CubeConundrumSuite extends munit.FunSuite:
    test("parse games"):
        val input = "Game 10: 13 blue, 4 red; 12 red, 2 green, 6 blue; 22 green"
        val expected = Map(
          10 -> List(
            Map(BLUE -> 13, RED -> 4),
            Map(RED -> 12, GREEN -> 2, BLUE -> 6),
            Map(GREEN -> 22)
          )
        )

        assertEquals(CubeConundrum.parseGames(input), expected)

    val testInput = """Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        |Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        |Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        |Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        |Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green""".stripMargin

    test("puzzle1 solution test that succeeds"):
        assertEquals(
          CubeConundrum.solvePuzzle1(testInput, CubeConundrum.puzzle1CubeSet),
          8
        )

    test("puzzle2 solution test that succeeds"):
        assertEquals(CubeConundrum.solvePuzzle2(testInput), 2286)

    test("power of a set of cubes"):
        assertEquals(
          CubeConundrum.cubesPower(
            List(
              Map(BLUE -> 13, RED -> 4),
              Map(RED -> 12, GREEN -> 2, BLUE -> 6),
              Map(GREEN -> 22)
            )
          ),
          13 * 12 * 22
        ) // 13 blue, 12 red, 22 green
