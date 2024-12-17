package com.mindmap.mindnotes

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.mindmap.mindmaplibrary.MindMapManager
import com.mindmap.mindmaplibrary.NodeClickListener
import com.mindmap.mindmaplibrary.data.CircleNodeData
import com.mindmap.mindmaplibrary.data.NodeData
import com.mindmap.mindmaplibrary.data.RectangleNodeData
import com.mindmap.mindmaplibrary.data.Tree
import com.mindmap.mindnotes.databinding.ActivityMainBinding
import com.mindmap.mindnotes.model.CircleNode
import com.mindmap.mindnotes.model.CirclePath
import com.mindmap.mindnotes.model.Node
import com.mindmap.mindnotes.model.RectangleNode
import com.mindmap.mindnotes.model.RectanglePath

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MindMapViewModel>()
    private lateinit var manager: MindMapManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setBinding()
        init()
        setClickEvent()
    }
    private fun setBinding() {
        binding.vm = viewModel
    }

    private fun init() {
        val tree = Tree<Node>(this)
        binding.mindMapView.setTree(tree)
        binding.mindMapView.initialize()
        manager = binding.mindMapView.getMindMapManager()
    }

    private fun showDialog(
        operationType: String,
        selectedNode: Node,
    ) {
        val description = if (operationType == "add") "" else selectedNode.description
        val editDescriptionDialog = EditDescriptionDialog()
        editDescriptionDialog.setDescription(description)
        editDescriptionDialog.setSubmitListener { description ->
            when (operationType) {
                "add" -> {
                    binding.mindMapView.addNode(description)
                }

                "update" -> {
                    binding.mindMapView.editNodeText(description)
                }

                else -> return@setSubmitListener
            }
        }
        editDescriptionDialog.show(
            this.supportFragmentManager,
            "EditDescriptionDialog",
        )
    }

    private fun setClickEvent() {
        with(binding) {
            imgbtnMindMapAdd.setOnClickListener {
                viewModel.selectedNode.value?.let { selectNode ->
                    showDialog("add", selectNode)
                }
            }
            imgbtnMindMapEdit.setOnClickListener {
                viewModel.selectedNode.value?.let { selectNode ->
                    showDialog("update", selectNode)
                }
            }
            imgbtnMindMapRemove.setOnClickListener {
                viewModel.selectedNode.value?.let { selectNode ->
                    mindMapView.removeNode()
                }
            }

            imgbtnMindMapFit.setOnClickListener {
                mindMapView.fitScreen()
            }

            mindMapView.setNodeClickListener(object : NodeClickListener {
                override fun onClickListener(node: NodeData<*>?) {
                    val selectedNode = createNode(node)
                    viewModel.setSelectedNode(selectedNode)
                }
            })
        }
    }

    private fun createNode(node: NodeData<*>?): Node? {
        return when (node) {
            is CircleNodeData -> CircleNode(
                node.id,
                node.parentId,
                CirclePath(
                    Dp(node.path.centerX.dpVal),
                    Dp(node.path.centerY.dpVal),
                    Dp(node.path.radius.dpVal)
                ),
                node.description,
                node.children
            )

            is RectangleNodeData -> RectangleNode(
                node.id,
                node.parentId,
                RectanglePath(
                    Dp(node.path.centerX.dpVal),
                    Dp(node.path.centerY.dpVal),
                    Dp(node.path.width.dpVal),
                    Dp(node.path.height.dpVal)
                ),
                node.description,
                node.children
            )

            else -> null
        }
    }
}