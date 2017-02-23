package com.tech.quiz.presenter;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import com.tech.quiz.databasemanager.DatabaseManager;
import com.tech.quiz.models.databasemodel.Questions;
import com.tech.quiz.repositories.presenter.UpdateQuestionPresenter;
import com.tech.quiz.util.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sachin Narang
 */

public class UpdateQuestionPresenterImpl implements UpdateQuestionPresenter {

    private final Context context;

    public UpdateQuestionPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void parseJson(JSONArray jsonArray, int technology) {
        List<ArrayMap<String, String>> arrayMapList = new ArrayList<>();
        try {

            JSONArray questionArray = jsonArray.getJSONArray(0);

            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject jsonObject = questionArray.optJSONObject(i);
                ArrayMap<String, String> arrayMap = new ArrayMap<>();
                arrayMap.put(Questions.ID, jsonObject.optString("id"));
                arrayMap.put(Questions.USER_LEVEL, jsonObject.optString("question_type").trim());
                arrayMap.put(Questions.CATEGORY, jsonObject.optString("category").trim());
                arrayMap.put(Questions.QUESTION, jsonObject.optString("question").trim());
                arrayMap.put(Questions.OPTION_A, jsonObject.optString("a").trim());
                arrayMap.put(Questions.OPTION_B, jsonObject.optString("b").trim());
                arrayMap.put(Questions.OPTION_C, jsonObject.optString("c").trim());
                arrayMap.put(Questions.OPTION_D, jsonObject.optString("d").trim());
                arrayMap.put(Questions.ANSWER, jsonObject.optString("answer"));
                arrayMapList.add(arrayMap);
            }

            updateQuestionInDB(arrayMapList, technology);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateQuestionInDB(List<ArrayMap<String, String>> arrayMapList, int technology) {
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(context);
        switch (technology) {
            case Constant.ANDROID:
                saveAndroidQuestion(databaseManager, arrayMapList);
                break;

            case Constant.IOS:
                saveIosQuestion(databaseManager, arrayMapList);
                break;

            case Constant.JAVA:
                saveJavaQuestion(databaseManager, arrayMapList);
                break;
        }
    }

    private void saveAndroidQuestion(DatabaseManager databaseManager, List<ArrayMap<String, String>> arrayMapList) {
        for (ArrayMap<String, String> arrayMap : arrayMapList) {
            databaseManager.updateAndroidQuestion(Integer.parseInt(arrayMap.get(Questions.ID)), arrayMap.get(Questions.USER_LEVEL),
                    arrayMap.get(Questions.CATEGORY), arrayMap.get(Questions.QUESTION), arrayMap.get(Questions.OPTION_A), arrayMap.get(Questions.OPTION_B),
                    arrayMap.get(Questions.OPTION_C), arrayMap.get(Questions.OPTION_D), Integer.parseInt(arrayMap.get(Questions.ANSWER)));
        }
    }

    private void saveIosQuestion(DatabaseManager databaseManager, List<ArrayMap<String, String>> arrayMapList) {
        for (ArrayMap<String, String> arrayMap : arrayMapList) {
            databaseManager.updateIosQuestion(Integer.parseInt(arrayMap.get(Questions.ID)), arrayMap.get(Questions.USER_LEVEL),
                    arrayMap.get(Questions.CATEGORY), arrayMap.get(Questions.QUESTION), arrayMap.get(Questions.OPTION_A), arrayMap.get(Questions.OPTION_B),
                    arrayMap.get(Questions.OPTION_C), arrayMap.get(Questions.OPTION_D), Integer.parseInt(arrayMap.get(Questions.ANSWER)));
        }
    }

    private void saveJavaQuestion(DatabaseManager databaseManager, List<ArrayMap<String, String>> arrayMapList) {
        for (ArrayMap<String, String> arrayMap : arrayMapList) {
            databaseManager.updateJavaQuestion(Integer.parseInt(arrayMap.get(Questions.ID)), arrayMap.get(Questions.USER_LEVEL),
                    arrayMap.get(Questions.CATEGORY), arrayMap.get(Questions.QUESTION), arrayMap.get(Questions.OPTION_A), arrayMap.get(Questions.OPTION_B),
                    arrayMap.get(Questions.OPTION_C), arrayMap.get(Questions.OPTION_D), Integer.parseInt(arrayMap.get(Questions.ANSWER)));
        }
    }
}
