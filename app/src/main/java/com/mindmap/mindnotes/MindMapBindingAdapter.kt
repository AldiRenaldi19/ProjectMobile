package com.mindmap.mindnotes

import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.mindmap.mindnotes.model.CircleNode
import com.mindmap.mindnotes.model.RectangleNode
import org.w3c.dom.Node

@BindingAdapter("app:removeBtn")
fun ImageButton.setEnabled(selectedNode: Node?) {
    this.isEnabled =
        when (selectedNode) {
            is CircleNode -> false
            is RectangleNode -> true
            else -> false
        }
}