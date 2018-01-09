package com.example.anzendigital.firebasechat.profile

import com.google.firebase.auth.FirebaseUser

/**
 * Created by anzendigital on 12/20/17.
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
        if (name.isEmpty()) view.showMessage("Name field should not be empty")
        else interactor.updateUserData(name)
    }

    override fun changePassword(newPassword: String, confirmedPassword: String) {
        if (!newPassword.isEmpty() and !confirmedPassword.isEmpty()) {
            if (newPassword == confirmedPassword) interactor.changePassword(newPassword)
            else view.showMessage("Password fields should match")
        } else view.showMessage("Password fields should not be empty")
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