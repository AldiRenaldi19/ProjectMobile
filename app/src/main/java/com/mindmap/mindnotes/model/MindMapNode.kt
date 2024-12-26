package com.mindmap.mindnotes.model

import android.graphics.Color
import java.util.UUID

data class MindMapNode(
    val id: String = UUID.randomUUID().toString(),
    var text: String,
    var x: Float,
    var y: Float,
    var width: Float = DEFAULT_WIDTH,  // Lebar oval
    var height: Float = DEFAULT_HEIGHT, // Tinggi oval
    var connections: MutableList<Connection> = mutableListOf()
) {
    companion object {
        const val DEFAULT_WIDTH = 240f
        const val DEFAULT_HEIGHT = 160f
        const val MIN_WIDTH = 160f
        const val MIN_HEIGHT = 120f
    }
}

data class Connection(
    val fromNode: String,  // ID node asal
    val toNode: String,    // ID node tujuan
    var lineColor: Int = Color.BLACK
)