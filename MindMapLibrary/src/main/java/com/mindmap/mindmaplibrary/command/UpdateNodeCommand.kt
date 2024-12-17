package com.mindmap.mindmaplibrary.command

import com.mindmap.mindmaplibrary.MindMapManager
import com.mindmap.mindmaplibrary.data.CircleNodeData
import com.mindmap.mindmaplibrary.data.NodeData
import com.mindmap.mindmaplibrary.data.RectangleNodeData

class UpdateNodeCommand(
    private val mindMapManager: MindMapManager,
    private val node: NodeData<*>,
    private val description: String,
) : MindMapCommand {
    override fun execute() {
        val selectedNode = when (node) {
            is CircleNodeData -> node.copy(description = description)
            is RectangleNodeData -> node.copy(description = description)
        }
        mindMapManager.update(selectedNode)
    }
}