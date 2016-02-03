package cz.koto.misak.kotipoint.android.mobile.util;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;

import cz.koto.misak.kotipoint.android.mobile.util.recyclerview.RecyclerViewMatcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;


public class EspressoUtils {

    public static String getText(final Matcher<View> matcher) {
        final String[] stringHolder = {null};
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView) view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }


//    onView(withId(R.id.password)).check(matches(withError(
//            getActivity().getString(R.string.incorrect_data))));
//
//    private static Matcher<View> withError(final String expected) {
//        return new TypeSafeMatcher<View>() {
//
//            @Override
//            public boolean matchesSafely(View view) {
//                if (!(view instanceof EditText)) {
//                    return false;
//                }
//                EditText editText = (EditText) view;
//                return editText.getError().toString().equals(expected);
//            }
//
//            @Override
//            public void describeTo(Description description) {
//
//            }
//        };
//    }


    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {

        return new RecyclerViewMatcher(recyclerViewId);
    }

}
