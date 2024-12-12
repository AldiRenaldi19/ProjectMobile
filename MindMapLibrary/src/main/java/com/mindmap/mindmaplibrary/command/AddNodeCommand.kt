package com.mindmap.mindmaplibrary.command

import com.mindmap.mindmaplibrary.MindMapManager
import com.mindmap.mindmaplibrary.data.CircleNodeData
import com.mindmap.mindmaplibrary.data.RectangleNodeData
import com.mindmap.mindmaplibrary.util.Dp
import com.mindmap.mindmaplibrary.util.NodeGenerator


class AddNodeCommand(
    private val mindMapManager: MindMapManager,
    private val addNode: RectangleNodeData,
) : MindMapCommand {
    override fun execute() {
        val parent = mindMapManager.getTree().getNode(addNode.parentId)
        val parentX = parent.path.centerX
        val parentY = parent.path.centerY
        val width = when (parent) {
            is CircleNodeData -> parent.path.radius
            is RectangleNodeData -> parent.path.width
        }
        val updatedNode =  addNode.copy(
            path = addNode.path.copy(
                centerX = parentX + width + Dp(20f),
                centerY = parentY
            )
        )
        mindMapManager.addNode(updatedNode, addNode.description)
    }
}