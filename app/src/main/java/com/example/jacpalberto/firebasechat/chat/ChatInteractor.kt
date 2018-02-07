package com.example.jacpalberto.firebasechat.chat

import com.example.jacpalberto.firebasechat.models.Message
import com.example.jacpalberto.firebasechat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by Alberto Carrillo on 12/27/17.
 */
class ChatInteractor(private val roomId: String) : ChatContract.Interactor {
    private lateinit var presenter: ChatContract.Presenter
    private val chatRef by lazy { FirebaseDatabase.getInstance().getReference("rooms/$roomId") }
    private val messageRef by lazy { chatRef.child("messages") }
    private val userRef by lazy { chatRef.child("user") }
    private val currentUser by lazy { FirebaseAuth.getInstance().currentUser }

    override fun setPresenter(presenter: ChatContract.Presenter) {
        this.presenter = presenter
    }

    override fun registerListeners() {
        initChatMessagesSocket()
        initUsersCounterSocket()
        registerUser()
    }

    override fun unregisterListeners() {
        unRegisterUser()
        removeChatMessagesListener()
        removeUsersCounterListener()
    }

    override fun sendMessage(message: Message) {
        val sendMessageRef = messageRef.push()
        sendMessageRef.setValue(Message(sendMessageRef.key, message.text, message.time, message.fromUser))
                .addOnCompleteListener { presenter.messageSent() }
                .addOnFailureListener { presenter.showMessage(it.localizedMessage) }
    }

    private fun initChatMessagesSocket() {
        messageRef.addValueEventListener(chatMessageListener)
    }

    private fun initUsersCounterSocket() {
        userRef.addValueEventListener(usersCounterListener)
    }

    private fun registerUser() {
        val userAux = with(currentUser) {
            User(this?.uid, this?.displayName, this?.email, this?.photoUrl?.toString())
        }
        currentUser.let { userRef.child(currentUser?.uid).setValue(userAux) }
                .addOnFailureListener { presenter.showMessage(it.localizedMessage) }
    }

    private fun unRegisterUser() {
        val leaveUserRef = chatRef.child("user").child(currentUser?.uid)
        leaveUserRef.removeValue().addOnFailureListener { presenter.showMessage(it.localizedMessage) }
    }

    private fun removeChatMessagesListener() {
        messageRef.removeEventListener(chatMessageListener)
    }

    private fun removeUsersCounterListener() {
        userRef.removeEventListener(usersCounterListener)
    }

    private val chatMessageListener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            presenter.showMessage(p0.message)
        }

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val messages = dataSnapshot.children.flatMap { mutableListOf(it.getValue(Message::class.java)) }
            presenter.chatMessagesFound(messages)
        }
    }
    private val usersCounterListener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError) {
            presenter.showMessage(p0.message)
        }

        override fun onDataChange(p0: DataSnapshot) {
            presenter.userCountFound(p0.childrenCount)
        }
    }
}