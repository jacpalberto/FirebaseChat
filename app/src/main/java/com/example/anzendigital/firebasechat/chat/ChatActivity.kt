package com.example.anzendigital.firebasechat.chat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.anzendigital.firebasechat.R
import com.example.anzendigital.firebasechat.common.clearText
import com.example.anzendigital.firebasechat.common.strValue
import com.example.anzendigital.firebasechat.common.toast
import com.example.anzendigital.firebasechat.models.Message
import com.example.anzendigital.firebasechat.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_chat.*
import java.util.*


class ChatActivity : AppCompatActivity(), ChatContract.View {

    lateinit private var presenter: ChatContract.Presenter
    lateinit private var roomId: String
    lateinit private var roomName: String
    lateinit private var roomDescription: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        init()
    }

    private fun init() {
        extractRoomData()
        presenter = ChatPresenter(this, ChatInteractor(), roomId)
        initToolbar()
        initRvMessages()
        btnSendMessage.setOnClickListener { presenter.sendMessage(buildMessage()) }
    }

    private fun buildMessage(): Message {
        val user = FirebaseAuth.getInstance().currentUser
        val myUserData = User(user?.uid, user?.displayName, user?.email, user?.photoUrl.toString())
        return Message("", etMessage.strValue(), Calendar.getInstance().time.toString(), myUserData)
    }

    private fun extractRoomData() {
        roomId = intent.getStringExtra("id")
        roomName = intent.getStringExtra("name")
        roomDescription = intent.getStringExtra("description")
    }

    private fun initRvMessages() {
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        rvChat.layoutManager = layoutManager
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar.let { supportActionBar?.setTitle(roomName) }
        toolbar.subtitle = roomDescription
        toolbar.inflateMenu(R.menu.menu_toolbar_chat)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun setUserCount(count: Long) {
        tvUsersCount.text = getString(R.string.user_count, count.toString())
    }

    override fun showChatMessages(messages: List<Message?>) {
        rvChat.adapter = ChatAdapter(messages)
    }

    override fun showMessage(message: String) {
        toast(message)
    }

    override fun clearMessageEditText() {
        etMessage.clearText()
    }
}