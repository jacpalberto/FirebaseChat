package com.example.jacpalberto.firebasechat.chat

import com.example.jacpalberto.firebasechat.models.Message

/**
 * Created by Alberto Carrillo on 12/27/17.
 */
class ChatPresenter(private val view: ChatContract.View, private val interactor: ChatInteractor) :
        ChatContract.Presenter {
    init {
        interactor.setPresenter(this)
    }

    override fun userCountFound(count: Long) {
        view.setUserCount(count)
    }

    override fun messageSent() {
        view.clearMessageEditText()
    }

    override fun onResume() {
        interactor.registerListeners()
    }

    override fun onPause() {
        interactor.unregisterListeners()
    }

    override fun onDestroy() {
        interactor.unregisterListeners()
    }

    override fun showMessage(message: String) {
        view.showMessage(message)
    }

    override fun chatMessagesFound(messages: List<Message?>) {
        view.showChatMessages(messages)
    }

    override fun sendMessage(message: Message) {
        if (message.text.isNotEmpty()) interactor.sendMessage(message)
    }
}