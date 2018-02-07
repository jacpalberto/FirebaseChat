package com.example.jacpalberto.firebasechat.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.jacpalberto.firebasechat.rooms.RoomsActivity
import com.example.jacpalberto.firebasechat.R
import com.example.jacpalberto.firebasechat.common.clearText
import com.example.jacpalberto.firebasechat.common.strValue
import com.example.jacpalberto.firebasechat.common.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity(), LoginContract.View {
    private val presenter by lazy { LoginPresenter(this, LoginInteractor()) }
    private val mAuth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        if (mAuth.currentUser != null) {
            startActivity(Intent(this, RoomsActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }
        updateUI()

        btnSignIn.setOnClickListener { presenter.login(etMail.strValue(), etPassword.strValue()) }
        btnCreateAccount.setOnClickListener { presenter.createUser(etMail.strValue(), etPassword.strValue()) }
        btnSignOut.setOnClickListener { presenter.logout() }
        btnContinue.setOnClickListener {
            startActivity(Intent(this, RoomsActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }
    }

    override fun updateUI() {
        val currentUser = mAuth.currentUser
        status.text = if (currentUser != null) "${currentUser.email.toString()} Logged In" else "Signed Out"
        if (currentUser != null) {
            emailPasswordLayout.visibility = View.GONE
            signOutLayout.visibility = View.VISIBLE
            emailPasswordFieldsLayout.visibility = View.GONE
            btnContinue.visibility = View.VISIBLE
        } else {
            emailPasswordLayout.visibility = View.VISIBLE
            signOutLayout.visibility = View.GONE
            emailPasswordFieldsLayout.visibility = View.VISIBLE
            btnContinue.visibility = View.GONE
        }
    }

    override fun clearFields() {
        etMail.clearText()
        etPassword.clearText()
    }

    override fun showMessage(message: String) {
        toast(message)
    }
}
