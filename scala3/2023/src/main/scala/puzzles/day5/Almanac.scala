package puzzles.day5

import scala.collection.immutable.NumericRange
import scala.annotation.nowarn

final case class Almanac(
    seeds: List[Long],
    seedToSoil: SDLookupTable,
    soilToFertilizer: SDLookupTable,
    fertilizerToWater: SDLookupTable,
    waterToLight: SDLookupTable,
    lightToTemperature: SDLookupTable,
    temperatureToHumidity: SDLookupTable,
    humidityToLocation: SDLookupTable
):
    @nowarn("msg=match may not be exhaustive")
    def seedRanges: Iterator[NumericRange[Long]] =
        seeds
            .sliding(size = 2, step = 2)
            .map:
                case seedStart :: seedRangeLength :: Nil =>
                    seedStart until seedStart + seedRangeLength

    def locationForSeed(seed: Long): Long =
        val soil = seedToSoil.correspondingDestination(seed)
        val fertilizer = soilToFertilizer.correspondingDestination(soil)
        val water = fertilizerToWater.correspondingDestination(fertilizer)
        val light = waterToLight.correspondingDestination(water)
        val temperature = lightToTemperature.correspondingDestination(light)
        val humidity =
            temperatureToHumidity.correspondingDestination(temperature)

        humidityToLocation.correspondingDestination(humidity)

    def firstLocation: Long = seeds.map(locationForSeed).min

    def firstLocationForSeedRanges: Long =
        seedRanges.flatMap(_.map(locationForSeed)).min

final case class SDLookupTable(rows: List[SDRange]):
    def correspondingDestination(value: Long): Long =
        rows.collectFirst:
            case row if row.source.contains(value) =>
                val indexOfSourceValue = row.source.indexOf(value)
                row.destination.apply(indexOfSourceValue)
        .getOrElse(value)

final case class SDRange(
    destination: NumericRange[Long],
    source: NumericRange[Long]
)
