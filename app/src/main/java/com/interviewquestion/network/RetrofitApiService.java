package com.interviewquestion.network;

import com.interviewquestion.repository.Question;
import com.interviewquestion.util.Constant;

import retrofit2.Call;
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

}
