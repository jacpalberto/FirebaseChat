package com.example.anzendigital.firebasechat.login

/**
 * Created by anzendigital on 12/20/17.
 */
class LoginPresenter(private val view: LoginContract.View, private val interactor: LoginInteractor) :
        LoginContract.Presenter {
    init {
        interactor.setPresenter(this)
    }

    override fun errorLoggingIn(localizedMessage: String) {
        view.showMessage(localizedMessage)
        view.updateUI()
    }

    override fun errorCreatingUser(localizedMessage: String) {
        view.showMessage(localizedMessage)
        view.updateUI()
    }

    override fun userLoggedIn() {
        view.clearFields()
        view.updateUI()
    }

    override fun userLoggedOut() {
        view.updateUI()
    }

    override fun login(email: String, password: String) {
        if (!email.isEmpty() && !password.isEmpty())
            interactor.login(email, password)
        else view.showMessage("Fields should not be empty")
    }

    override fun logout() {
        interactor.logout()
    }

    override fun createUser(email: String, password: String) {
        if (!email.isEmpty() && !password.isEmpty())
            interactor.createUser(email, password)
        else view.showMessage("Fields should not be empty")
    }
}