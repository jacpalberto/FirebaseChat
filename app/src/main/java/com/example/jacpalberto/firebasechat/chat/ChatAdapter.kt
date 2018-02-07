package com.example.jacpalberto.firebasechat.chat

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.jacpalberto.firebasechat.R
import com.example.jacpalberto.firebasechat.models.Message
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

/**
* Created by Alberto Carrillo on 12/26/17.
*/

class ChatAdapter(private val messages: List<Message?>) : RecyclerView.Adapter<ChatAdapter.ViewHolder>() {
    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(messages[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return when (viewType) {
            0 -> ViewHolder(layoutInflater.inflate(R.layout.item_owns_message, parent, false))
            else -> ViewHolder(layoutInflater.inflate(R.layout.item_others_message, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        val auth = FirebaseAuth.getInstance().currentUser
        val messageFromUserId = messages[position]?.fromUser?.id
        return if (messageFromUserId.equals(auth?.uid)) 0
        else 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var tvMessage: TextView
        private lateinit var tvTime: TextView
        private lateinit var tvFrom: TextView
        fun bind(message: Message?) = with(itemView) {
            tvMessage = findViewById(R.id.tvMessage)
            tvFrom = findViewById(R.id.tvFromUser)
            tvTime = findViewById(R.id.tvTime)
            val isNameEmpty : Boolean? = message?.fromUser?.name?.isEmpty()
            tvMessage.text = message?.text
            tvTime.text = parseDate(message?.time)
            tvFrom.text = if(isNameEmpty!!) message.fromUser?.email else message.fromUser?.name
        }

        private fun parseDate(time: String?): String {
            val formatComplete = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
            val formatDayHours = SimpleDateFormat("dd-MM HH:mm", Locale.getDefault())
            val formatHours = SimpleDateFormat("HH:mm", Locale.getDefault())
            val messageDate = Calendar.getInstance()
            val originDate = formatComplete.parse(time)
            messageDate.time = originDate
            return  if (DateUtils.isToday(messageDate.timeInMillis)) formatHours.format(originDate)
            else formatDayHours.format(originDate)
        }
    }
}