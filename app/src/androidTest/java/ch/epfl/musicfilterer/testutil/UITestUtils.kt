package ch.epfl.musicfilterer.testutil

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.app.NotificationManagerCompat
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.*
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import org.hamcrest.Matcher


@Suppress("UNUSED")
object UITestUtils {

    private const val BOOSTRAP_ACTIVITY_NAME =
        "androidx.test.core.app.InstrumentationActivityInvoker\$BootstrapActivity"

    /**
     * Asserts that there's no unverified intents, but ignores an eventual intent with component "BootstrapActivity"
     * which is the activity used to boot most tests.
     * @see Intents.assertNoUnverifiedIntents
     */
    fun assertNoUnverifiedIntentIgnoringBootstrap() {
        if (Intents.getIntents().find { it.component?.className == BOOSTRAP_ACTIVITY_NAME } != null)
            Intents.intended(IntentMatchers.hasComponent(BOOSTRAP_ACTIVITY_NAME))

        Intents.assertNoUnverifiedIntents()
    }

    /**
     * Returns a [ViewInteraction] for a menu item matching [matcher].
     * Overflows the menu/action bar if needed
     * and then uses [Espresso.onView] with the given matcher.
     *
     * @param matcher [Matcher] menu item matcher
     * @return [ViewInteraction] for the given item.
     */
    fun onMenuItem(matcher: Matcher<View>): ViewInteraction {
        try {
            val context: Context = getApplicationContext()
            Espresso.openActionBarOverflowOrOptionsMenu(context)
        } catch (ignored: Exception) {
            // there may be no menu overflow, ignore
        }

        return Espresso.onView(matcher)
    }

    /**
     * Shortcut to get the [UiDevice].
     * @see UiDevice.getInstance
     */
    fun getUiDevice(): UiDevice =
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    /**
     * Clears all notifications and closes the Notification Panel if open
     */
    fun clearAllNotifications() {
        NotificationManagerCompat.from(getApplicationContext()).cancelAll()
        closeNotificationPanel()
    }

    /**
     * Closes the notification panel if open
     */
    fun closeNotificationPanel() {
        // Causes SecurityException in normal uses, but it's authorized in android tests
        @Suppress("DEPRECATION")
        val it = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        getApplicationContext<Context>().sendBroadcast(it)
    }

    /**
     * Waits for a [UiObject2] matching [matcher] to appear on the screen,
     * and returns it.
     * @param matcher the [BySelector] matcher to satisfy
     * @param timeout (Long) timeout in ms
     * @param throwIfNotFound (Boolean) whether to throw if timeout is exceeded,
     * or to simply return null
     */
    fun waitAndFind(
        matcher: BySelector,
        timeout: Long = 2000L,
        throwIfNotFound: Boolean = true
    ): UiObject2? {
        val device = getUiDevice()

        val found = device.wait(Until.hasObject(matcher), timeout) ?: false
        if (throwIfNotFound && !found)
            throw RuntimeException(
                "Waited ${timeout}ms for an object matching $matcher to appear, in vain."
            )
        return device.findObject(matcher)
    }
}