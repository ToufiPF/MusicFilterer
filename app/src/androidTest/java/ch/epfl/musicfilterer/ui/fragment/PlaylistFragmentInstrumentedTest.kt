package ch.epfl.musicfilterer.ui.fragment

import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import ch.epfl.musicfilterer.MockUtil.generatePlaylist
import ch.epfl.musicfilterer.R
import ch.epfl.musicfilterer.data.Music
import ch.epfl.musicfilterer.testutil.SafeFragmentScenario
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PlaylistFragmentInstrumentedTest {

    @get:Rule(order = 0)
    val hilt = HiltAndroidRule(this)

    private val onRecycler: ViewInteraction get() = Espresso.onView(withId(R.id.playlist_recycler))

    private fun launchPlaylistFragments(
        playlist: List<Music>,
        test: (SafeFragmentScenario<PlaylistFragment>) -> Unit
    ) = SafeFragmentScenario.launchInHiltContainer { scenario ->

        test.invoke(scenario)
    }

    @Test
    fun fragmentDisplaysAllItemsInPlaylist() {
        val list = generatePlaylist(size = 8)
        launchPlaylistFragments(list) {

            onRecycler.check(matches(isDisplayed()))
        }
    }
}