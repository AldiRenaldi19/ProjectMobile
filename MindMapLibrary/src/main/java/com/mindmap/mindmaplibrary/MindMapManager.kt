package com.mindmap.mindmaplibrary

import android.content.Context
import com.mindmap.mindmaplibrary.data.NodeData
import com.mindmap.mindmaplibrary.data.Tree
import com.mindmap.mindmaplibrary.layout.MeasureTextSize
import com.mindmap.mindmaplibrary.layout.MindMapRightLayoutManager
import com.mindmap.mindmaplibrary.util.Dp

class MindMapManager(context: Context) {
    private lateinit var tree: Tree<*>
    private var selectNode: NodeData<*>? = null
    private val rightLayoutManager = MindMapRightLayoutManager()
    private val measureTextSize: MeasureTextSize = MeasureTextSize(context)
    private var isMoving = false

    fun update(tree: Tree<*>) {
        this.tree = tree
        if (isMoving.not()) {
            measureTextSize.traverseTextHead(this.tree)
        }
    }

    fun update(target: NodeData<*>) {
        tree.updateNode(
            target.id,
            target.description,
            target.children,
            target.path.centerX,
            target.path.centerY
        )
        measureTextSize.traverseTextHead(tree)
    }

    fun addNode(node: NodeData<*>, description: String) {
        this.tree.addNode(node.id, node.parentId, description)
        update(node)
    }

    fun removeNode(node: NodeData<*>) {
        this.tree.removeNode(node.id)
    }

    fun setSelectedNode(node: NodeData<*>?) {
        this.selectNode = node
    }

    fun setMoving() {
        this.isMoving = true
    }

    fun setNotMoving() {
        this.isMoving = false
    }

    fun getMovingState(): Boolean {
        return this.isMoving
    }

    fun getSelectedNode(): NodeData<*>? {
        return this.selectNode
    }

    fun arrangeTree() {
        rightLayoutManager.arrangeNode(this.tree)
    }

    fun measureHeight(node: NodeData<*>): Dp {
        return rightLayoutManager.measureChildHeight(node, tree)
    }

    fun measureWidth(node: NodeData<*>): Dp {
        return rightLayoutManager.measureChildWidth(node, tree)
    }

    fun deepCopyTree(): Map<String, NodeData<*>> {
        return this.tree.nodes.entries.associate { node ->
            node.key to node.value
        }
    }

    fun setTree(tree: Tree<*>) {
        this.tree = tree
        measureTextSize.traverseTextHead(tree)
    }

    fun getTree(): Tree<*> {
        return this.tree
    }

}