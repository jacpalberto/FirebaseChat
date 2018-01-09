package com.example.anzendigital.firebasechat.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.anzendigital.firebasechat.rooms.RoomsActivity
import com.example.anzendigital.firebasechat.R
import com.example.anzendigital.firebasechat.common.clearText
import com.example.anzendigital.firebasechat.common.strValue
import com.example.anzendigital.firebasechat.common.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class LoginActivity : AppCompatActivity(), LoginContract.View {
    lateinit private var mAuth: FirebaseAuth
    lateinit private var presenter: LoginPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        mAuth = FirebaseAuth.getInstance()
        if (mAuth.currentUser != null) startActivity(Intent(this, RoomsActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))

        presenter = LoginPresenter(this, LoginInteractor())
        updateUI()

        btnSignIn.setOnClickListener { presenter.login(etMail.strValue(), etPassword.strValue()) }
        btnCreateAccount.setOnClickListener { presenter.createUser(etMail.strValue(), etPassword.strValue()) }
        btnSignOut.setOnClickListener { presenter.logout() }
        btnContinue.setOnClickListener { startActivity(Intent(this, RoomsActivity::class.java)) }
    }

    override fun updateUI() {
        val currentUser = mAuth.currentUser
        status.text = if (currentUser != null) "${currentUser.email.toString()} Logged In" else "Signed Out"
        currentUser.let {
            emailPasswordLayout.visibility = View.GONE
            signOutLayout.visibility = View.VISIBLE
            emailPasswordFieldsLayout.visibility = View.GONE
            btnContinue.visibility = View.VISIBLE
        }
        if (currentUser == null) {
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
