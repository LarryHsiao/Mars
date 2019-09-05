package com.larryhsiao.mars.github

/**
 * A User object adapter from [UserJson]
 */
class GithubUser(private val it: UserJson) : User {
    override fun imageUrl(): String {
        return it.avatar_url
    }

    override fun userName(): String {
        return it.login
    }
}