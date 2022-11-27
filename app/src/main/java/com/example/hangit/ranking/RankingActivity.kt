package com.example.hangit.ranking

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hangit.MainActivity
import com.example.hangit.databinding.ActivityRankingBinding
import com.example.hangit.particles.particle


class RankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRankingBinding
    private val adapter = RankingAdapter(this)

    //Hardcode user list
    private var users = arrayListOf<User>().apply {
        add(User("Pochita", 3500))
        add(User("Sakura", 5000))
        add(User("Focalita", 10000))
        add(User("Roger", -3))
        add(User("Eric", 3))
    }

    //Order users by score ascending
    private var orderedUsers = users.sortedBy { it.score }.reversed()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Update the ranking/recyclerView
        adapter.updateUsersList(orderedUsers)
        binding.list.adapter = adapter

        //Go to back the main screen
        binding.goBackButtonRanking.setOnClickListener {
            val intent = Intent(this@RankingActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

    }

}