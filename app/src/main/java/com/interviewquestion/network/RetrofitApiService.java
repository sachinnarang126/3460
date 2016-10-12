package com.interviewquestion.network;

import com.interviewquestion.repository.QuestionResponse;
import com.interviewquestion.util.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitApiService {

    @Headers("x-TokenType:Dev")
    @POST(Constant.ANDROID_URL)
    Call<QuestionResponse> getAndroidQuestion();

    @GET(Constant.IOS_URL)
    Call<QuestionResponse> getIosQuestion();

    @GET(Constant.JAVA_URL)
    Call<QuestionResponse> getJavaQuestion();

    @FormUrlEncoded
    @POST(Constant.IOS_POST_URL)
    Call<QuestionResponse> iosSelectedQuestion(@Field("id[]") List<Integer> id);

    @FormUrlEncoded
    @POST(Constant.ANDROID_POST_URL)
    Call<QuestionResponse> androidSelectedQuestion(@Field("id[]") List<Integer> id);

    @FormUrlEncoded
    @POST(Constant.JAVA_POST_URL)
    Call<QuestionResponse> javaSelectedQuestion(@Field("id[]") List<Integer> id);

}
