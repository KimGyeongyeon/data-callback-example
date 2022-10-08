package com.example.simpledatacallback

import com.example.simplecallback.data.DataSource

class MainPresenter(
    private val repository: DataSource,
    private val view: MainActivity
    ) : DataSource.GetCallback{

    fun loadAllData(){
        repository.getAllData(this)
    }

    fun saveData(word: String){
        repository.saveData(word)
    }

    override fun onLoaded(allData: ArrayList<String>) {
        view.showData(allData.joinToString(" "))
    }

    override fun onDataNotAvailable() {
        view.alertNoData()
    }
}