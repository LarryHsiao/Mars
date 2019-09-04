package com.larryhsiao.mars.github.viewmodel

import android.app.Application
import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.larryhsiao.mars.github.ByName
import com.larryhsiao.mars.github.User
import com.larryhsiao.mars.R
import com.larryhsiao.mars.github.JsonUsers

/**
 *  View model of searching user
 */
class SearchUserVM(private val app: Application) : AndroidViewModel(app) {
    private val backgroundThread = HandlerThread("SearchBG").apply { start() }
    private val bgHandler = Handler(backgroundThread.looper)

    private val error = MutableLiveData<String>()
    private val users = ArrayList<User>()
    private val usersLiveData = MutableLiveData<List<User>>().apply { value = users }
    private var keyword: String = ""
    private var page = 0

    override fun onCleared() {
        super.onCleared()
        backgroundThread.interrupt()
    }

    /**
     * The liveData of user list
     */
    fun users(): LiveData<List<User>> {
        return usersLiveData
    }

    /**
     * Load up the first page, this will clean all the exist search result.
     */
    fun loadUp(keyword: String) {
        this.keyword = keyword
        this.page = 0
        bgHandler.post {
            ByName(
                keyword,
                0
            ).value().let {
                when {
                    it.code == 200 -> {
                        users.clear()
                        users.addAll(JsonUsers(String(it.bodyBytes)).value())
                        usersLiveData.value = users
                    }
                    else -> errorHandling(it.code)
                }
            }
        }
    }

    /**
     * Load the next page from github api.
     */
    fun nextPage(): LiveData<List<User>> {
        val live = MutableLiveData<List<User>>()
        bgHandler.post {
            ByName(
                keyword,
                ++page
            ).value().let {

                when {
                    it.code == 200 -> {
                        val result = JsonUsers(String(it.bodyBytes)).value()
                        users.addAll(result)
                        live.value = result
                    }
                    else -> errorHandling(it.code)
                }
            }
        }
        return live
    }

    private fun errorHandling(code: Int) {
        when (code) {
            in 400..499 -> error.value = app.getString(R.string.client_error)
            in 500..599 -> error.value = app.getString(R.string.server_error)
            else -> error.value = app.getString(R.string.unsupported_response)
        }
    }

    /**
     * The error message, empty string if no error.
     */
    fun error(): LiveData<String> {
        return error
    }
}