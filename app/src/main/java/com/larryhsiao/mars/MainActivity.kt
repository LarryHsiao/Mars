package com.larryhsiao.mars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
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
    private lateinit var adapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = UserAdapter {
            refreshLayout.isRefreshing = true
            vm.nextPage().observe(this, Observer {
                refreshLayout.isRefreshing = false
                adapter.append(it)
            })
        }
        searchList.layoutManager = LinearLayoutManager(this)
        searchList.adapter = adapter

        vm = ViewModelProviders.of(this).get(SearchUserVM::class.java)
        vm.users().observe(this, Observer {
            refreshLayout.isRefreshing = false
            adapter.loadUp(it)
        })
        vm.error().observe(this, Observer { errorHandling(it) })

        searchButton.setOnClickListener { searchByInput() }
        refreshLayout.setOnRefreshListener { searchByInput() }

        searchInput.setOnEditorActionListener { textView, i, keyEvent ->
            when (i) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    searchByInput()
                    true
                }
                else -> false
            }
        }
    }

    private fun searchByInput() {
        val input = searchInput.text.toString()
        if (input.isEmpty()) {
            refreshLayout.isRefreshing = false
            return
        }
        refreshLayout.isRefreshing = true
        vm.search(input)
    }

    private fun errorHandling(it: String) {
        if (it.isEmpty()) {
            return
        }
        AlertDialog.Builder(this)
            .setTitle(R.string.error)
            .setMessage(it)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .show()
    }
}
