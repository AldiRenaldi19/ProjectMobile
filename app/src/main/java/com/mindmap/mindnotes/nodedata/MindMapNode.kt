package com.mindmap.mindnotes.nodedata

data class MindMapNode(
    val id: String = java.util.UUID.randomUUID().toString(),
    var text: String,
    val children: MutableList<MindMapNode> = mutableListOf()
)