package com.example.exploregithubapi.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.exploregithubapi.R
import com.example.exploregithubapi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    //rcy adapter
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initRv()

        viewModel.onSearch("alex")

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collect {
                    when(it) {
                        is MainViewModel.UiState.Success -> {
                            val data = it.users
                            val message = it.message

                            if (message.equals("0")){
                                userAdapter.setData(data)
                            } else {
                                data.map {
                                    userAdapter.updateData(it)
                                }
                            }


                            Log.d(TAG, "data = ${data.size}")
                            //binding.isLoading = false
                            //binding.data = data[0].word
                        }
                        is MainViewModel.UiState.Loading -> {
                            Log.d(TAG, "show Loading..")
                            //binding.isLoading = true
                        }
                        is MainViewModel.UiState.Error -> {
                            val error = it.message
                            Log.d(TAG, "error : ${error}")
                            //binding.isLoading = false
                        }
                    }
                }

            }
        }
    }

    private fun initRv(){
        userAdapter = UserAdapter()

        binding.apply {
            rcy.setHasFixedSize(true)
            rcy.layoutManager = LinearLayoutManager(this@MainActivity)
            val divider = DividerItemDecoration(this@MainActivity, (rcy.layoutManager as LinearLayoutManager).orientation)
            rcy.addItemDecoration(divider)
            (rcy?.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            rcy.adapter = userAdapter
        }
    }
}