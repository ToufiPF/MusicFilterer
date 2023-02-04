package ch.epfl.musicfilterer

import ch.epfl.musicfilterer.data.Music
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.faker
import kotlin.random.Random
import kotlin.random.asJavaRandom

@Suppress("MemberVisibilityCanBePrivate")
object MockUtil {

    val rng = Random(System.currentTimeMillis())

    fun makeFaker(): Faker = faker {
        fakerConfig {
            locale = "en"
            random = rng.asJavaRandom()
            uniqueGeneratorRetryLimit = 500
        }
    }

    fun generateMusic(faker: Faker = makeFaker()): Music {
        val name = faker.music.instruments()
        return Music(name, faker.music.bands(), faker.music.albums(), "/data/music/$name")
    }

    fun generatePlaylist(from: Int = 5, until: Int = 10, faker: Faker = makeFaker()): List<Music> {
        return Array(rng.nextInt(from, until)) { generateMusic(faker) }.toList()
    }

    fun generatePlaylist(size: Int = -1, faker: Faker = makeFaker()): List<Music> {
        return if (size < 0) generatePlaylist(faker = faker)
        else generatePlaylist(from = size, until = size + 1, faker = faker)
    }

}