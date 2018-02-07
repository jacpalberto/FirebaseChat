package com.example.jacpalberto.firebasechat.profile

import com.google.firebase.auth.FirebaseUser

/**
* Created by Alberto Carrillo on 12/20/17.
*/
interface ProfileContract {
    interface View {
        fun updateUI(currentUser: FirebaseUser?)
        fun showMessage(message: String)
        fun clearPasswordFields()
    }

    interface Presenter {
        fun fetchUserData()
        fun updateUserData(name: String)
        fun changePassword(newPassword: String, confirmedPassword: String)
        fun showMessage(message: String)
        fun userDataFound(currentUser: FirebaseUser?)
        fun dataUpdated(currentUser: FirebaseUser?)
        fun passwordUpdated()
    }

    interface Interactor {
        fun setPresenter(presenter: ProfileContract.Presenter)
        fun fetchUserData()
        fun updateUserData(name: String)
        fun changePassword(password: String)
    }
}