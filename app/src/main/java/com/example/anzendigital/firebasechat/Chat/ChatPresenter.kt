package com.example.anzendigital.firebasechat.Chat

import com.example.anzendigital.firebasechat.models.Message

/**
 * Created by anzendigital on 12/27/17.
 */
class ChatPresenter(private val view: ChatContract.View, private val interactor: ChatInteractor, private val roomId: String) :
        ChatContract.Presenter {
    init {
        interactor.setPresenter(this)
        interactor.setRoomId(roomId)
    }

    override fun userCountFound(count: Long) {
        view.setUserCount(count)
    }

    override fun messageSent() {
        view.clearMessageEditText()
    }

    override fun onResume() {
        interactor.initChatMessagesSocket()
        interactor.initUsersCounterSocket()
        interactor.registerUser()
    }

    override fun onPause() {
        interactor.removeChatMessagesListener()
        interactor.removeChatMessagesListener()
    }

    override fun onDestroy() {
        interactor.removeChatMessagesListener()
        interactor.removeChatMessagesListener()
        interactor.unRegisterUser()
    }

    override fun showMessage(message: String) {
        view.showMessage(message)
    }

    override fun chatMessagesFound(messages: List<Message?>) {
        view.showChatMessages(messages)
    }

    override fun sendMessage(message: Message) {
        if (!message.text.isEmpty()) interactor.sendMessage(message)
    }
}