package com.yke.livedataexample.view

import android.os.Bundle
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yke.livedataexample.R
import com.yke.livedataexample.model.Blog
import com.yke.livedataexample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var mainViewModel: MainViewModel? = null
    var mBlogAdapter: BlogAdapter? = null
    private val SPAM_COUNT = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        getPopularBlog()
        swiperefresh.setOnRefreshListener { getPopularBlog() }
    }

    private fun getPopularBlog() {
        swiperefresh.isRefreshing = false
        mainViewModel!!.allBlog.observe(this, Observer {  blogList ->
            prepareRecyclerView(blogList)
        })

    }

    private fun prepareRecyclerView(blogList: List<Blog>?) {

        mBlogAdapter = BlogAdapter(blogList)
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            blogRecyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            blogRecyclerView.layoutManager = GridLayoutManager(this, SPAM_COUNT)
        }

        blogRecyclerView.itemAnimator = DefaultItemAnimator()
        blogRecyclerView.adapter = mBlogAdapter
    }
}
