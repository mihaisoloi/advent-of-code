package puzzles.day1

/** https://adventofcode.com/2023/day/1
  */
object Trebuchet:
    private val digitsMap =
        "zero, one, two, three, four, five, six, seven, eight, nine"
            .split(", ")
            .zipWithIndex
            .tail
            .toMap

    def solvePuzzle1(input: String): Int =
        def takeDigit(s: String): Char = s.dropWhile(!_.isDigit).charAt(0)

        input
            .split('\n')
            .map: s =>
                s"${takeDigit(s)}${takeDigit(s.reverse)}".toInt
            .sum

    def solvePuzzle2(input: String): Int =
        input
            .split('\n')
            .map: s =>
                s"${takeDigitLR(s).identifiedDigit}${takeDigitRL(s).identifiedDigit}".toInt
            .sum

    case class Acc(substring: String = "", identifiedDigit: Int = 0)

    def takeDigitLR(s: String): Acc =
        s.foldLeft(Acc()):
            case (acc, char) =>
                digitSearch(acc, char, acc.substring :+ char)

    def takeDigitRL(s: String): Acc =
        s.foldRight(Acc()):
            case (char, acc) =>
                digitSearch(acc, char, char +: acc.substring)

    def digitSearch(acc: Acc, char: Char, newChars: String): Acc =
        if (acc.identifiedDigit != 0) acc
        else if (char.isDigit) Acc(identifiedDigit = char.asDigit)
        else
            digitsMap.collectFirst {
                case (digit, numericalValue) if newChars.contains(digit) =>
                    numericalValue
            } match
                case Some(numericalValue) =>
                    Acc(identifiedDigit = numericalValue)
                case None => Acc(substring = newChars)
