package puzzles.day2

import Color.*

/** https://adventofcode.com/2023/day/2
  */
object CubeConundrum:
    val puzzle1CubeSet = Map(RED -> 12, GREEN -> 13, BLUE -> 14)
    private val gameRegex = "Game (\\d+): (.*)".r
    private val cubesRegex = "(\\d+) (\\w+)".r

    def parseGames(input: String): Map[Int, List[Map[Color, Int]]] =
        input
            .split('\n')
            .map:
                case gameRegex(gameNumber, cubeSets) =>
                    gameNumber.toInt -> parseCubeSets(cubeSets)
            .toMap

    private def parseCubeSets(cubeSets: String): List[Map[Color, Int]] =
        cubeSets
            .split("; ")
            .toList
            .map:
                _.split(", ")
                    .map:
                        case cubesRegex(count, color) =>
                            Color.valueOf(color.toUpperCase()) -> count.toInt
                    .toMap

    def solvePuzzle1(input: String, challenge: Map[Color, Int]): Int =
        val games = parseGames(input)
        val possibleGames =
            games.collect:
                case (gameNumber, game) if isGamePossible(game, challenge) =>
                    gameNumber

        possibleGames.sum

    def isGamePossible(
        game: List[Map[Color, Int]],
        challenge: Map[Color, Int]
    ): Boolean =
        game.foldLeft(true):
            case (acc, cubeSet) => isCubesPossible(cubeSet, challenge) && acc

    def isCubesPossible(
        cubes: Map[Color, Int],
        challenge: Map[Color, Int]
    ): Boolean =
        challenge.foldLeft(true):
            case (acc, (color, count)) =>
                count - cubes.get(color).getOrElse(0) >= 0 && acc

    def solvePuzzle2(input: String): Int =
        parseGames(input).values.map(cubesPower).sum

    def cubesPower(cubeSet: List[Map[Color, Int]]): Int =
        val maxCountOfColorsInCubeSet: Map[Color, Int] =
            cubeSet.foldLeft(Color.values.map(_ -> 1).toMap):
                case (acc, cubes) =>
                    acc.map: (color, count) =>
                        (
                          color,
                          cubes.get(color) match
                              case Some(colorCount: Int)
                                  if colorCount > count =>
                                  colorCount
                              case _ => count
                        )

        maxCountOfColorsInCubeSet.values.reduce(_ * _)
