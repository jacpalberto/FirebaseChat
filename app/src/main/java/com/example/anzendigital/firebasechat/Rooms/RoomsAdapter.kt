package com.example.anzendigital.firebasechat.Rooms

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.anzendigital.firebasechat.R
import com.example.anzendigital.firebasechat.models.RoomChat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.item_room.view.*

/**
 * Created by anzendigital on 12/22/17.
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val database = FirebaseDatabase.getInstance()
        lateinit private var roomId: String
        private val chatRef by lazy { database.getReference("rooms/$roomId") }

        fun bind(roomChat: RoomChat?, function: (RoomChat) -> Unit) = with(itemView) {
            tvRoomTitle.text = roomChat?.name
            tvDescription.text = roomChat?.description
            rlItemRoom.setOnClickListener { function(roomChat!!) }

            roomId = roomChat?.id!!
            val enterUserRef = chatRef.child("user")
            enterUserRef.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError?) {
                }

                override fun onDataChange(p0: DataSnapshot?) {
                    tvParticipants.text = "Active users: ${p0?.childrenCount.toString()}"
                    if (p0?.childrenCount.toString() == "0") tvParticipants.setTextColor(Color.RED)
                    else tvParticipants.setTextColor(Color.GREEN)
                }
            })
        }
    }
}