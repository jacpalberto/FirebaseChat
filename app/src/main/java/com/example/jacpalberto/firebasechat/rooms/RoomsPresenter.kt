package com.example.jacpalberto.firebasechat.rooms

import com.example.jacpalberto.firebasechat.models.RoomChat

/**
 * Created by Alberto Carrillo on 12/27/17.
 */
class RoomsPresenter(private val view: RoomsContract.View, private val interactor: RoomsInteractor) :
        RoomsContract.Presenter {
    init {
        interactor.setPresenter(this)
    }

    override fun fetchRooms() {
        interactor.fetchRooms()
    }

    override fun roomsFound(rooms: List<RoomChat?>) {
        view.showRooms(rooms)
    }

    override fun showMessage(message: String) {
        view.showMessage(message)
    }
}