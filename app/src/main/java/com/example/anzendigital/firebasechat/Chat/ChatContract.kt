package com.example.anzendigital.firebasechat.Chat

import com.example.anzendigital.firebasechat.common.BasePresenter
import com.example.anzendigital.firebasechat.models.Message

/**
 * Created by anzendigital on 12/27/17.
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
        fun setRoomId(roomId: String)
        fun setPresenter(presenter: ChatContract.Presenter)
        fun sendMessage(message: Message)
        fun initChatMessagesSocket()
        fun initUsersCounterSocket()
        fun removeChatMessagesListener()
        fun removeUsersCounterListener()
        fun registerUser()
        fun unRegisterUser()
    }
}