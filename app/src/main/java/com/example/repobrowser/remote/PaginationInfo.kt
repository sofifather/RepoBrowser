package com.example.repobrowser.remote

class PaginationInfo {
    var next: String? = null
    var last: String? = null
    var first: String? = null
    var prev: String? = null

    companion object{
        fun createFromHeaderValue(headerValue: String): PaginationInfo {
            val pagInfo = PaginationInfo()
            headerValue.split(",").forEach { linkWithRelationString ->
                linkWithRelationString.split(";").also { linkRelPair->
                    when(linkRelPair.last().trim()) {
                        "rel=\"next\"" -> pagInfo.next = linkRelPair.first().trim().let { it.substring(1, it.length-1) }
                        "rel=\"last\"" -> pagInfo.last = linkRelPair.first().trim().let { it.substring(1, it.length-1) }
                        "rel=\"first\"" -> pagInfo.first = linkRelPair.first().trim().let { it.substring(1, it.length-1) }
                        "rel=\"prev\"" -> pagInfo.prev = linkRelPair.first().trim().let { it.substring(1, it.length-1) }
                    }
                }
            }
            return pagInfo
        }
    }
}
