package com.mindmap.mindmaplibrary

import com.mindmap.mindmaplibrary.data.NodeData

interface NodeClickListener {
    fun onClickListener(node: NodeData<*>?)
}