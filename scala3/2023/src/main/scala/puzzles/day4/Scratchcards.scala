package puzzles.day4

import fastparse.*, SingleLineWhitespace.*
import scala.collection.mutable

case class Card(number: Int, winning: Set[Int], guesses: Set[Int]):
    val matching: Int = winning.intersect(guesses).size
    val points: Int =
        if (matching == 0) 0
        else math.pow(2, matching - 1).toInt

    val cardsWon: List[Int] = (number + 1 to number + matching).toList

object Scratchcards:
    def parseNumber[$: P]: P[Int] = P(CharsWhileIn("0-9").!.map(_.toInt))
    def parseCard[$: P]: P[Card] =
        P(
          "Card" ~/ parseNumber ~/ ":" ~/ parseNumber.rep ~/ "|" ~/ parseNumber.rep
        ).map:
            case (number, winning, guesses) =>
                Card(number, winning.toSet, guesses.toSet)

    def parseScratchCards(input: List[String]): List[Card] =
        input.map: line =>
            val Parsed.Success(value, _) =
                parse(line, parseCard): @unchecked
            value

    def solvePuzzle1(input: List[String]): Int =
        parseScratchCards(input).map(_.points).sum

    def solvePuzzle2(input: List[String]): Int =
        val originalCards = parseScratchCards(input)
        val cardsWonPerCard =
            List
                .unfold(originalCards): remaining =>
                    remaining.headOption.map: card =>
                        (card.number -> card.cardsWon) -> remaining.tail
                .toMap

        val cardCounts =
            cardsWonPerCard.foldLeft(mutable.Map.empty[Int, Int]):
                case (cardCount, (originalCard, cardCopies)) =>
                    def bumpCount(card: Int) =
                        cardCount.update(card, cardCount.getOrElse(card, 0) + 1)
                    def walk(remainingCards: List[Int]): Unit =
                        remainingCards.foreach: card =>
                            bumpCount(card)
                            walk(cardsWonPerCard(card))

                    bumpCount(originalCard)
                    walk(cardCopies)
                    cardCount

        cardCounts.values.sum
