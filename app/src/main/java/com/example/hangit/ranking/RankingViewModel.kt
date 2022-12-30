package com.example.hangit.ranking

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RankingViewModel : ViewModel() {
    /*
    val ranking = MutableLiveData<User>()
    val db = Firebase.database("https://hangit-660df-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("users") //collection name

    /*fun openRanking(user: String)
    {

    }*/

    private val currentUser: String
        get() = FirebaseAuth.getInstance().currentUser?.email
            ?: throw IllegalStateException("Not logged in")

    fun addUser()
    {

    }

    fun openRanking(user: String) {
        val id = "ranking"
        val request = db.child(id).get()

        request.addOnSuccessListener {
            val rnk = it.value

            if (rnk != null && rnk is Chat) {
                chat.postValue(cht!!)
                subscribe(cht)
            } else {
                createChatWith(user)
            }
        }

        request.addOnFailureListener {
            createChatWith(user)
        }

        /*
        val id = Chat.idChatOf(user, currentUser).toString()
        val request = db.child(id).get()

        request.addOnSuccessListener {
            val cht = it.value

            if (cht != null && cht is Chat) {
                chat.postValue(cht!!)
                subscribe(cht)
            } else {
                createChatWith(user)
            }
        }

        request.addOnFailureListener {
            createChatWith(user)
        }
        */
    }

    private fun createChatWith(user: String) {
        val rnk = Chat(user, "Chat with $user")
        db.child(cht.id.toString()).setValue(cht)
        subscribe(cht)
        chat.postValue(cht)
    }

    private fun subscribe(usr: User) {
        db.child(usr.name?.toString() ?: return).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val typeIndicator = object : GenericTypeIndicator<ArrayList<ChatMessage>>() {}
                val messages = snapshot.child("messages").getValue(typeIndicator)

                if (messages != null) {
                    usr.messages = messages
                    currentUser.postValue(usr)
                }
            }

            override fun onCancelled(error: DatabaseError) = Unit
        })
    }

    fun sendScore() {
        val message = U(currentUser, currentUser.sc)
        val scr = ranking.value ?: return

        cht.messages.add(message)
        db.child(cht.id?.toString() ?: return)
            .setValue(cht)
        ranking.postValue(cht)
    }

    */
}