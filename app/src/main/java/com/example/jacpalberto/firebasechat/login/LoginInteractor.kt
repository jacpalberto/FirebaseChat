package com.example.jacpalberto.firebasechat.login

import com.google.firebase.auth.FirebaseAuth

/**
 * Created by Alberto Carrillo on 12/20/17.
 */
class LoginInteractor : LoginContract.Interactor {
    private lateinit var presenter: LoginContract.Presenter
    private val mAuth = FirebaseAuth.getInstance()

    override fun setPresenter(presenter: LoginContract.Presenter) {
        this.presenter = presenter
    }

    override fun createUser(email: String, password: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) presenter.userLoggedIn()
        }.addOnFailureListener { presenter.errorCreatingUser(it.localizedMessage) }
    }

    override fun login(email: String, password: String) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) presenter.userLoggedIn()
        }.addOnFailureListener { presenter.errorLoggingIn(it.localizedMessage) }
    }

    override fun logout() {
        mAuth.signOut()
        presenter.userLoggedOut()
    }
}