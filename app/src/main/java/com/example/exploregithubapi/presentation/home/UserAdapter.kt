package com.example.exploregithubapi.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exploregithubapi.R
import com.example.exploregithubapi.databinding.ItemListBinding
import com.example.exploregithubapi.domain.model.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){
    private val listUser = ArrayList<User>()

    fun setData(data: List<User>?) {
        if (data!!.isNotEmpty()) {
            listUser.clear()
            listUser.addAll(data)
            notifyDataSetChanged()
        }
    }

    fun updateData(data: User){
        val indexUpdatedData = listUser.indexOfFirst { user ->  user.login == data.login}
        listUser.set(indexUpdatedData, data)
        notifyItemChanged(indexUpdatedData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UserViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentData = listUser[position]
        holder.bind(currentData)
    }

    override fun getItemCount() = listUser.size


    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val itemBinding = ItemListBinding.bind(itemView)
        fun bind(data: User){
            itemBinding.data = data
        }
    }
}
