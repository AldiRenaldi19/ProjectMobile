package com.mindmap.mindmaplibrary.command

import com.mindmap.mindmaplibrary.MindMapManager
import com.mindmap.mindmaplibrary.data.CircleNodeData
import com.mindmap.mindmaplibrary.data.NodeData

class RemoveNodeCommand(
    private val mindMapManager: MindMapManager,
    private val node: NodeData<*>,
) : MindMapCommand {
    override fun execute() {
        if (node is CircleNodeData) return
        mindMapManager.removeNode(node)
        mindMapManager.setSelectedNode(null)
    }
}