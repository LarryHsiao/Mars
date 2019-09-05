package com.larryhsiao.mars.github

import com.silverhetch.clotho.Source

/**
 * User list adapter from Array<UserJson>
 */
class GithubUsers(private val items: Array<UserJson>) : Source<List<User>> {
    private val value by lazy {
        ArrayList<User>().apply {
            items.toList().mapTo(this, { GithubUser(it) })
        }
    }

    override fun value(): List<User> {
        return value
    }
}