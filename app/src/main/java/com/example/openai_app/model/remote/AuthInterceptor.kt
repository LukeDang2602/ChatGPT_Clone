package com.example.openai_app.model.remote

import com.example.openai_app.utils.Constants.AUTHORIZATION_HEADER
import com.example.openai_app.utils.Constants.OPENAI_API_KEY
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val curRequest = chain.request().newBuilder()
        curRequest.addHeader(AUTHORIZATION_HEADER, OPENAI_API_KEY)
        val newRequest = curRequest.build()
        return chain.proceed(newRequest)
    }
}