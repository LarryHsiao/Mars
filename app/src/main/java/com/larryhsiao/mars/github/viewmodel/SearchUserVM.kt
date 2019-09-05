package com.larryhsiao.mars.github.viewmodel

import android.app.Application
import android.os.Handler
import android.os.HandlerThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.larryhsiao.mars.R
import com.larryhsiao.mars.github.*

/**
 *  View model of searching user
 */
class SearchUserVM(app: Application) : AndroidViewModel(app) {
    private val backgroundThread = HandlerThread("SearchBG").apply { start() }
    private val bgHandler = Handler(backgroundThread.looper)

    private val error = MutableLiveData<String>().apply { value = "" }
    private val users = ArrayList<User>()
    private val usersLiveData =
        MutableLiveData<List<User>>().apply { value = users }
    private lateinit var pagination: Pagination<User>
    private var keyword: String = ""
    private var loading = false

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
    fun search(keyword: String) {
        loading = true
        this.keyword = keyword
        bgHandler.post {
            users.clear()
            pagination = UserPagination(keyword) { code, message ->
                handleErr(
                    code,
                    message
                )
            }
            users.addAll(pagination.firstPage())
            loading = false
            usersLiveData.postValue(users)
        }
    }

    private fun handleErr(code: Int, message: String) {
        error.postValue("$code $message")
    }

    /**
     * Load the next page from github api.
     */
    fun nextPage(): LiveData<List<User>> {
        val live = MutableLiveData<List<User>>()
        if (loading){
            return live
        }
        loading = true
        bgHandler.post {
            val result = pagination.newPage()
            users.addAll(result)
            loading = false
            live.postValue(result)
        }
        return live
    }

    /**
     * The error message, empty string if no error.
     */
    fun error(): LiveData<String> {
        return error
    }
}