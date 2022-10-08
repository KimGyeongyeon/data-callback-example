package com.example.simplecallback.data

import kotlinx.coroutines.*

class LocalDataSource : DataSource {
    private val localData = arrayListOf<String>()

    override fun getAllData(callback: DataSource.GetCallback) {
        // 코루틴 적용. IO dispatcher 적용
        // task 있으면 callback.onLoaded 호출
        CoroutineScope(Dispatchers.IO).launch {
            val getData = async {
                localData.isEmpty()
            }
            val checkEmptyResult = getData.await() // await로 Job의 결과를 받을 수 있다.
            val sendResult = async {
                if (checkEmptyResult) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onLoaded(localData)
                }
            }
        }
    }

    override fun saveData(data: String) {
        CoroutineScope(Dispatchers.IO).launch {
            localData.add(data)
        }
    }

    override fun deleteRecentData() {
        CoroutineScope(Dispatchers.IO).launch {
            localData.removeLastOrNull()
        }
    }
}