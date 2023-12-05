package puzzles.day3

import fastparse.*, NoWhitespace.*

/** https://adventofcode.com/2023/day/3
  */
object GearRatios:
    case class Symbol(index: Int, isGear: Boolean)

    def parseSymbol[$: P] = P(Index ~ CharIn("=*+#$/@%&\\-").!).map:
        case (index, symbol) => Symbol(index, isGear = symbol == "*")

    def parsePart[$: P]: P[Part] =
        P(Index ~ CharsWhileIn("0-9").! ~ Index).map:
            case (start, partNumber, end) => Part(partNumber.toInt, start, end)

    def parseSchematicLine[$: P]: P[SchematicLine] =
        P((".".rep ~ (parsePart | parseSymbol) ~/ ".".rep).rep ~ End)
            .map: partsAndSymbolIndices =>
                val (parts, symbols) = partsAndSymbolIndices.partitionMap:
                    case part: Part     => Left(part)
                    case symbol: Symbol => Right(symbol)
                val gearIndices = symbols.collect:
                    case symbol if symbol.isGear => symbol.index

                SchematicLine(
                  gearIndices = gearIndices,
                  symbolIndices = symbols.map(_.index),
                  parts = parts
                )

    def parseEngineSchematic(input: String): EngineSchematic =
        EngineSchematic(
          input
              .split("\n")
              .toList
              .map: schematicLine =>
                  val Parsed.Success(parsedSchematicLine, _) =
                      parse(schematicLine, parseSchematicLine): @unchecked
                  parsedSchematicLine
        )

    def solvePuzzle1(input: String): Int =
        parseEngineSchematic(input).validPartsSum

    def solvePuzzle2(input: String): Int =
        parseEngineSchematic(input).validGearRatiosSum
