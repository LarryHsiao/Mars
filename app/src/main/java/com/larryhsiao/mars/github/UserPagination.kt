package com.larryhsiao.mars.github

import com.google.gson.Gson
import com.google.gson.JsonParser
import com.silverhetch.clotho.connection.Response

/**
 * Pagination of Github user searching model.
 *
 * TODO: Consider use Iterator?
 */
class UserPagination(
    private val keyword: String,
    private val error: (code: Int, message: String) -> Unit
) : Pagination<User> {
    private var count = 0
    private var page = 1
    override fun firstPage(): List<User> {
        page = 1
        return handlingRes(ByName(keyword, page).value())
    }

    override fun newPage(): List<User> {
        return handlingRes(ByName(keyword, ++page).value())
    }

    private fun handlingRes(res: Response): List<User> {
        return if (res.code == 200) {
            success(res)
        } else {
            error(
                res.code,
                JsonParser().parse(String(res.bodyBytes))
                    .asJsonObject.get("message").asString
            )
            listOf()
        }
    }

    private fun success(res: Response): List<User> {
        return Gson().fromJson(
            String(res.bodyBytes),
            SearchPayload::class.java
        ).let {
            count = it.total_count
            GithubUsers(it.items).value()
        }
    }
}