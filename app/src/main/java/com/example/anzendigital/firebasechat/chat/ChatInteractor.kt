package com.example.anzendigital.firebasechat.chat

import com.example.anzendigital.firebasechat.models.Message
import com.example.anzendigital.firebasechat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by anzendigital on 12/27/17.
 */
class ChatInteractor : ChatContract.Interactor {

    lateinit private var presenter: ChatContract.Presenter
    private var roomId = ""
    private val database = FirebaseDatabase.getInstance()
    private val chatRef by lazy { database.getReference("rooms/$roomId") }
    private val messageRef by lazy { chatRef.child("messages") }
    private val userRef by lazy { chatRef.child("user") }
    private val currentUser by lazy { FirebaseAuth.getInstance().currentUser }

    override fun setPresenter(presenter: ChatContract.Presenter) {
        this.presenter = presenter
    }

    override fun setRoomId(roomId: String) {
        this.roomId = roomId
    }

    override fun initChatMessagesSocket() {
        messageRef.addValueEventListener(chatMessageListener)
    }

    override fun initUsersCounterSocket() {
        userRef.addValueEventListener(usersCounterListener)
    }

    override fun sendMessage(message: Message) {
        val sendMessageRef = messageRef.push()
        sendMessageRef.setValue(Message(sendMessageRef.key, message.text, message.time, message.fromUser)).
                addOnCompleteListener { presenter.messageSent() }.
                addOnFailureListener { presenter.showMessage(it.localizedMessage) }
    }

    override fun registerUser() {
        val userAux = with(currentUser!!) { User(uid, displayName, email, photoUrl.toString()) }
        currentUser.let { userRef.child(currentUser?.uid).setValue(userAux) }
                .addOnFailureListener { presenter.showMessage(it.localizedMessage) }
    }

    override fun unRegisterUser() {
        val leaveUserRef = chatRef.child("user").child(currentUser?.uid)
        leaveUserRef.removeValue().addOnFailureListener { presenter.showMessage(it.localizedMessage) }
    }

    override fun removeChatMessagesListener() {
        messageRef.removeEventListener(chatMessageListener)
    }

    override fun removeUsersCounterListener() {
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