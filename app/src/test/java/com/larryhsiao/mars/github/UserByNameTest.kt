package com.larryhsiao.mars.github

import org.junit.Assert.*
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Tests for [com.larryhsiao.mars.github.UserByName]
 */
@Ignore
@RunWith(RobolectricTestRunner::class)
class UserByNameTest {

    /**
     * Learning test: Check teh result is exist
     */
    @Test
    fun simpleCode() {
        assertEquals(
            200,
            UserByName(
                "LarryHsiao",
                0
            ).value().code
        )
    }


    /**
     * Learning test: Check teh result body is exist
     */
    @Test
    fun simpleBody() {
        assertNotEquals(
            "",
            String(
                UserByName(
                    "LarryHsiao",
                    0
                ).value().bodyBytes
            )
        )
    }


    /**
     * Learning test: Check teh result if page is large then the result have
     *
     * It still returns the last page of search result.
     */
    @Test
    fun simplePage1() {
        assertNotEquals(
            "",
            String(
                UserByName(
                    "LarryHsiao",
                    100
                ).value().bodyBytes
            )
        )
    }
}