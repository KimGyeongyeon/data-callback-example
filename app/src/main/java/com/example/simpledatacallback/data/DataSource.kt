package com.example.simplecallback.data

interface DataSource {

    interface GetCallback {

        fun onLoaded(allData: ArrayList<String>)

        fun onDataNotAvailable()

    }

    fun getAllData(callback: GetCallback)

    fun saveData(data: String)

    fun deleteRecentData()
}