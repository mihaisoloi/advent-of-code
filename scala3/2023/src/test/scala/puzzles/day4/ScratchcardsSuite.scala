package puzzles.day4

import puzzles.day4.Scratchcards.parseScratchCards

class ScratchcardsSuite extends munit.FunSuite:
    val testInput =
        scala.io.Source.fromResource("day4/TestScratchcard").getLines().toList

    test("parse test scratchcard"):
        assertEquals(
          parseScratchCards(testInput),
          List(
            Card(
              number = 1,
              winning = Set(41, 48, 83, 86, 17),
              guesses = Set(83, 86, 6, 31, 17, 9, 48, 53)
            ),
            Card(
              number = 2,
              winning = Set(13, 32, 20, 16, 61),
              guesses = Set(61, 30, 68, 82, 17, 32, 24, 19)
            ),
            Card(
              number = 3,
              winning = Set(1, 21, 53, 59, 44),
              guesses = Set(69, 82, 63, 72, 16, 21, 14, 1)
            ),
            Card(
              number = 4,
              winning = Set(41, 92, 73, 84, 69),
              guesses = Set(59, 84, 76, 51, 58, 5, 54, 83)
            ),
            Card(
              number = 5,
              winning = Set(87, 83, 26, 28, 32),
              guesses = Set(88, 30, 70, 12, 93, 22, 82, 36)
            ),
            Card(
              number = 6,
              winning = Set(31, 18, 13, 56, 72),
              guesses = Set(74, 77, 10, 23, 35, 67, 36, 11)
            )
          )
        )

    test("card points should succeed"):
        val card = Card(
          number = 1,
          winning = Set(41, 48, 83, 86, 17),
          guesses = Set(83, 86, 6, 31, 17, 9, 48, 53)
        )

        assertEquals(card.points, 8)
        assertEquals(card.cardsWon, List(2, 3, 4, 5))

    test("puzzle1 solution should succeed"):
        assertEquals(Scratchcards.solvePuzzle1(testInput), 13)

    test("puzzle2 solution should succeed"):
        assertEquals(Scratchcards.solvePuzzle2(testInput), 30)
