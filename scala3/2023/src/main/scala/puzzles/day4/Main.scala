package puzzles.day4

val puzzleInput =
    scala.io.Source.fromResource("day4/Scratchcards").getLines().toList

@main def main() =
    println("Puzzle1 = " + Scratchcards.solvePuzzle1(puzzleInput))
    println("Puzzle2 = " + Scratchcards.solvePuzzle2(puzzleInput))
