package com.example.openai_app.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.openai_app.model.remote.NetworkResult
import com.example.openai_app.model.remote.responsemodel.ChatResponse
import com.example.openai_app.model.repository.RemoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: RemoteRepository) : ViewModel() {
    private val _result = MutableLiveData<NetworkResult<ChatResponse>>(NetworkResult.Loading())
    val result: LiveData<NetworkResult<ChatResponse>>
        get() = _result

    fun sendMessage(mess: String){
        viewModelScope.launch(Dispatchers.IO){
            repository.sendMessage(mess)
                .collect{
                    _result.postValue(it)
                }
        }
    }
}