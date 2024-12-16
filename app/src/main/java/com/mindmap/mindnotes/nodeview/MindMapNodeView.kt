package com.mindmap.mindnotes.nodeview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class MindMapNodeView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val radius = 20f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Mengatur warna dan style untuk node
        paint.color = Color.BLUE
        paint.style = Paint.Style.FILL

        // Menggambar lingkaran sebagai node
        val cx = width / 2f
        val cy = height / 2f
        val radius = 20f
        canvas.drawCircle(cx, cy, radius, paint)

        // Mengatur warna dan style untuk teks
        paint.color = Color.WHITE
        paint.textSize = 15f

        // Mengatur teks di tengah node
        val text = "Node Text"
        val textWidth = paint.measureText(text)
        val textX = cx - textWidth / 2
        val textY = cy + (paint.descent() + paint.ascent()) / 2
        canvas.drawText(text, textX, textY, paint)

        // Mengatur warna dan style untuk garis
        paint.color = Color.BLACK
        paint.strokeWidth = 2f
        paint.style = Paint.Style.STROKE

        // Menggambar garis yang menghubungkan node dengan parent
        parentNode?.let { parent ->
            val parentX = parent.x + parent.width / 2f
            val parentY = parent.y + parent.height / 2f

            // Menghitung sudut ke parent node
            var angleToParent = Math.atan2(
                (y - parentY).toDouble(),
                (x - parentX).toDouble()
            )
            val startX = cx + radius * Math.cos(angleToParent).toFloat()
            val startY = cy + radius * Math.sin(angleToParent).toFloat()
            val endX = parentX - parent.radius * Math.cos(angleToParent).toFloat()
            val endY = parentY - parent.radius * Math.sin(angleToParent).toFloat()
            canvas.drawLine(startX, startY, endX, endY, paint)
        }
    }

    private var parentNode: MindMapNodeView? = null

    // Metode untuk mendapatkan parent node
    private fun getParentNode(): MindMapNodeView? {
        return parentNode
    }
    fun setParentNode(parentNode: MindMapNodeView?) {
        this.parentNode = parentNode
    }

    private val childNodes = mutableListOf<MindMapNodeView>()
    fun getChildNodes(): List<MindMapNodeView> {
        return childNodes
    }

    fun setChildNodes(childNodes: List<MindMapNodeView>) {
        this.childNodes.clear()
        this.childNodes.addAll(childNodes)
    }

    fun addChildNode(childNode: MindMapNodeView) {
        this.childNodes.add(childNode)
        childNode.setParentNode(this)
    }

    fun removeChildNode(childNode: MindMapNodeView) {
        this.childNodes.remove(childNode)
    }
}