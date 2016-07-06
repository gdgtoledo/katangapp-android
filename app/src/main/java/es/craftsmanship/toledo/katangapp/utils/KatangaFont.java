package es.craftsmanship.toledo.katangapp.utils;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Manuel de la Peña
 */
public enum KatangaFont {

    QUICKSAND_BOLD, QUICKSAND_LIGHT, QUICKSAND_REGULAR;

    public static Typeface getFont(AssetManager assetManager, KatangaFont font) {
        String fontPath = getFontPath(font);

        Typeface typeface = _katangaFonts.get(font);

        if (typeface != null) {
            return typeface;
        }

        typeface = Typeface.createFromAsset(assetManager, fontPath);

        _katangaFonts.put(font, typeface);

        return typeface;
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

    private static Map<KatangaFont, Typeface> _katangaFonts = new ConcurrentHashMap<>();

}