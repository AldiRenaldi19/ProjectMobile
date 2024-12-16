package com.mindmap.mindnotes.nodeview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.PopupWindow
import com.mindmap.mindnotes.nodedata.Node

class MindMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // Implementasikan tampilan dan logika untuk MindMapNodeView di sini
    private val paint = Paint()
    val nodes = mutableListOf<Node>()
    private val connections = mutableListOf<Pair<Node, Node>>()
    val nodeCount: Int
        get() = nodes.size

    init {
        paint.color = Color.BLACK
        paint.textSize = 50f
    }

    fun addNode(text: String) {
        val newNode = Node(text, width / 2f, height / 2f + (nodes.size * 100))
        nodes.add(newNode)

        if (nodes.size > 1) {
            connections.add(Pair(nodes[nodes.size - 2], newNode))
        }

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Gambar garis antar node
        for (connection in connections) {
            val startX = connection.first.x
            val startY = connection.first.y
            val endX = connection.second.x
            val endY = connection.second.y
            canvas.drawLine(startX, startY, endX, endY, paint)
        }

        // Gambar node
        for (node in nodes) {
            canvas.drawText(node.text, node.x, node.y, paint)
        }
    }

    fun editNode(node: Node) {
        val editText = EditText(context)
        editText.setText(node.text)
        val popupWindow = PopupWindow(editText, 400, 200)
        popupWindow.isFocusable = true
        popupWindow.showAtLocation(this, 0,(node.x - 200).toInt(), (node.y - 100).toInt())

        editText.setOnEditorActionListener { _, _, _ ->
            node.text = editText.text.toString()
            popupWindow.dismiss()
            invalidate()
            true
        }
    }
}