package com.example.jacpalberto.firebasechat.common

import android.support.v7.app.AppCompatActivity
import android.widget.EditText
import android.widget.Toast

/**
* Created by Alberto Carrillo on 12/19/17.
*/
fun AppCompatActivity.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun EditText.strValue() = this.text.toString()
fun EditText.clearText() { this.text.clear() }
