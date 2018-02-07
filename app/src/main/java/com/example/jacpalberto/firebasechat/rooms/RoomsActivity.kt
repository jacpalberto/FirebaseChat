package com.example.jacpalberto.firebasechat.rooms

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.jacpalberto.firebasechat.R
import com.example.jacpalberto.firebasechat.chat.ChatActivity
import com.example.jacpalberto.firebasechat.common.toast
import com.example.jacpalberto.firebasechat.login.LoginActivity
import com.example.jacpalberto.firebasechat.models.RoomChat
import com.example.jacpalberto.firebasechat.profile.ProfileActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_rooms.*

class RoomsActivity : AppCompatActivity(), RoomsContract.View {
    private val presenter by lazy { RoomsPresenter(this, RoomsInteractor()) }
    private var adapter = RoomsAdapter(emptyList(), {})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)
        init()
    }

    private fun init() {
        initToolbar()
        initRvRooms()
        presenter.fetchRooms()
    }

    private fun initRvRooms() {
        rvRooms.layoutManager = LinearLayoutManager(this)
    }

    override fun showRooms(rooms: List<RoomChat?>) {
        adapter = RoomsAdapter(rooms) {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("id", it.id)
            intent.putExtra("description", it.description)
            intent.putExtra("name", it.name)
            adapter.removeAllListeners()
            startActivity(intent)
        }
        rvRooms.adapter = adapter
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar.let { supportActionBar?.setTitle(getString(R.string.app_name)) }
        toolbar.subtitle = getString(R.string.welcome)
        toolbar.inflateMenu(R.menu.menu_toolbar_chat)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_chat, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                true
            }
            R.id.logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showMessage(message: String) {
        toast(message)
    }
}
