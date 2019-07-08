package com.yke.livedataexample.viewmodel

import androidx.lifecycle.MutableLiveData
import com.yke.livedataexample.model.Blog
import com.yke.livedataexample.networking.RestApiService
import kotlinx.coroutines.*
import retrofit2.HttpException

class BlogRepository() {

    private var movies = mutableListOf<Blog>()
    private var mutableLiveData = MutableLiveData<List<Blog>>()
    val completableJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + completableJob)

    private val thisApiCorService by lazy {
        RestApiService.createCorService()
    }

    fun getMutableLiveData():MutableLiveData<List<Blog>> {
        coroutineScope.launch {
            val request = thisApiCorService.getPopularBlog()
            withContext(Dispatchers.Main) {
                
                try {
                    val response = request.await()
                    
                    if (response.blog != null) {
                        movies = response.blog as MutableList<Blog>;
                        mutableLiveData.value=movies;
                    }

                } catch (e: HttpException) {
                    // Log exception //

                } catch (e: Throwable) {
                    // Log error //)
                }
            }
        }
        
        return mutableLiveData;
    }
}
