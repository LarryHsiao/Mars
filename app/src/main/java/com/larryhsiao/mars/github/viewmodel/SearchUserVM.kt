package com.larryhsiao.mars.github.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.larryhsiao.mars.github.User

/**
 *  View model of searching user
 */
class SearchUserVM(private val app: Application) : AndroidViewModel(app) {
    private val users = ArrayList<User>()
    private val liveData = MutableLiveData<List<User>>().apply { value = users }
    private var page = 0

    /**
     * The liveData of user list
     */
    fun users(): LiveData<List<User>> {
        return liveData
    }

    /**
     * Load up the first page, this will clean all the exist search result.
     */
    fun loadUp() {
        TODO()
    }

    /**
     * Load the next page from github api.
     */
    fun nextPage(): LiveData<List<User>> {
        TODO()
    }
}