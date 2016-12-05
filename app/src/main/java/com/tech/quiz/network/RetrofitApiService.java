package com.tech.quiz.network;

import com.tech.quiz.models.bean.QuestionResponse;
import com.tech.quiz.models.bean.UserRegistor;
import com.tech.quiz.util.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

public interface RetrofitApiService {

    @Headers("x-TokenType:Dev")
    @POST(Constant.ANDROID_URL)
    Call<QuestionResponse> getAndroidQuestion();

    @POST(Constant.ANDROID_URL)
    Observable<QuestionResponse> getAndroidQuestion1();

    @GET(Constant.IOS_URL)
    Call<QuestionResponse> getIosQuestion();

    @GET(Constant.IOS_URL)
    Observable<QuestionResponse> getIosQuestion1();

    @GET(Constant.JAVA_URL)
    Call<QuestionResponse> getJavaQuestion();

    @GET(Constant.JAVA_URL)
    Observable<QuestionResponse> getJavaQuestion1();

    @FormUrlEncoded
    @POST(Constant.REGISTER_USER)
    Call<UserRegistor> registerUserForFCM(@Field("device_id") String deviceID, @Field("device_token") String device_token,
                                          @Field("device_type") String device_type);

    @FormUrlEncoded
    @POST(Constant.IOS_POST_URL)
    Call<QuestionResponse> getIosSelectedQuestion(@Field("id[]") List<Integer> id);

    @FormUrlEncoded
    @POST(Constant.ANDROID_POST_URL)
    Call<QuestionResponse> getAndroidSelectedQuestion(@Field("id[]") List<Integer> id);

    @FormUrlEncoded
    @POST(Constant.JAVA_POST_URL)
    Call<QuestionResponse> getJavaSelectedQuestion(@Field("id[]") List<Integer> id);

}
