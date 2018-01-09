package com.example.anzendigital.firebasechat.models

/**
 * Created by anzendigital on 12/21/17.
 */
data class Message(var id: String = "",
                   var text: String = "",
                   var time: String = "",
                   var fromUser: User? = User())

data class User(var id: String? = "",
                var name: String? = "",
                var email: String? = "",
                var photo: String = "")

data class RoomChat(var id: String = "",
                    var name: String = "",
                    var description: String = "",
                    var participants: List<User> = emptyList())