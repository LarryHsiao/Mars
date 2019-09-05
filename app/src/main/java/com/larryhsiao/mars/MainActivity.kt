package com.larryhsiao.mars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.larryhsiao.mars.github.view.UserAdapter
import com.larryhsiao.mars.github.viewmodel.SearchUserVM
import kotlinx.android.synthetic.main.activity_main.*

/**
 * The entry point of mars.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var vm: SearchUserVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter = UserAdapter()
        searchList.layoutManager = LinearLayoutManager(this)
        searchList.adapter = adapter

        vm = ViewModelProviders.of(this).get(SearchUserVM::class.java)
        vm.users().observe(this, Observer { adapter.loadUp(it) })
        vm.error().observe(this, Observer { errorHandling(it) })
        vm.search("")

        searchButton.setOnClickListener { 
           vm.search(searchInput.text.toString())
        }
    }

    private fun errorHandling(it: String) {
    }
}
