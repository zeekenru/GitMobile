package com.kovapss.gitmobile.model.api

import com.kovapss.gitmobile.entities.Notification
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface NotificationService {

    @GET("notifications")
    fun getNotifications(@Query("all") all : Boolean) : Single<Response<List<Notification>>>
}