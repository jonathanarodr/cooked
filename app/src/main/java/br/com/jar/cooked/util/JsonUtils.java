package br.com.jar.cooked.util;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public final class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static String loadAsset(Context context, String fileName) {
        try {
            InputStream inputStream = context.getAssets().open(fileName);

            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            return new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            Log.e(TAG, "Asset file " + ex.getMessage() + " not found");
            return "";
        }
    }

}
