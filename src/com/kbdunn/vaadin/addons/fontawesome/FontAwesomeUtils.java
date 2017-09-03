package com.kbdunn.vaadin.addons.fontawesome;

import java.util.HashMap;
import java.util.Map;

/**
 * Collection of helper methods for getting the instances of
 * {@link FontAwesome}.
 */
public final class FontAwesomeUtils {

    private static final int OFFSET;
    private static final Map<String, FontAwesome> FROM_CLASS;
    private static final FontAwesome[] FROM_CODEPOINT;
    static {
        FontAwesome[] values = FontAwesome.values();
        int minCodepoint = Integer.MAX_VALUE;
        int maxCodepoint = Integer.MIN_VALUE;

        FROM_CLASS = new HashMap<>(values.length, 1.0f);
        for (FontAwesome value : values) {
            int codepoint = value.getCodepoint();
            minCodepoint = minCodepoint > codepoint ? codepoint : minCodepoint;
            maxCodepoint = maxCodepoint < codepoint ? codepoint : maxCodepoint;
            FROM_CLASS.put(value.getClazz(), value);
        }

        OFFSET = minCodepoint;
        FROM_CODEPOINT = new FontAwesome[maxCodepoint - minCodepoint + 1];
        for (FontAwesome value : values) {
            FROM_CODEPOINT[value.getCodepoint() - OFFSET] = value;
        }
    }

    private FontAwesomeUtils() {
        // NO_INST
    }

    /**
     * <p>
     * Finds an instance of FontAwesome with given codepoint.
     * </p>
     * <p>
     * Unlike the Vaadin 7 object method, this one is backed up by an array-based
     * cache and thus runs in constant time.
     * </p>
     * 
     * @param codepoint
     *            of the icon, e.g. {@code 0xF005} for "fa-star"
     * @return the corresponding {@link FontAwesome} object
     */
    public static final FontAwesome fromCodepoint(int codepoint) {
        int offsetCodepoint = codepoint - OFFSET;
        return offsetCodepoint >= 0 && offsetCodepoint < FROM_CODEPOINT.length ? FROM_CODEPOINT[offsetCodepoint] : null;
    }

    /**
     * <p>
     * Finds an instance of FontAwesome with given class string.
     * </p>
     * <p>
     * The method is mainly intended for the purpose of parsing or persistence. The
     * use of this method when the icon is known beforehand is discouraged, instead
     * one should use the {@link FontAwesome} entries directly:
     * </p>
     * <p>
     * <code>
     * Button b = new Button(FontAwesomeUtils.fromClass("fa-star")); &#47;&#47; not recommended<br>
     * Button b = new Button(FontAwesome.STAR); &#47;&#47; recommended
     * </code>
     * </p>
     * 
     * @param clazz
     *            class string of the icon, e.g. "fa-star"
     * @return the corresponding {@link FontAwesome} object
     */
    public static final FontAwesome fromClass(String clazz) {
        return FROM_CLASS.get(clazz);
    }

}
