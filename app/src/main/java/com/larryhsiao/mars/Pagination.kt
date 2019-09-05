package com.larryhsiao.mars

/**
 * The Pagination model.
 */
interface Pagination <T>{
    /**
     * Load up first page, this method will change the internal page record to 0
     */
    fun firstPage(): List<T>

    /**
     * Load up nextPage of object, this method will get next page of the objects.
     * If there it have reached the end of list, returns empty list.
     */
    fun newPage(): List<T>
}