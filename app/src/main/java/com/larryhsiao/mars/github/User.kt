package com.larryhsiao.mars.github

/**
 * Represents a Github user
 */
interface User {
    /**
     * The user avatar image url
     */
    fun imageUrl(): String

    /**
     * The user name
     */
    fun userName(): String
}