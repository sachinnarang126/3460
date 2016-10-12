package com.interviewquestion.network;

import com.interviewquestion.repository.Question;
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
    Call<Question> getAndroidQuestion();

    @GET(Constant.IOS_URL)
    Call<Question> getIosQuestion();

    @GET(Constant.JAVA_URL)
    Call<Question> getJavaQuestion();

    @FormUrlEncoded
    @POST(Constant.IOS_POST_URL)
    Call<Question> iosSelectedQuestion(@Field("id[]") List<Integer> id);

    @FormUrlEncoded
    @POST(Constant.ANDROID_POST_URL)
    Call<Question> androidSelectedQuestion(@Field("id[]") List<Integer> id);

    @FormUrlEncoded
    @POST(Constant.JAVA_POST_URL)
    Call<Question> javaSelectedQuestion(@Field("id[]") List<Integer> id);

}
