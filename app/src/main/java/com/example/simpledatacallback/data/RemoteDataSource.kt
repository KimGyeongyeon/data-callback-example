package com.example.simplecallback.data

import kotlinx.coroutines.*

class RemoteDataSource : DataSource {
    private val fakeServer = arrayListOf<String>("옛날", "옛적에")

    override fun getAllData(callback: DataSource.GetCallback) {
        // 코루틴 적용. IO dispatcher 적용
        // task 있으면 callback.onLoaded 호출
        CoroutineScope(Dispatchers.IO).launch {
            delay(500L) // 통신 시간 걸리는 것 모방
            val getData = async {
                fakeServer.isEmpty()
            }
            val checkEmptyResult = getData.await()
            val sendResult = async {
                if (checkEmptyResult) {
                    callback.onDataNotAvailable()
                } else {
                    callback.onLoaded(fakeServer)
                }
            }
        }
    }

    override fun saveData(data: String) {
        CoroutineScope(Dispatchers.IO).launch {
            fakeServer.add(data)
        }
    }

    override fun deleteRecentData() {
        CoroutineScope(Dispatchers.IO).launch {
            fakeServer.removeLastOrNull()
        }
    }
}