package com.example.anzendigital.firebasechat.rooms

import android.util.Log
import com.example.anzendigital.firebasechat.models.RoomChat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
* Created by Alberto Carrillo on 12/27/17.
*/
class RoomsInteractor : RoomsContract.Interactor {
    lateinit private var presenter: RoomsContract.Presenter
    private val database = FirebaseDatabase.getInstance()
    private val roomRef = database.getReference("rooms")

    override fun setPresenter(presenter: RoomsContract.Presenter) {
        this.presenter = presenter
    }

    override fun setRoomsListener() {
        roomRef.orderByChild("name").addListenerForSingleValueEvent(roomsListener)
    }

    override fun removeRoomsListener() {
        roomRef.removeEventListener(roomsListener)
    }

    private val roomsListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val rooms = dataSnapshot.children.flatMap { mutableListOf(it.getValue(RoomChat::class.java)) }
            rooms.forEach { Log.d("Rooms", it.toString()) }
            presenter.roomsFound(rooms)
        }

        override fun onCancelled(error: DatabaseError) {
            presenter.showMessage("code: ${error.code} ${error.message}")
        }
    }
}