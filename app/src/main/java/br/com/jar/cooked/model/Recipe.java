package br.com.jar.cooked.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = Recipe.TABLE_NAME)
public class Recipe implements Parcelable {

    public static final String TABLE_NAME = "recipes";
    public static final String COLUMN_ID = BaseColumns._ID;

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @PrimaryKey
    @ColumnInfo(index = true, name = Recipe.COLUMN_ID)
    @SerializedName("id")
    private long id;
    @Ignore
    @SerializedName("name")
    private String name;
    @Ignore
    @SerializedName("servings")
    private int servings;
    @Ignore
    @SerializedName("image")
    private String image;
    @Ignore
    @SerializedName("ingredients")
    private List<Ingredient> ingredients;
    @Ignore
    @SerializedName("steps")
    private List<Step> steps;

    public Recipe(long id) {
        this.id = id;
    }

    protected Recipe(Parcel in) {
        id = in.readLong();
        name = in.readString();
        servings = in.readInt();
        image = in.readString();
        ingredients = new ArrayList<>();
        in.readList(ingredients, Ingredient.class.getClassLoader());
        steps = new ArrayList<>();
        in.readList(steps, Step.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(image);
        dest.writeList(ingredients);
        dest.writeList(steps);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

}
