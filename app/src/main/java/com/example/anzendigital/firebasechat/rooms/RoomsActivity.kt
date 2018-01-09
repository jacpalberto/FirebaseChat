package com.example.anzendigital.firebasechat.rooms

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.anzendigital.firebasechat.chat.ChatActivity
import com.example.anzendigital.firebasechat.login.LoginActivity
import com.example.anzendigital.firebasechat.profile.ProfileActivity
import com.example.anzendigital.firebasechat.R
import com.example.anzendigital.firebasechat.common.toast
import com.example.anzendigital.firebasechat.models.RoomChat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_rooms.*

class RoomsActivity : AppCompatActivity(), RoomsContract.View {
    private val presenter by lazy { RoomsPresenter(this, RoomsInteractor()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rooms)
        init()
    }

    private fun init() {
        initToolbar()
        initRvRooms()
    }

    private fun initRvRooms() {
        rvRooms.layoutManager = LinearLayoutManager(this)
    }

    override fun showRooms(rooms: List<RoomChat?>) {
        rvRooms.adapter = RoomsAdapter(rooms) {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("id", it.id)
            intent.putExtra("description", it.description)
            intent.putExtra("name", it.name)
            startActivity(intent)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar.let { supportActionBar?.setTitle("Firebase Chat") }
        toolbar.subtitle = "Welcome"
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

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showMessage(message: String) {
        toast(message)
    }
}
