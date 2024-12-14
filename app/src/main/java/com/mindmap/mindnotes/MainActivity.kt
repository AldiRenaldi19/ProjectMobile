package com.mindmap.mindnotes


import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private val mindMapNodes = mutableListOf<MindMapNode>()
    private lateinit var adapter: MindMapAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MindMapAdapter(mindMapNodes) { node ->
            deleteNode(node)
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewMindMap)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val buttonAddNode: Button = findViewById(R.id.buttonAddNode)
        buttonAddNode.setOnClickListener {
            val editTextNode: EditText = findViewById(R.id.editTextNode)
            val nodeText = editTextNode.text.toString()
            if (nodeText.isNotEmpty()) {
                addNode(nodeText)
                editTextNode.text.clear()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }



    private fun addNode(text: String) {
        val newNode = MindMapNode(text = text)
        mindMapNodes.add(newNode)
        adapter.notifyItemInserted(mindMapNodes.size - 1)
    }

    private fun deleteNode(node: MindMapNode) {
        val position = mindMapNodes.indexOf(node)
        if (position >= 0) {
            mindMapNodes.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
    }

    override fun onResume() {
        super.onResume()
    }
}