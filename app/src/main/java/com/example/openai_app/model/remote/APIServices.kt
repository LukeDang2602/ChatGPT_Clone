package com.example.openai_app.model.remote

import com.example.openai_app.model.remote.requestmodel.ChatRequest
import com.example.openai_app.model.remote.responsemodel.ChatResponse
import com.example.openai_app.utils.Constants.CHAT_ENDPOINT
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface APIServices {
    @POST(CHAT_ENDPOINT)
    suspend fun sendMessage(@Body chatRequest: ChatRequest): Response<ChatResponse>
}