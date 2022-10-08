package com.example.simplecallback.data

class Repository(
    val localDataSource: LocalDataSource,
    val remoteDataSource: RemoteDataSource
) : DataSource {

    var noData = true
    val cachedData = arrayListOf<String>()
    // 여기서 서버에서 받은 값을 캐싱할 수도 있다.

    override fun getAllData(callback: DataSource.GetCallback) {
        // 여기서 콜백을 만들어서, 그 결과를 함수에 인자로 들어온 콜백을 통해 넘겨야한다.
        if(noData){
            remoteDataSource.getAllData(object : DataSource.GetCallback{
                override fun onDataNotAvailable() {
                    callback.onDataNotAvailable()
                }

                override fun onLoaded(allData: ArrayList<String>) {
                    noData = false
                    cachedData.addAll(allData)
                    callback.onLoaded(allData)
                }
            })
        } else {
            localDataSource.getAllData(object : DataSource.GetCallback {
                override fun onDataNotAvailable() {
                    callback.onDataNotAvailable()
                }

                override fun onLoaded(allData: ArrayList<String>) {
                    cachedData.addAll(allData)
                    callback.onLoaded(cachedData)
                }
            })
        }
    }

    override fun saveData(data: String) {
        localDataSource.saveData(data)
    }

    override fun deleteRecentData() {
        localDataSource.deleteRecentData()
    }
}