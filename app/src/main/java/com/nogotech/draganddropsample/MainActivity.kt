package com.nogotech.draganddropsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.nogotech.draganddropsample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.recyclerView.adapter = RecyclerViewAdapter(
            Listener({
                Log.d("Item: ", it)
            }) { position, isSelected ->
                if (isSelected) viewModel.startSelectItem(position)
                else viewModel.endSelectItem(position)
            }
        )
    }
}