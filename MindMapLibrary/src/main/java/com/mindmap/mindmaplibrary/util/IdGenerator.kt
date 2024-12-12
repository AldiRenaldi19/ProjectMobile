package com.mindmap.mindmaplibrary.util

import java.util.UUID

object IdGenerator {
    fun makeRandomNodeId() = UUID.randomUUID().toString()
}