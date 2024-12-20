package com.mindmap.mindnotes

import androidx.lifecycle.ViewModel
import com.mindmap.mindnotes.model.Node
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MindMapViewModel: ViewModel() {
    private var _selectedNode = MutableStateFlow<Node?>(null)
    val selectedNode: StateFlow<Node?> = _selectedNode

    fun setSelectedNode(selectNode: Node?) {
        _selectedNode.value = selectNode
    }
}