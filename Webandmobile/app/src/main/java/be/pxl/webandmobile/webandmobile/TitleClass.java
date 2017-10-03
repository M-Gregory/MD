package be.pxl.webandmobile.webandmobile;

/**
 * Created by 11400136 on 3/10/2017.
 */

public class TitleClass {
    private static String defaultTitle;

    public static void initialise(String title) {
        defaultTitle = title;
    }

    public static String getDefaultTitle() {
        return defaultTitle;
    }

    public static String getCustomisedTitle(String message) {
        return defaultTitle + ": " + message;
    }
}
