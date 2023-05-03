package com.example.part2_chapter6.userlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.part2_chapter6.Key.Companion.DB_USERS
import com.example.part2_chapter6.R
import com.example.part2_chapter6.databinding.FragmentUserlistBinding
import com.example.part2_chapter6.databinding.ItemUserBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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

        val currentUserId = Firebase.auth.currentUser?.uid ?: ""

        //복습
        Firebase.database.reference.child(DB_USERS)
            .addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {

                    val userItemList = mutableListOf<UserItem>()

                    snapshot.children.forEach{
                        val user = it.getValue(UserItem::class.java)
                        user ?: return

                        if(user.userId != currentUserId) {
                            userItemList.add(user)
                        }
                    }

                    userListAdapter.submitList(userItemList)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            } )

        userListAdapter.submitList(
           mutableListOf<UserItem?>().apply {
               add(UserItem("11","22","33"))
           }
        )
    }
}