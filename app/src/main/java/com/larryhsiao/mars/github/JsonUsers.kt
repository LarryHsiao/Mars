package com.larryhsiao.mars.github

import com.silverhetch.clotho.Source

/**
 * Source to build user list from json which return from Github
 */
class JsonUsers(private val json: String) : Source<List<User>> {
    override fun value(): List<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}