package ru.unn.agile.StatisticalValues.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegularExpMatcher extends BaseMatcher {
    public RegularExpMatcher(final String regexp) {
        this.regexp = regexp;
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regexp);
    }

    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regexp);
    }

    public static Matcher<? super String> matchesPattern(final String regexp) {
        RegularExpMatcher matcher = new RegularExpMatcher(regexp);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }

    private final String regexp;
}
