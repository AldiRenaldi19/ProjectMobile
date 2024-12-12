package com.mindmap.mindmaplibrary


import com.mindmap.mindmaplibrary.data.NodeData
import com.mindmap.mindmaplibrary.data.Tree

interface NodeMoveListener {
    fun onMoveListener(
        tree: Tree<*>,
        target: NodeData<*>,
        parent: NodeData<*>,
    )
}