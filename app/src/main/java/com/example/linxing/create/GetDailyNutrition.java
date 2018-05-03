package com.example.linxing.create;

import android.util.Log;

/**
 * Created by linxing on 5/2/18.
 */

public class GetDailyNutrition {
    private static final String TAG = "GetDailyNutrition";
    public static double getCalorie(UserProfile profile) {
        double calorie = 0;
        int ageNum = 40;
        boolean genderNum = false;
        int weightNum = 60;
        String age = profile.getAge_profile();
        String gender = profile.getGender_profile();
        String weight = profile.getWeight_profile();

        switch (weight) {
            case "Weight: below 50 kg/110lbs":
                weightNum = 45;
                break;
            case "Weight: Over 70kg/154lbs":
                weightNum = 80;
                break;
            default:
                weightNum = 60;
                break;
        }

        switch (gender) {
            case "Male":
                genderNum = true;
                switch (age) {
                    case "Age: below 20":
                        ageNum = 20;
                        calorie = 15.3 * weightNum + 679;
                        break;
                    case "Age: over 50":
                        ageNum = 60;
                        calorie = 13.5 * weightNum + 487;
                        break;
                    default:
                        ageNum = 40;
                        calorie = 11.6 * weightNum + 879;
                        break;
                }
                break;
            default:
                genderNum = false;
                switch (age) {
                    case "Age: below 20":
                        ageNum = 20;
                        calorie = 14.7 * weightNum + 496;
                        break;
                    case "Age: over 50":
                        ageNum = 60;
                        calorie = 10.5 * weightNum + 596;
                        break;
                    default:
                        ageNum = 40;
                        calorie = 8.7 * weightNum + 829;
                        break;
                }
                break;
        }

        Log.d(TAG, "getCalorie: age is: " + ageNum);
        Log.d(TAG, "getCalorie: weight is: " + weightNum);
        Log.d(TAG, "getCalorie: gender is: " + genderNum);
        Log.d(TAG, "getCalorie: calorie is: " + calorie);
        return calorie;
    }
}
