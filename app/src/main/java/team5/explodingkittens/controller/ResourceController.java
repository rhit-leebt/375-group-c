package team5.explodingkittens.controller;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import team5.explodingkittens.model.CardType;

/**
 * Manages the Message Bundle property files, letting the program
 * display the correct string for the selected language.
 *
 * @author Duncan McKee
 */
public final class ResourceController {
    private static final Locale KLINGON_LOCALE = new Locale("Klingon");
    private static final Locale[] AVAILABLE_LOCALES = {
        Locale.US,
        Locale.JAPAN,
        KLINGON_LOCALE
    };
    private static final String RESOURCE_BUNDLE_BASENAME = "MessagesBundle";
    private static final String CARD_NAME_PREFIX = "name";
    private static final String CARD_INFO_PREFIX = "info";
    private static final String FONT_NAME = "fontName";

    private static Locale formatLocale;
    private static ResourceBundle resources = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASENAME);
    private static NumberFormat numberFormat = NumberFormat.getInstance();

    public static int getNumberLocales() {
        return AVAILABLE_LOCALES.length;
    }

    public static Locale getLocale(int index) {
        return AVAILABLE_LOCALES[index];
    }

    /**
     * Sets the current locale to the provided locale.
     *
     * @param locale The locale to use for strings
     */
    public static void setLocale(Locale locale) {
        formatLocale = locale;
        resources = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASENAME, locale);
        numberFormat = NumberFormat.getInstance(locale);
    }

    public static String getString(String key) {
        return resources.getString(key);
    }

    /**
     * Retreives the correct string for the set locale, and fills in the detail.
     *
     * @param key The key to find a string for
     * @param option The detail to fill in into the string
     * @return A correctly formatted string
     */
    public static String getFormatString(String key, Object option) {
        if (formatLocale == null) {
            return String.format(getString(key), option);
        } else {
            return String.format(formatLocale, getString(key), option);
        }

    }

    public static String formatInteger(int number) {
        return numberFormat.format(number);
    }

    public static String getCardName(CardType card) {
        return resources.getString(CARD_NAME_PREFIX + card.genericName);
    }

    public static String getCardInfo(CardType card) {
        return resources.getString(CARD_INFO_PREFIX + card.genericName);
    }

    public static String getFontName() {
        return resources.getString(FONT_NAME);
    }
}
