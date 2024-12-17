package com.mindmap.mindmaplibrary.layout

import android.content.Context
import android.graphics.Rect
import com.mindmap.mindmaplibrary.data.CircleNodeData
import com.mindmap.mindmaplibrary.data.NodeData
import com.mindmap.mindmaplibrary.data.RectangleNodeData
import com.mindmap.mindmaplibrary.data.Tree
import com.mindmap.mindmaplibrary.model.DrawInfo
import com.mindmap.mindmaplibrary.util.Dp
import com.mindmap.mindmaplibrary.util.Px
import com.mindmap.mindmaplibrary.util.toDp

class MeasureTextSize(private val context: Context) {
    private val drawInfo = DrawInfo(context)
    fun traverseTextHead(tree: Tree<*>?) {
        tree?.let {
            it.doPreorderTraversal { node ->
                val newNode =
                    changeSize(node, sumWidth(node.description), sumTotalHeight(node.description))
                tree.setNode(node.id, newNode)
            }
        }
    }
    private fun changeSize(
        nodeData: NodeData<*>,
        width: Float,
        height: Float,
    ): NodeData<*> = when (nodeData) {
        is CircleNodeData -> {
            val newRadius =
                Dp(
                    maxOf(
                        (Dp(Px(width).toDp(context) / 2).dpVal + drawInfo.padding.dpVal),
                        ((Dp(Px(height).toDp(context)).dpVal + drawInfo.padding.dpVal * 2) / 2),
                    ),
                )
            nodeData.copy(
                id = nodeData.id,
                parentId = nodeData.parentId,
                path = nodeData.path.copy(radius = newRadius),
                description = nodeData.description,
                children = nodeData.children,
            )
        }
        is RectangleNodeData -> {
            val newWidth = Dp(Dp(Px(width).toDp(context)).dpVal + drawInfo.padding.dpVal)
            val newHeight = Dp(Dp(Px(height).toDp(context)).dpVal + drawInfo.padding.dpVal)
            nodeData.copy(
                id = nodeData.id,
                parentId = nodeData.parentId,
                path = nodeData.path.copy(width = newWidth, height = newHeight),
                description = nodeData.description,
                children = nodeData.children,
            )
        }
    }

    private fun sumTotalHeight(description: String): Float {
        val bounds = Rect()
        var sum = 0f
        description.split("\n").forEach { line ->
            drawInfo.textPaint.getTextBounds(line, 0, line.length, bounds)
            sum += bounds.height() + drawInfo.lineHeight.dpVal
        }
        return sum
    }

    private fun sumWidth(description: String): Float {
        var sum = 0f
        description.split("\n").forEach {
            sum = maxOf(sum, drawInfo.textPaint.measureText(it))
        }
        return sum
    }
}