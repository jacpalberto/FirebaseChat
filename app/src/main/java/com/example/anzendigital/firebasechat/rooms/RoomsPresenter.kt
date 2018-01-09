package com.example.anzendigital.firebasechat.rooms

import com.example.anzendigital.firebasechat.models.RoomChat

/**
 * Created by anzendigital on 12/27/17.
 */
class RoomsPresenter(private val view: RoomsContract.View, private val interactor: RoomsInteractor) :
        RoomsContract.Presenter {
    override fun roomsFound(rooms: List<RoomChat?>) {
        view.showRooms(rooms)
    }

    init {
        interactor.setPresenter(this)
    }

    override fun onResume() {
        interactor.setRoomsListener()
    }

    override fun onPause() {
        interactor.removeRoomsListener()
    }

    override fun onDestroy() {
        interactor.removeRoomsListener()
    }

    override fun showMessage(message: String) {
        view.showMessage(message)
    }
}