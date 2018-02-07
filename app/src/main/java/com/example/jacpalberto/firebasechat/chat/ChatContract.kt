package com.example.jacpalberto.firebasechat.chat

import com.example.jacpalberto.firebasechat.common.BasePresenter
import com.example.jacpalberto.firebasechat.models.Message

/**
* Created by Alberto Carrillo on 12/27/17.
*/
interface ChatContract {
    interface View {
        fun showChatMessages(messages: List<Message?>)
        fun showMessage(message: String)
        fun setUserCount(count: Long)
        fun clearMessageEditText()
    }

    interface Presenter : BasePresenter {
        fun chatMessagesFound(messages: List<Message?>)
        fun sendMessage(message: Message)
        fun userCountFound(count: Long)
        fun messageSent()
    }

    interface Interactor {
        fun setPresenter(presenter: ChatContract.Presenter)
        fun sendMessage(message: Message)
        fun registerListeners()
        fun unregisterListeners()
    }
}