package com.example.jacpalberto.firebasechat.rooms

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jacpalberto.firebasechat.R
import com.example.jacpalberto.firebasechat.models.RoomChat
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.item_room.view.*

/**
 * Created by Alberto Carrillo on 12/22/17.
 */
class RoomsAdapter(private val students: List<RoomChat?>, private val function: (RoomChat) -> Unit)
    : RecyclerView.Adapter<RoomsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bind(students[position], function)
    }

    override fun getItemCount() = students.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_room, parent, false))
    }

    fun removeAllListeners() {
        ViewHolder.removeListeners()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val database = FirebaseDatabase.getInstance()
        private lateinit var roomId: String
        private val chatRef by lazy { database.getReference("rooms/$roomId") }

        companion object {
            val userCountListeners = mutableListOf<Pair<DatabaseReference, ValueEventListener>>()

            fun removeListeners() {
                userCountListeners.forEach { it.first.removeEventListener(it.second) }
                userCountListeners.clear()
            }
        }

        fun bind(roomChat: RoomChat?, function: (RoomChat) -> Unit) = with(itemView) {
            tvRoomTitle.text = roomChat?.name
            tvDescription.text = roomChat?.description
            rlItemRoom.setOnClickListener { roomChat?.let { function(it) } }
            roomId = roomChat?.id!!
            val enterUserRef = chatRef.child("user")
            val userCountListener = object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    tvParticipants.text = context.getString(R.string.active_users, p0?.childrenCount.toString())
                    if (p0?.childrenCount.toString() == "0") tvParticipants.setTextColor(Color.RED)
                    else tvParticipants.setTextColor(Color.GREEN)
                }
            }
            userCountListeners.add(Pair(enterUserRef, userCountListener))
            enterUserRef.addValueEventListener(userCountListener)
        }
    }
}