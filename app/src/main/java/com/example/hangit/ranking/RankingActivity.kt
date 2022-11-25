package com.example.hangit.ranking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hangit.databinding.ActivityRankingBinding
import com.example.hangit.particles.particle


class RankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRankingBinding
    private val adapter = RankingAdapter(this)

    private var users = arrayListOf<User>().apply {
        add(User("Focalita", 10000))
        add(User("RogerElFumador", -3))
        add(User("EricElCiego", 3))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        orderList()
        adapter.updateUsersList(users)
        binding.list.adapter = adapter

    }

    // TODO:perque no va???
    fun orderList() {
        (users.sortedByDescending(User::score)).reversed()
    }
}