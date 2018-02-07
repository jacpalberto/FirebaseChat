package com.example.jacpalberto.firebasechat.profile

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

/**
* Created by Alberto Carrillo on 12/20/17.
*/
class ProfileInteractor : ProfileContract.Interactor {
    private lateinit var presenter: ProfileContract.Presenter
    private val mAuth = FirebaseAuth.getInstance()

    override fun setPresenter(presenter: ProfileContract.Presenter) {
        this.presenter = presenter
    }

    override fun fetchUserData() {
        presenter.userDataFound(mAuth.currentUser)
    }

    fun updateEmail(email: String) {
        mAuth.currentUser?.updateEmail(email)
                ?.addOnCompleteListener { presenter.dataUpdated(mAuth.currentUser) }
                ?.addOnFailureListener { presenter.showMessage(it.localizedMessage) }
    }

    override fun updateUserData(name: String) {
        mAuth.currentUser?.updateProfile(UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build())
                ?.addOnCompleteListener { presenter.dataUpdated(mAuth.currentUser) }
                ?.addOnFailureListener { presenter.showMessage(it.localizedMessage) }
    }

    override fun changePassword(password: String) {
        mAuth.currentUser?.updatePassword(password)
                ?.addOnCompleteListener { presenter.passwordUpdated() }
                ?.addOnFailureListener { presenter.showMessage(it.localizedMessage) }
    }
}