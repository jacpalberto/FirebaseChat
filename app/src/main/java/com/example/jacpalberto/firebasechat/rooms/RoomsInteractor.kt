package com.example.jacpalberto.firebasechat.rooms

import android.util.Log
import com.example.jacpalberto.firebasechat.models.RoomChat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by Alberto Carrillo on 12/27/17.
 */
class RoomsInteractor : RoomsContract.Interactor {
    private lateinit var presenter: RoomsContract.Presenter
    private val roomRef = FirebaseDatabase.getInstance().getReference("rooms")

    override fun setPresenter(presenter: RoomsContract.Presenter) {
        this.presenter = presenter
    }

    override fun fetchRooms() {
        roomRef.orderByChild("name").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val rooms = dataSnapshot.children.flatMap { mutableListOf(it.getValue(RoomChat::class.java)) }
                rooms.forEach { Log.d("Rooms", it.toString()) }
                presenter.roomsFound(rooms)
            }

            override fun onCancelled(error: DatabaseError) {
                presenter.showMessage("code: ${error.code} ${error.message}")
            }
        })
    }
}