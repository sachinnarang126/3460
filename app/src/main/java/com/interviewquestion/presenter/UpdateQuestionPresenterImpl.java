package com.interviewquestion.presenter;

import android.content.Context;

import com.interviewquestion.databasemanager.DatabaseManager;
import com.interviewquestion.models.databasemodel.Questions;
import com.interviewquestion.repositories.presenter.UpdateQuestionPresenter;
import com.interviewquestion.util.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by root on 19/10/16.
 */

public class UpdateQuestionPresenterImpl implements UpdateQuestionPresenter {

    private Context context;

    public UpdateQuestionPresenterImpl(Context context) {
        this.context = context;
    }

    @Override
    public void parseJson(JSONArray jsonArray, int technology) {
        List<HashMap<String, String>> hashMapList = new ArrayList<>();
        try {

            JSONArray questionArray = jsonArray.getJSONArray(0);

            for (int i = 0; i < questionArray.length(); i++) {
                JSONObject jsonObject = questionArray.optJSONObject(i);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put(Questions.ID, jsonObject.optString("id"));
                hashMap.put(Questions.USER_LEVEL, jsonObject.optString("question_type").trim());
                hashMap.put(Questions.CATEGORY, jsonObject.optString("category").trim());
                hashMap.put(Questions.QUESTION, jsonObject.optString("question").trim());
                hashMap.put(Questions.OPTION_A, jsonObject.optString("a").trim());
                hashMap.put(Questions.OPTION_B, jsonObject.optString("b").trim());
                hashMap.put(Questions.OPTION_C, jsonObject.optString("c").trim());
                hashMap.put(Questions.OPTION_D, jsonObject.optString("d").trim());
                hashMap.put(Questions.ANSWER, jsonObject.optString("answer"));
                hashMapList.add(hashMap);
            }

            updateQuestionInDB(hashMapList, technology);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        QuestionResponse response = gson.fromJson(json, QuestionResponse.class);
    }

    private void updateQuestionInDB(List<HashMap<String, String>> hashMapList, int technology) {
        DatabaseManager databaseManager = DatabaseManager.getDataBaseManager(context);
        switch (technology) {
            case Constant.ANDROID:
                saveAndroidQuestion(databaseManager, hashMapList);
                break;

            case Constant.IOS:
                saveIosQuestion(databaseManager, hashMapList);
                break;

            case Constant.JAVA:
                saveJavaQuestion(databaseManager, hashMapList);
                break;
        }
    }

    private void saveAndroidQuestion(DatabaseManager databaseManager, List<HashMap<String, String>> hashMapList) {
        for (HashMap<String, String> hashMap : hashMapList) {
            databaseManager.updateAndroidQuestion(Integer.parseInt(hashMap.get(Questions.ID)), hashMap.get(Questions.USER_LEVEL),
                    hashMap.get(Questions.CATEGORY), hashMap.get(Questions.QUESTION), hashMap.get(Questions.OPTION_A), hashMap.get(Questions.OPTION_B),
                    hashMap.get(Questions.OPTION_C), hashMap.get(Questions.OPTION_D), Integer.parseInt(hashMap.get(Questions.ANSWER)));
        }
    }

    private void saveIosQuestion(DatabaseManager databaseManager, List<HashMap<String, String>> hashMapList) {
        for (HashMap<String, String> hashMap : hashMapList) {
            databaseManager.updateIosQuestion(Integer.parseInt(hashMap.get(Questions.ID)), hashMap.get(Questions.USER_LEVEL),
                    hashMap.get(Questions.CATEGORY), hashMap.get(Questions.QUESTION), hashMap.get(Questions.OPTION_A), hashMap.get(Questions.OPTION_B),
                    hashMap.get(Questions.OPTION_C), hashMap.get(Questions.OPTION_D), Integer.parseInt(hashMap.get(Questions.ANSWER)));
        }
    }

    private void saveJavaQuestion(DatabaseManager databaseManager, List<HashMap<String, String>> hashMapList) {
        for (HashMap<String, String> hashMap : hashMapList) {
            databaseManager.updateJavaQuestion(Integer.parseInt(hashMap.get(Questions.ID)), hashMap.get(Questions.USER_LEVEL),
                    hashMap.get(Questions.CATEGORY), hashMap.get(Questions.QUESTION), hashMap.get(Questions.OPTION_A), hashMap.get(Questions.OPTION_B),
                    hashMap.get(Questions.OPTION_C), hashMap.get(Questions.OPTION_D), Integer.parseInt(hashMap.get(Questions.ANSWER)));
        }
    }
}
