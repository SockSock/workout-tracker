package com.example.workouttracker;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class ExerciseTypeConverter {
    @TypeConverter
    public static List<Exercise> fromString(String value) {
        Type listType = new TypeToken<List<Exercise>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Exercise> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
