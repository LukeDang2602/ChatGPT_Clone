package com.example.openai_app.model.repository

import com.example.openai_app.model.remote.NetworkResult
import com.example.openai_app.model.remote.responsemodel.ChatResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface IRepository {
    suspend fun sendMessage(mess: String): Flow<NetworkResult<ChatResponse>>
}