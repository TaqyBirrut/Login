package com.example.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import coil.load
import com.example.login.databinding.ActivityDetailBinding
import com.example.login.viewModel.DetailStoryViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailStoryViewModel> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id") ?: ""

        viewModel.resultSucces.observe(this){
            binding.ivFoto.load(it.photoUrl)
            binding.tvNama.text = it.name
            binding.tvDeskripsi.text = it.description
        }

        viewModel.resultError.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.resultLoading.observe(this){
            binding.progressBar.isVisible = it
        }

        viewModel.getDetailStory(id)

    }
}