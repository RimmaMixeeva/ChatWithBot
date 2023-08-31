package com.mr.chatgpt.presentation.components

import android.widget.LinearLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mr.chatgpt.Message

@Composable
fun chatScreen() {
    var chatMessages: ArrayList<Message> = arrayListOf(
        Message("hi", "me", "12:12"),
        Message("Hello! How can I assist you today?", "bot", "12:12"),
        Message("What can you do?", "me", "12:13"),
        Message("I am a language model trained by OpenAI, based on the GPT-3.5 architecture. I can help you with a wide range of tasks, such as answering questions, providing information on various topics, generating text, translating text, and more. Just tell me what you need help with, and I will do my best to assist you!", "bot", "12:14"),
        Message("Well, tell me what do you know about cats?", "me", "12:14"),
        Message("As a language model, I have been trained on a vast amount of text data, including information about cats. Here are some facts and general information about cats:\n" +
                "\n" +
                "    Cats are mammals and are known for their independent and mysterious nature.\n" +
                "    They are popular pets and come in a variety of breeds, colors, and sizes.\n" +
                "    Cats are known for their agility and hunting skills, as well as their ability to groom themselves.\n" +
                "    They have excellent hearing and eyesight, and can jump up to six times their body length in a single bound.\n" +
                "    Domestic cats are typically kept as indoor or outdoor pets, and require regular veterinary care, grooming, and exercise.\n" +
                "    Cats are carnivorous animals and require a diet that is high in protein and fat.\n" +
                "    They are social animals and can form strong bonds with their owners, but they may also exhibit independent and solitary behavior at times.\n" +
                "    Cats have been associated with various cultures and mythologies throughout history, and have been revered as sacred animals in some cultures.\n" +
                "\n" +
                "These are just a few examples of what I know about cats. If you have any specific questions or topics related to cats, feel free to ask me and I will do my best to provide you with accurate information.\n", "bot", "12:14")
    )

    Column(modifier = Modifier.fillMaxSize()) {
        chatMenu()
        chat(chatMessages)
//        userPanel()
    }
}

@Preview
@Composable
fun test3() {
    chatScreen()
}