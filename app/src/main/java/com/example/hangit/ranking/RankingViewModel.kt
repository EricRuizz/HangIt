package com.example.hangit.ranking

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hangit.GameActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class RankingViewModel : ViewModel() {

    public val ranking =  arrayListOf<User>()
    val db = Firebase.database("https://hangit-660df-default-rtdb.europe-west1.firebasedatabase.app/")
        .getReference("Users") //collection name

    fun openRanking()
    {
        db.get().addOnSuccessListener {
            it.children.forEach{ userID->
                ranking.add(User(userID.child("username").value as String, userID.child("score").value as Int))
            }
        }
    }

    private val currentUserID: String
        get() = FirebaseAuth.getInstance().currentUser?.uid
            ?: throw IllegalStateException("Not logged in")

    private val currentUser: String
        get() = FirebaseAuth.getInstance().currentUser?.email
            ?: throw IllegalStateException("Not logged in")

    fun addScore() {
        if(GameActivity.score == 0) return
        db.child(currentUserID).child("username").setValue(currentUser)
        db.child(currentUserID).child("score").setValue(GameActivity.score)
    }

}