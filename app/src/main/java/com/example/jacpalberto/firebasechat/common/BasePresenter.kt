package com.example.jacpalberto.firebasechat.common

/**
* Created by Alberto Carrillo on 12/27/17.
*/
interface BasePresenter {
    fun onResume()
    fun onPause()
    fun onDestroy()
    fun showMessage(message: String)
}