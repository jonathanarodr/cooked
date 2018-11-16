package br.com.jar.cooked.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import br.com.jar.cooked.model.Recipe;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "cooked";
    private static final Object LOCK = new Object();
    private static AppDatabase mInstance;

    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Create a new instance of the database");

                mInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,
                        AppDatabase.DATABASE_NAME)
                        .build();
            }
        }

        Log.d(TAG, "Loading instance of the database...");

        return mInstance;
    }

    public abstract RecipeDao recipeDao();

}
