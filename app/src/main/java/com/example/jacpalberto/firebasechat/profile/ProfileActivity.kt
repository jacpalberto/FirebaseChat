package com.example.jacpalberto.firebasechat.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.jacpalberto.firebasechat.R
import com.example.jacpalberto.firebasechat.common.clearText
import com.example.jacpalberto.firebasechat.common.strValue
import com.example.jacpalberto.firebasechat.common.toast
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity(), ProfileContract.View {
    private val presenter by lazy { ProfilePresenter(this, ProfileInteractor()) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()
    }

    private fun init() {
        initToolbar()
        presenter.fetchUserData()
        btnUpdateProfile.setOnClickListener { validateName() }
        btnChangePassword.setOnClickListener { validatePassword() }
    }

    private fun validatePassword() {
        if (etNewPassword.strValue().isNotEmpty() and etConfirmPassword.strValue().isNotEmpty()) {
            if (etNewPassword.strValue() == etConfirmPassword.strValue())
                presenter.changePassword(etNewPassword.strValue(), etConfirmPassword.strValue())
            else showMessage("Password fields should match")
        } else showMessage(getString(R.string.passwords_cannot_ne_empty))
    }

    private fun validateName() {
        if (etName.strValue().isEmpty()) showMessage(getString(R.string.name_cannot_be_empty))
        else presenter.updateUserData(etName.strValue())
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar.let { supportActionBar?.setTitle(getString(R.string.app_name)) }
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