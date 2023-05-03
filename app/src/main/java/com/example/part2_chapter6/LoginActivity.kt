package com.example.part2_chapter6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.*
import android.widget.Toast
import com.example.part2_chapter6.Key.Companion.DB_URL
import com.example.part2_chapter6.Key.Companion.DB_USERS
import com.example.part2_chapter6.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.signUpButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일 또는 패스워드가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // 회원가입 성공
                        Toast.makeText(this, "회원가입에 성공했습니다. 로그인 해주세요.", Toast.LENGTH_SHORT).show()

                    } else {
                        // 회원가입 실패
                        Toast.makeText(this, "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        binding.signInButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "이메일 또는 패스워드가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Firebase.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    // 복습
                    val currentUser = Firebase.auth.currentUser
                    if(task.isSuccessful && currentUser != null){
                        val userId = currentUser.uid
                        val user = mutableMapOf<String, Any>()
                        user["userId"] = userId
                        user["username"] = email

                        Firebase.database(DB_URL).reference.child(DB_USERS).child(userId).updateChildren(user)


                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.e("LoginActivity", task.exception.toString())
                        Toast.makeText(this, "이메일 또는 패스워드가 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}