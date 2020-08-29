package ru.bulat.currencyconvertertest

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.bulat.currencyconvertertest.ui.main.MainActivity

@RunWith(AndroidJUnit4::class)
class CurrencyFragmentEspressoTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testConvert() {
        onView(withId(R.id.amount))
            .perform(typeText("10"), closeSoftKeyboard())
        onView(withId(R.id.convert))
            .perform(click())
        onView(withId(R.id.result))
            .check(matches(withText("10,00")))
    }
}