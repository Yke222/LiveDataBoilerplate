package com.yke.livedataexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yke.livedataexample.model.Blog


class MainViewModel() : ViewModel() {

    private val movieRepository = BlogRepository()
    val allBlog: LiveData<List<Blog>> get() = movieRepository.getMutableLiveData()

    override fun onCleared() {
        super.onCleared()
        movieRepository.completableJob.cancel()
    }
}