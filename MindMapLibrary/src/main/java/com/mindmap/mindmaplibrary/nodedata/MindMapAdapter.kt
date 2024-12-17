package com.mindmap.mindmaplibrary.nodedata

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mindmap.mindmaplibrary.R

class MindMapAdapter(
    private val nodes: MutableList<Node>,
    private val onDelete: (Node) -> Unit
) : RecyclerView.Adapter<MindMapAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textViewNode)
        val buttonDelete: Button = itemView.findViewById(R.id.buttonDeleteNode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mind_map_node, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val node = nodes[position]
        holder.textView.text = node.text
        holder.buttonDelete.setOnClickListener {
            onDelete(node)
        }
    }

    override fun getItemCount(): Int {
        return nodes.size
    }
}