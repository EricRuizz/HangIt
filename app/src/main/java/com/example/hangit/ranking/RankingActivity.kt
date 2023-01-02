package com.example.hangit.ranking

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.hangit.MainActivity
import com.example.hangit.databinding.ActivityRankingBinding


class RankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRankingBinding

    private val rankingViewModel: RankingViewModel by viewModels()

    //Hardcode user list
    private var users = arrayListOf<User>().apply {
        add(User("Pochita", 3500))
        add(User("Sakura", 5000))
        add(User("Focalita", 10000))
        add(User("Roger", -3))
        add(User("Eric", 3))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //rankingViewModel.rankingActivity = this
        rankingViewModel.init(this@RankingActivity)

        //Update the ranking/recyclerView
        rankingViewModel.setAdapter(binding.list)

        rankingViewModel.addScore()
        rankingViewModel.openRanking()

        //Go to back the main screen
        binding.goBackButtonRanking.setOnClickListener {
            val intent = Intent(this@RankingActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }


    }

}