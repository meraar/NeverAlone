package com.example.neveralone;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.neveralone.Activity.Peticiones.CrearPeticionActivity;


import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.Date;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    String[] valores = {"Compras", "Asesoramiento", "Acompañamiento", "Otros"};

    @Rule
    public ActivityTestRule<CrearPeticionActivity> activityRule
            = new ActivityTestRule<>(CrearPeticionActivity.class);

    @Test
    public void camposVacios() {

        onView(withId(R.id.SpinnerCategoriaPeticion)).perform(click());
        onData(is(valores[1])).perform(click());

        onView(withId(R.id.idCrearPeticion)).perform(click());
        onView(withId(R.id.result))
                .check(matches(withText("Something went wrong")));
    }

    @Test
    public void fechaErronea() throws InterruptedException {

        onView(withId(R.id.SpinnerCategoriaPeticion)).perform(click());
        onData(is(valores[1])).perform(click());

        onView(withId(R.id.fecha_peticion)).perform(click());
        onView(withId(R.id.fecha_peticion)).perform(click());

        String name = DatePicker.class.getName();
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 11, 12));
        onView(withText("D'ACORD")).perform(click());

        onView(withId(R.id.idCrearPeticion)).perform(click());
        onView(withId(R.id.result))
                .check(matches(withText("Something went wrong")));

    }

    @Test
    public void horaErronea() throws InterruptedException {

        onView(withId(R.id.SpinnerCategoriaPeticion)).perform(click());
        onData(is(valores[1])).perform(click());

        onView(withId(R.id.fecha_peticion)).perform(click());
        onView(withId(R.id.fecha_peticion)).perform(click());

        String name = DatePicker.class.getName();
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2020, 11, 12));
        onView(withText("D'ACORD")).perform(click());

        onView(withId(R.id.hora_peticion)).perform(click());
        onView(withId(R.id.hora_peticion)).perform(click());

        onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(PickerActions.setTime(07, 11));
        onView(withText("D'acord")).perform(click());

        onView(withId(R.id.idCrearPeticion)).perform(click());
        onView(withId(R.id.result))
                .check(matches(withText("Something went wrong")));

    }

}