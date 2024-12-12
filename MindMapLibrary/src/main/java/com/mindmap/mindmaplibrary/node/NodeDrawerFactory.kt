package com.mindmap.mindmaplibrary.node

import android.content.Context
import com.mindmap.mindmaplibrary.R
import com.mindmap.mindmaplibrary.data.CircleNodeData
import com.mindmap.mindmaplibrary.data.NodeData
import com.mindmap.mindmaplibrary.data.RectangleNodeData
import com.mindmap.mindmaplibrary.model.DrawInfo

class NodeDrawerFactory(
    private val node: NodeData<*>,
    private val context: Context,
    private val depth: Int = 0,
) {
    private val nodeColors = listOf(
        context.getColor(R.color.main3),
        context.getColor(R.color.mindmap2),
        context.getColor(R.color.mindmap3),
        context.getColor(R.color.mindmap4),
        context.getColor(R.color.mindmap5),
    )
    private val drawInfo = DrawInfo(context)

    fun createStrokeNode(): NodeDrawer {
        return when(node) {
            is RectangleNodeData -> {
                RectangleNodeDrawer(
                    node,
                    drawInfo,
                    context,
                    drawInfo.strokePaint,
                )
            }
            is CircleNodeData -> {
                CircleNodeDrawer(
                    node,
                    drawInfo,
                    context,
                    drawInfo.strokePaint,
                )
            }
        }
    }

    fun createNodeDrawer(): NodeDrawer {
        return when (node) {
            is RectangleNodeData -> {
                val paint = drawInfo.rectanglePaint.apply {
                    color = nodeColors[(depth - 1) % nodeColors.size]
                }
                RectangleNodeDrawer(
                    node,
                    drawInfo,
                    context,
                    paint,
                )
            }

            is CircleNodeData -> {
                CircleNodeDrawer(
                    node,
                    drawInfo,
                    context,
                    drawInfo.circlePaint,
                )
            }
        }
    }
}