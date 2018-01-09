package com.example.anzendigital.firebasechat.Rooms

import com.example.anzendigital.firebasechat.common.BasePresenter
import com.example.anzendigital.firebasechat.models.RoomChat

/**
 * Created by anzendigital on 12/27/17.
 */
interface RoomsContract {
    interface View {
        fun showRooms(rooms: List<RoomChat?>)
        fun showMessage(message: String)
    }

    interface Presenter : BasePresenter {
        fun roomsFound(rooms: List<RoomChat?>)
    }

    interface Interactor {
        fun setPresenter(presenter: RoomsContract.Presenter)
        fun setRoomsListener()
        fun removeRoomsListener()
    }
}