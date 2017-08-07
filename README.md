# katangapp-android repo

[![Build Status](https://travis-ci.org/craftsmanship-toledo/katangapp-android.svg)](https://travis-ci.org/craftsmanship-toledo/katangapp-android)

## API keys
To verify application's Google Maps features, please create a constants.xml
file under `res/values` folder, which is ignored by Git. There write down
your api key like so:
```
<resources>
    <item name="google_maps_api_key" type="string">YOUR API KEY</item>
</resources>
```

## Building the Android APK

In the main directory, execute `gradlew assemble` and the unsigned APK will be generated in the `./app/build/outputs/apk` folder.

## Thanks to...

* [@chermidap](https://github.com/chermidap)
* [@nhpatt] (https://github.com/nhpatt)
* [@mdelapenya] (https://github.com/mdelapenya)
