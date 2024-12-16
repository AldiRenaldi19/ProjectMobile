package com.mindmap.mindnotes.nodedata

data class Node(
    var text: String,
    var x: Float,
    var y: Float,
    val children: MutableList<Node> = mutableListOf()
)