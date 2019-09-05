package com.larryhsiao.mars.github

import android.net.Uri
import com.larryhsiao.mars.BuildConfig
import com.silverhetch.clotho.Source
import com.silverhetch.clotho.connection.Get
import com.silverhetch.clotho.connection.Response
import com.silverhetch.clotho.connection.TargetImpl

/**
 * Get User list by user name from github api.
 */
class UserByName(
    private val keyword: String,
    private val page: Int
) : Source<Response> {
    override fun value(): Response {
        return Get(
            TargetImpl(
                Uri.parse(
                    BuildConfig.GITHU_API_HOST
                ).buildUpon()
                    .appendPath("search")
                    .appendPath("users")
                    .appendQueryParameter("q", keyword)
                    .appendQueryParameter("page", page.toString())
                    .build().toString()
            )
        ).request()
    }
}