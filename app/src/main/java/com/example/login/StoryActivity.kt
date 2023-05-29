package com.example.login

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.login.adapter.StoryAdapter
import com.example.login.databinding.ActivityStoryBinding
import com.example.login.storage.SharedPrefManager
import com.example.login.viewModel.StoryViewModel

class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding
    private val adapter by lazy {
        StoryAdapter{
            Intent(this, DetailActivity::class.java).apply {
                putExtra("id", it.id)
                startActivity(this)
            }
        }
    }

    private val viewModel by viewModels<StoryViewModel>()

    private var backPressedTime = 0L

    override fun onResume() {
        super.onResume()
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter


        viewModel.resultSucces.observe(this){
            adapter.setData(it)
        }

        viewModel.resultError.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.resultLoading.observe(this){
            binding.progressBar.isVisible = it
        }

        viewModel.getStory()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_camera, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menuCamera -> {
                startActivity(Intent(this, AddPostActivity::class.java))
                true
            }
            R.id.menuLogOut -> {
                SharedPrefManager.getInstance(applicationContext).clear()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

//    override fun onStart() {
//        super.onStart()
//
//        if(SharedPrefManager.getInstance(this).isLoggedIn!!){
//            val intent = Intent(applicationContext, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }

}