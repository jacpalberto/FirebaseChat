package com.example.anzendigital.firebasechat.common

/**
 * Created by anzendigital on 12/27/17.
 */
interface BasePresenter {
    fun onResume()
    fun onPause()
    fun onDestroy()
    fun showMessage(message: String)
}