/**
 *    Copyright 2016-today Software Craftmanship Toledo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

    public static final float DEFAULT_FONT_SIZE = 18;

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