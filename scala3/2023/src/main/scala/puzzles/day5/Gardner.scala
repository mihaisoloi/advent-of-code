package puzzles.day5

import fastparse.*, MultiLineWhitespace.*

object Gardner:
    def parseNumber[$: P]: P[Long] = P(CharsWhileIn("0-9").!.map(_.toLong))
    def parseSeeds[$: P]: P[List[Long]] =
        P("seeds:" ~/ parseNumber.rep).map(_.toList)
    def parseLookupTable[$: P]: P[SDLookupTable] = P(
      CharsWhileIn("a-z") ~/ "-to-" ~/ CharsWhileIn("a-z") ~/ "map:" ~/
          (parseNumber.rep(exactly = 3)).rep
    ).map: lookupTable =>
        SDLookupTable(
          lookupTable.toList.map:
              case destination :: source :: rangeLength :: Nil =>
                  SDRange(
                    destination = destination until destination + rangeLength,
                    source = source until source + rangeLength
                  )
        )
    def parseAlmanac[$: P]: P[Almanac] = P(
      parseSeeds ~/ parseLookupTable ~/ parseLookupTable ~/ parseLookupTable ~/ parseLookupTable ~/ parseLookupTable ~/ parseLookupTable ~/ parseLookupTable ~/ End
    ).map(Almanac.apply)

    def parseInput(input: String): Almanac =
        val Parsed.Success(value, _) = parse(input, parseAlmanac): @unchecked
        value

    def solvePuzzle1(input: String): Long =
        parseInput(input).firstLocation

    def solvePuzzle2(input: String): Long =
        parseInput(input).firstLocationForSeedRanges
