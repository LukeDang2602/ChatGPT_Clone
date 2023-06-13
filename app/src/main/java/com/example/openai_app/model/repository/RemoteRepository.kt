package com.example.openai_app.model.repository

import com.example.openai_app.model.remote.APIServices
import com.example.openai_app.model.remote.NetworkResult
import com.example.openai_app.model.remote.requestmodel.ChatRequest
import com.example.openai_app.model.remote.requestmodel.Message
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiServices: APIServices): IRepository {

    override suspend fun sendMessage(mess: String) = flow {
        try{
            val response = apiServices
                .sendMessage(ChatRequest(listOf(Message(mess)))
            )
            with(response){
                if(this.isSuccessful) {
                    emit(NetworkResult.Success(this.body()))
                } else emit(NetworkResult.Error(this.code(),this.message()))
            }
        }catch (e: Exception){
            emit(NetworkResult.Exception(e))
        }
    }
}
