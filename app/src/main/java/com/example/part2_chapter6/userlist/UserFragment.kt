package com.example.part2_chapter6.userlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.part2_chapter6.R
import com.example.part2_chapter6.databinding.FragmentUserlistBinding
import com.example.part2_chapter6.databinding.ItemUserBinding

class UserFragment: Fragment(R.layout.fragment_userlist) {

    private lateinit var binding: FragmentUserlistBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentUserlistBinding.bind(view)

        val userListAdapter = UserAdapter()
        binding.userListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userListAdapter
        }

        userListAdapter.submitList(
           mutableListOf<UserItem?>().apply {
               add(UserItem("11","22","33"))
           }
        )
    }
}