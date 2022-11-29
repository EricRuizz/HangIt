package com.example.hangit.ranking

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hangit.databinding.UserItemBinding

class RankingAdapter(val context: Context) :
    RecyclerView.Adapter<RankingAdapter.UserViewHolder>() {

        private var users = listOf<User>()

        inner class UserViewHolder(binding: UserItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val name = binding.name
            val score = binding.score
            val number = binding.number
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = UserItemBinding.inflate(layoutInflater)
            return UserViewHolder(binding)
        }

        override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
            val user = users[position]
            holder.name.text = user.name
            holder.score.text = user.score.toString()
            holder.number.text = (position + 1).toString()

        }

        fun updateUsersList(users: List<User>) {
            this.users = users
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return users.size
        }
    }