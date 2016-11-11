package cl.bastian.flashome.views.main.drawer;

import android.content.Context;
import android.content.SharedPreferences;

import java.net.URL;

/**
 * Created by santo_000 on 09-11-2016.
 */

public class PhotoData {

    private Context context;


    private static final String PHOTO_PREFERENCE_NAME = "PHOTO_PREFERENCE_NAME";
    private static final String PHOTO_KEY= "PHOTO_KEY";


    public PhotoData(Context context){
        this.context = context;
    }

    public void saveUrl(String url) {
        SharedPreferences savePhotoData = context.getSharedPreferences(PHOTO_PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = savePhotoData.edit();
        prefEditor.putString(PHOTO_KEY, url);
        prefEditor.commit();
    }

    public String getUrl() {
        SharedPreferences getSelectedImageUriPath = context.getSharedPreferences(PHOTO_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return getSelectedImageUriPath.getString(PHOTO_KEY, null);
    }
}
