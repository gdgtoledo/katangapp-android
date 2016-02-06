package es.craftsmanship.toledo.katangapp.utils;

import android.content.res.AssetManager;

import android.graphics.Typeface;

/**
 * @author Manuel de la Peña
 */
public enum KatangaFont {

   QUICKSAND_BOLD, QUICKSAND_LIGHT, QUICKSAND_REGULAR;

    public static Typeface getFont(AssetManager assetManager, KatangaFont font) {
        String fontPath = "fonts/Quicksand-Regular.ttf";

        switch (font) {
            case QUICKSAND_BOLD:
                fontPath = "fonts/Quicksand-Bold.ttf";
            case QUICKSAND_LIGHT:
                fontPath = "fonts/Quicksand-Light.ttf";
            case QUICKSAND_REGULAR:
                fontPath = "fonts/Quicksand-Regular.ttf";
        }

        return Typeface.createFromAsset(assetManager, fontPath);
    }

}