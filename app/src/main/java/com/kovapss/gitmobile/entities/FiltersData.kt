package com.kovapss.gitmobile.entities


class FiltersData (val query : String,  val order : String = ASC_ORDER) {
    constructor(query : String, sort : String, order : String = ASC_ORDER) : this(query, order)

    companion object {
        const val ASC_ORDER = "asc"
        const val DESC_ORDER = "desc"
        const val STARS_SORT = "stars"
        const val FORKS_SORT = "forks"
        const val UPDATED_SORT = "updated"
    }

}