package puzzles.day5

val puzzleInput =
    scala.io.Source.fromResource("day5/Almanac").mkString

@main def main() =
    // println("Puzzle1 = " + Gardner.solvePuzzle1(puzzleInput))
    println("Puzzle2 = " + Gardner.solvePuzzle2(puzzleInput))
