package br.com.jar.cooked.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import br.com.jar.cooked.model.Recipe;

@Dao
public interface RecipeDao {

    @Query("select * from " + Recipe.TABLE_NAME)
    Recipe loadRecipe();

    @Insert
    long insertRecipe(Recipe recipe);

}
