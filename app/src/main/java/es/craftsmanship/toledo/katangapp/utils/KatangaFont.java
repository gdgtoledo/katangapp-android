package es.craftsmanship.toledo.katangapp.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

/**
 * @author Manuel de la Peña
 */
public enum KatangaFont {

    QUICKSAND_BOLD, QUICKSAND_LIGHT, QUICKSAND_REGULAR;

    public static Typeface getFont(AssetManager assetManager, KatangaFont font) {
        String fontPath = getFontPath(font);
        return Typeface.createFromAsset(assetManager, fontPath);
    }
    
    @NonNull
    private static String getFontPath(KatangaFont font) {
        switch (font) {
            case QUICKSAND_BOLD:
                return "fonts/Quicksand-Bold.ttf";
            case QUICKSAND_LIGHT:
                return "fonts/Quicksand-Light.ttf";
            case QUICKSAND_REGULAR:
            default:
                return "fonts/Quicksand-Regular.ttf";
        }
    }

}