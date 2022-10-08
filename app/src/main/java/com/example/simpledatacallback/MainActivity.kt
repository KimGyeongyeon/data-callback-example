package com.example.simpledatacallback

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.simplecallback.data.LocalDataSource
import com.example.simplecallback.data.RemoteDataSource
import com.example.simplecallback.data.Repository
import com.example.simpledatacallback.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val repository = Repository(LocalDataSource(), RemoteDataSource())
        val presenter = MainPresenter(repository, this)
        presenter.loadAllData()

        binding.addButton.setOnClickListener {
            presenter.saveData(binding.inputText.text.toString())
            presenter.loadAllData()
        }
    }

    fun showData(data: String){
        binding.root.post { binding.myTextview.text = data }
    }

    fun alertNoData(){
        val text = "No Data"
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(applicationContext, text, duration)
        toast.show()
    }
}