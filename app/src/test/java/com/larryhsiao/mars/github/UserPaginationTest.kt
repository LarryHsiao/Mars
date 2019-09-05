package com.larryhsiao.mars.github

import android.util.SparseArray
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * (Learning) Tests for [com.larryhsiao.mars.github.UserPagination]
 */
@RunWith(RobolectricTestRunner::class)
class UserPaginationTest {
    /**
     * Test the pagination result, check first two page have unique name.
     */
    @Test
    fun simple() {
        val result = SparseArray<User>()
        UserPagination(
            "l"
        ) { code, msg -> fail() }.let { pagination ->
            pagination.firstPage()
                .forEach { result.append(it.userName().hashCode(), it) }
            pagination.newPage()
                .forEach { result.append(it.userName().hashCode(), it) }
        }
        assertEquals(
            60,
            result.size()
        )
    }
}