package puzzles.day5

import fastparse.*

class GardnerSuite extends munit.FunSuite:
    val testInput = scala.io.Source.fromResource("day5/TestAlmanac").mkString

    test("parse seeds succeeds"):
        val Parsed.Success(value, _) =
            parse(testInput, Gardner.parseSeeds, true): @unchecked
        assertEquals(value, List(79L, 14L, 55L, 13L))

    test("parse lookup table succeeds"):
        val rawLookupTable = """seed-to-soil map:
            |50 98 2
            |52 50 48""".stripMargin
        val Parsed.Success(value, _) =
            parse(rawLookupTable, Gardner.parseLookupTable): @unchecked

        assertEquals(
          value,
          SDLookupTable(
            List(
              SDRange(destination = 50L until 52L, source = 98L until 100L),
              SDRange(destination = 52L until 100L, source = 50L until 98L)
            )
          )
        )

    test("parse almanac succeeds"):
        assertEquals(
          Gardner.parseInput(testInput),
          Almanac(
            seeds = List(79L, 14L, 55L, 13L),
            seedToSoil = SDLookupTable(
              List(
                SDRange(destination = 50L until 52L, source = 98L until 100L),
                SDRange(destination = 52L until 100L, source = 50L until 98L)
              )
            ),
            soilToFertilizer = SDLookupTable(
              List(
                SDRange(destination = 0L until 37L, source = 15L until 52L),
                SDRange(destination = 37L until 39L, source = 52L until 54L),
                SDRange(destination = 39L until 54L, source = 0L until 15L)
              )
            ),
            fertilizerToWater = SDLookupTable(
              List(
                SDRange(destination = 49L until 57L, source = 53L until 61L),
                SDRange(destination = 0L until 42L, source = 11L until 53L),
                SDRange(destination = 42L until 49L, source = 0L until 7L),
                SDRange(destination = 57L until 61L, source = 7L until 11L)
              )
            ),
            waterToLight = SDLookupTable(
              List(
                SDRange(destination = 88L until 95L, source = 18L until 25L),
                SDRange(destination = 18L until 88L, source = 25L until 95L)
              )
            ),
            lightToTemperature = SDLookupTable(
              List(
                SDRange(destination = 45L until 68L, source = 77L until 100L),
                SDRange(destination = 81L until 100L, source = 45L until 64L),
                SDRange(destination = 68L until 81L, source = 64L until 77L)
              )
            ),
            temperatureToHumidity = SDLookupTable(
              List(
                SDRange(destination = 0L until 1L, source = 69L until 70L),
                SDRange(destination = 1L until 70L, source = 0L until 69L)
              )
            ),
            humidityToLocation = SDLookupTable(
              List(
                SDRange(destination = 60L until 97L, source = 56L until 93L),
                SDRange(destination = 56L until 60L, source = 93L until 97L)
              )
            )
          )
        )

    test("solve puzzle1 succeeds"):
        assertEquals(Gardner.solvePuzzle1(testInput), 35L)

    test("solve puzzle2 succeeds"):
        assertEquals(Gardner.solvePuzzle2(testInput), 46L)