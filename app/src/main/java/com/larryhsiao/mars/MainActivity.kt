package com.larryhsiao.mars

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.text.util.LinkifyCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.larryhsiao.mars.github.view.UserAdapter
import com.larryhsiao.mars.github.viewmodel.SearchUserVM
import com.silverhetch.aura.view.measures.DP
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.about -> {
                AlertDialog.Builder(this)
                    .setTitle(R.string.about)
                    .setView(TextView(this).apply {
                        setTextAppearance(R.style.Base_TextAppearance_AppCompat_Body1)

                        val padding = DP(context, 24f).px().toInt()
                        layoutParams =
                            ViewGroup.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
                                .apply {
                                    setPadding(
                                        padding,
                                        padding,
                                        padding,
                                        padding
                                    )
                                }
                        text = resources.getString(R.string.about_content)
                        autoLinkMask = Linkify.WEB_URLS
                        linksClickable = true
                        movementMethod = LinkMovementMethod.getInstance()
                    })
                    .setPositiveButton(R.string.ok) { _, _ -> }
                    .show()
            }
        }
        return super.onOptionsItemSelected(item)
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
