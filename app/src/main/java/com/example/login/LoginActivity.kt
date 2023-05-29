package com.example.login

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.login.api.ApiConfig
import com.example.login.databinding.ActivityLoginBinding
import com.example.login.storage.SharedPrefManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

private lateinit var binding: ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        binding.tvRegister.setOnClickListener{
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }

        binding.btnLogin.setOnClickListener{
            val email = binding.etLoginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.etLoginEmail.error = "Email harus diisi"
                binding.etLoginEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.etLoginPassword.error = "Password harud diisi"
                binding.etLoginPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 8) {
                binding.etLoginPassword.error = "Password minimal 8 karakter"
                binding.etLoginPassword.requestFocus()
                return@setOnClickListener
            }

            showLoading(true)


            GlobalScope.launch(Dispatchers.IO) {
                launch (Dispatchers.Main){
                    flow {
                        val response = ApiConfig
                            .dicodingStoryService
                            .login(email, password)
                        emit(response)
                    }.onStart {
                        binding.progressBar.isVisible = true
                    }.onCompletion {
                        binding.progressBar.isVisible = false
                    }.catch {
                        Toast.makeText(this@LoginActivity, it.message.toString(), Toast.LENGTH_SHORT).show()
                        Log.e("Error", it.message.toString())
                    }.collect{
                        if (it.error !!){
                            SharedPrefManager.getInstance(applicationContext).saveUser(it.loginResult!!)
                            val intent = Intent(this@LoginActivity, StoryActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                        else{
                            val intent = Intent(this@LoginActivity, StoryActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            Toast.makeText(applicationContext, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.ivLogin, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}