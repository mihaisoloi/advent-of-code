package puzzles.day3

final case class EngineSchematic(lines: List[SchematicLine]):
    def validPartsSum: Int =
        LineNeighbours
            .neighbors(lines)
            .flatMap:
                case LineNeighbours(schematicLine, neighboringLines) =>
                    schematicLine.validPartNumbers(neighboringLines)
            .sum
    def validGearRatiosSum: Int =
        LineNeighbours
            .neighbors(lines)
            .flatMap:
                case LineNeighbours(schematicLine, neighboringLines) =>
                    schematicLine.validGearRatios(neighboringLines)
            .sum

final case class LineNeighbours[A](line: A, neighbouringLines: List[A])
object LineNeighbours:
    def neighbors[A](lines: List[A]): List[LineNeighbours[A]] =
        lines
            .foldLeft(List.empty[LineNeighbours[A]] -> lines.tail):
                case ((neighbors, remainingLines), line) =>
                    (
                      LineNeighbours(
                        line,
                        neighbors.headOption
                            .map(_.line)
                            .toList ++ remainingLines.headOption.toList
                      ) :: neighbors,
                      remainingLines.drop(1)
                    )
            ._1
            .reverse

final case class SchematicLine(
    gearIndices: Seq[Int],
    symbolIndices: Seq[Int],
    parts: Seq[Part]
):
    def validPartNumbers(neighboringLines: List[SchematicLine]): Seq[Int] =
        parts.collect:
            case part
                if part.isAdjacentToSymbol(
                  (this +: neighboringLines).flatMap(_.symbolIndices)
                ) =>
                part.number

    def validGearRatios(neighboringLines: List[SchematicLine]): Seq[Int] =
        for
            gearIndex <- gearIndices
            gears = (this +: neighboringLines)
                .flatMap(_.parts)
                .collect:
                    case part if part.isAdjacentToSymbol(Seq(gearIndex)) =>
                        part.number
            if (gears.size >= 2)
        yield gears.reduce(_ * _)

final case class Part(number: Int, startIndex: Int, endIndex: Int):
    def isAdjacentToSymbol(symbolIndices: Seq[Int]): Boolean =
        symbolIndices.exists: symbolIndex =>
            symbolIndex >= startIndex - 1 && symbolIndex <= endIndex
