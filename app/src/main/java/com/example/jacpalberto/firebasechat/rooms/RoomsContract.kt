package com.example.jacpalberto.firebasechat.rooms

import com.example.jacpalberto.firebasechat.models.RoomChat

/**
 * Created by Alberto Carrillo on 12/27/17.
 */
interface RoomsContract {
    interface View {
        fun showRooms(rooms: List<RoomChat?>)
        fun showMessage(message: String)
    }

    interface Presenter {
        fun roomsFound(rooms: List<RoomChat?>)
        fun fetchRooms()
        fun showMessage(message: String)
    }

    interface Interactor {
        fun setPresenter(presenter: RoomsContract.Presenter)
        fun fetchRooms()
    }
}