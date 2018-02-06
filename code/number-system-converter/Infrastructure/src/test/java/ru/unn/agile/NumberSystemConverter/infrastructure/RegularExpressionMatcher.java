package ru.unn.agile.NumberSystemConverter.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegularExpressionMatcher extends BaseMatcher {
    public RegularExpressionMatcher(final String regularExpression) {
        this.regularExpression = regularExpression;
    }

    public boolean matches(final Object object) {
        String string = (String) object;
        return string.matches(regularExpression);
    }

    public void describeTo(final Description description) {
        description.appendText("String must match regular expression " + regularExpression);
    }

    public static Matcher<? super String> getMatcher(final String regularExpression) {
        @SuppressWarnings("unchecked")
        Matcher<? super String> casted =
            (Matcher<? super String>) new RegularExpressionMatcher(regularExpression);
        return casted;
    }

    private final String regularExpression;
}
