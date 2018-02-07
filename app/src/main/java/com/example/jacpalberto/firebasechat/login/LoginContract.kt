package com.example.jacpalberto.firebasechat.login

/**
* Created by Alberto Carrillo on 12/20/17.
*/
interface LoginContract {
    interface View {
        fun updateUI()
        fun clearFields()
        fun showMessage(message: String)
    }

    interface Presenter {
        fun createUser(email: String, password: String)
        fun login(email: String, password: String)
        fun logout()
        fun errorLoggingIn(localizedMessage: String)
        fun errorCreatingUser(localizedMessage: String)
        fun userLoggedIn()
        fun userLoggedOut()
    }

    interface Interactor {
        fun setPresenter(presenter: LoginContract.Presenter)
        fun createUser(email: String, password: String)
        fun login(email: String, password: String)
        fun logout()
    }
}