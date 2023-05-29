package com.example.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.login.api.ApiConfig
import com.example.login.databinding.ActivityMainBinding
import com.example.login.storage.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

private lateinit var binding : ActivityMainBinding

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.etRegisterName.text.toString().trim()
            val email = binding.etRegisterEmail.text.toString().trim()
            val password = binding.etRegisterPassword.text.toString().trim()

            if (name.isEmpty()) {
                binding.etRegisterName.error = "Nama harus diisi"
                binding.etRegisterName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                binding.etRegisterEmail.error = "Email harus diisi"
                binding.etRegisterEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etRegisterPassword.error = "Password harud diisi"
                binding.etRegisterPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 8) {
                binding.etRegisterPassword.error = "Password minimal 8 karakter"
                binding.etRegisterPassword.requestFocus()
                return@setOnClickListener
            }

            showLoading(true)

            GlobalScope.launch (Dispatchers.IO){
                launch (Dispatchers.Main){
                    flow {
                        val response = ApiConfig
                            .dicodingStoryService
                            .register(name, email, password)
                        emit(response)
                    }.onStart {
                        showLoading(true)
                    }.onCompletion {
                        showLoading(false)
                    }.catch {
                        Toast.makeText(this@MainActivity, it.message.toString(),Toast.LENGTH_SHORT).show()
                    }.collect{
                        Toast.makeText(this@MainActivity, it.message,Toast.LENGTH_SHORT).show()
                    }
                }
            }


        }

    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

//    override fun onStart() {
//        super.onStart()
//
//        if(!SharedPrefManager.getInstance(this).isLoggedIn){
//            val intent = Intent(applicationContext, StoryActivity::class.java)
//            startActivity(intent)
//        }
//    }

}