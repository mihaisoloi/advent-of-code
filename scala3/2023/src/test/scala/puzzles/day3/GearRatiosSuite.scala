package puzzles.day3

import fastparse.*

class GearRatiosSuite extends munit.FunSuite:
    val testInput = """467..114..
        |...*......
        |..35..633.
        |......#...
        |617*......
        |.....+.58.
        |..592.....
        |......755.
        |...$.*....
        |.664.598..""".stripMargin

    test("parse part"):
        val Parsed.Success(value, _) =
            parse("2345", GearRatios.parsePart): @unchecked

        assert(value == Part(2345, 0, 4))

    test("parse symbol"):
        val Parsed.Success(value, _) =
            parse("$", GearRatios.parseSymbol): @unchecked

        assertEquals(value.index, 0)

    test("parse schematic line"):
        val input = "..467..114..*123"
        val Parsed.Success(value, _) =
            parse(input, GearRatios.parseSchematicLine): @unchecked

        assertEquals(
          value,
          SchematicLine(
            Seq(12),
            Seq(12),
            Seq(Part(467, 2, 5), Part(114, 7, 10), Part(123, 13, 16))
          )
        )

    test("parse input to correct engine schematic"):
        assertEquals(
          GearRatios.parseEngineSchematic(testInput),
          EngineSchematic(
            lines = List(
              elems = SchematicLine(
                gearIndices = Nil,
                symbolIndices = Nil,
                parts = List(Part(467, 0, 3), Part(114, 5, 8))
              ),
              SchematicLine(
                gearIndices = List(3),
                symbolIndices = List(3),
                parts = Nil
              ),
              SchematicLine(
                gearIndices = Nil,
                symbolIndices = Nil,
                parts = List(Part(35, 2, 4), Part(633, 6, 9))
              ),
              SchematicLine(
                gearIndices = Nil,
                symbolIndices = List(6),
                parts = Nil
              ),
              SchematicLine(
                gearIndices = List(3),
                symbolIndices = List(3),
                parts = List(Part(617, 0, 3))
              ),
              SchematicLine(
                gearIndices = Nil,
                symbolIndices = List(5),
                parts = List(Part(58, 7, 9))
              ),
              SchematicLine(
                gearIndices = Nil,
                symbolIndices = Nil,
                parts = List(Part(592, 2, 5))
              ),
              SchematicLine(
                gearIndices = Nil,
                symbolIndices = Nil,
                parts = List(Part(755, 6, 9))
              ),
              SchematicLine(
                gearIndices = List(5),
                symbolIndices = List(3, 5),
                parts = Nil
              ),
              SchematicLine(
                gearIndices = Nil,
                symbolIndices = Nil,
                parts = List(Part(664, 1, 4), Part(598, 5, 8))
              )
            )
          )
        )

    test("neighboring lines"):
        val input = List("a", "b", "c", "d", "e", "f")
        val expected = List(
          LineNeighbours("a", List("b")),
          LineNeighbours("b", List("a", "c")),
          LineNeighbours("c", List("b", "d")),
          LineNeighbours("d", List("c", "e")),
          LineNeighbours("e", List("d", "f")),
          LineNeighbours("f", List("e"))
        )

        assertEquals(LineNeighbours.neighbors(input), expected)

    test("puzzle1 solution should be successful"):
        assertEquals(GearRatios.solvePuzzle1(testInput), 4361)

    test("puzzle2 solution should be successful"):
        assertEquals(GearRatios.solvePuzzle2(testInput), 467 * 35 + 755 * 598)
