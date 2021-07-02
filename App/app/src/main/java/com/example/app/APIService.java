package com.example.app;


import com.example.app.Notifications.MyResponse;
import com.example.app.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA4ckVFPA:APA91bFuMOzGBOGlhGTvWnFDAdaKKpHGBAwX-LyDFxPHZ4je1a1jKKW3I5tzAksaODZ7UwtQR4M38fsIwR0cqrVHFWqo8v4zGCv5GMCUg7ZmSTxq_hyoRUR_UL3F4ghxjq9aVwh-VBa9"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
