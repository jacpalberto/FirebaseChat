package com.example.anzendigital.firebasechat.Profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.anzendigital.firebasechat.R
import com.example.anzendigital.firebasechat.common.clearText
import com.example.anzendigital.firebasechat.common.strValue
import com.example.anzendigital.firebasechat.common.toast
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), ProfileContract.View {
    lateinit private var presenter: ProfilePresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
    }

    private fun init() {
        presenter = ProfilePresenter(this, ProfileInteractor())
        presenter.fetchUserData()
        initToolbar()
        btnUpdateProfile.setOnClickListener { presenter.updateUserData(etName.strValue()) }
        btnChangePassword.setOnClickListener {
            presenter.changePassword(etNewPassword.strValue(), etConfirmPassword.strValue())
        }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar.let { supportActionBar?.setTitle("Firebase Chat") }
    }

    override fun updateUI(currentUser: FirebaseUser?) {
        currentUser.let {
            etEmail.setText(currentUser?.email)
            etName.setText(currentUser?.displayName)
        }
    }

    override fun clearPasswordFields() {
        etNewPassword.clearText()
        etConfirmPassword.clearText()
    }

    override fun showMessage(message: String) {
        toast(message)
    }
}