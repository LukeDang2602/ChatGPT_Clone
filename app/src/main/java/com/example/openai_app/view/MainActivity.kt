package com.example.openai_app.view


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.openai_app.R
import com.example.openai_app.model.remote.NetworkResult
import com.example.openai_app.model.remote.responsemodel.ChatResponse
import com.example.openai_app.ui.theme.OpenAIappTheme
import com.example.openai_app.ui.theme.dp_15
import com.example.openai_app.ui.theme.dp_5
import com.example.openai_app.viewmodel.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OpenAIappTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dp_15),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatScreen(viewModel())
                }
            }
        }
    }
}

@Composable
fun ChatScreen(chatViewModel: ChatViewModel) {
    val result by chatViewModel.result.observeAsState()
    var message by remember { mutableStateOf("") }

    when(result){
        is NetworkResult.Success -> {
            message = (result as NetworkResult.Success<ChatResponse>).data?.choices?.get(0)?.message?.content ?: ""
        }
        is NetworkResult.Error -> {
            message = (result as NetworkResult.Error<ChatResponse>).mess.toString()
        }
        is NetworkResult.Exception -> {
            message = (result as NetworkResult.Exception<ChatResponse>).e.message.toString()
        }
        else -> {
            message = "Loading"
        }
    }
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val (chatMess, chatInput) = createRefs()
            ChatMessages(
                modifier = Modifier.constrainAs(chatMess){
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
                message = message
            )
            ChatInput(
                modifier = Modifier.constrainAs(chatInput){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }.fillMaxWidth(),
                sendMessage = { mess ->
                chatViewModel.sendMessage(mess)
            } )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInput(modifier: Modifier, sendMessage:(String) -> Unit) {
    var mess by remember { mutableStateOf("") }
    ConstraintLayout(
        modifier = modifier
            .padding(dp_5)
    ) {
        val (tField, sendBtn) = createRefs()

        TextField(
            modifier = Modifier.constrainAs(tField)
            {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                end.linkTo(sendBtn.start)
            },
            value = mess,
            onValueChange = {newMess ->
                mess = newMess
            }
        )
        FloatingActionButton(
            modifier = Modifier.constrainAs(sendBtn)
            {
                start.linkTo(tField.end)
                top.linkTo(parent.top)
                end.linkTo(parent.end)
            },
            onClick = { sendMessage(mess) }) {
            Text(text = stringResource(id = R.string.sendBtn))
        }
    }
}

@Composable
fun ChatMessages(modifier: Modifier, message: String) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = message,
        )
    }
}