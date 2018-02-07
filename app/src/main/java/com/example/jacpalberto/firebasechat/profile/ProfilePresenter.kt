package com.example.jacpalberto.firebasechat.profile

import com.google.firebase.auth.FirebaseUser

/**
 * Created by Alberto Carrillo on 12/20/17.
 */
class ProfilePresenter(private val view: ProfileContract.View, private val interactor:
ProfileInteractor) : ProfileContract.Presenter {
    init {
        interactor.setPresenter(this)
    }

    override fun fetchUserData() {
        interactor.fetchUserData()
    }

    override fun updateUserData(name: String) {
        interactor.updateUserData(name)
    }

    override fun changePassword(newPassword: String, confirmedPassword: String) {
        interactor.changePassword(newPassword)
    }

    override fun showMessage(message: String) {
        view.showMessage(message)
    }

    override fun userDataFound(currentUser: FirebaseUser?) {
        view.updateUI(currentUser)
    }

    override fun dataUpdated(currentUser: FirebaseUser?) {
        view.updateUI(currentUser)
        view.showMessage("Your profile has been updated")
    }

    override fun passwordUpdated() {
        view.showMessage("Your password has been updated")
        view.clearPasswordFields()
    }

}