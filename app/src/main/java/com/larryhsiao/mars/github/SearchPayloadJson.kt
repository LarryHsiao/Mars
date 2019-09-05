package com.larryhsiao.mars.github

/**
 * Payload object of Github search api
 */
class SearchPayloadJson {
    /**
     * Total count of this search
     */
    var total_count: Int = 0

    /**
     * The user item objects.
     */
    var items: Array<UserJson> = arrayOf()
}