package com.mindmap.mindnotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.view.LayoutInflater
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.view.WindowManager
import com.google.android.material.snackbar.Snackbar
import com.mindmap.mindnotes.model.MindMapNode
import com.mindmap.mindnotes.view.MindMapView
import com.mindmap.mindnotes.view.MindMapView.Companion.NODE_RADIUS
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : AppCompatActivity() {
    private lateinit var mindMapView: MindMapView
    private lateinit var fabAddNode: FloatingActionButton
    private lateinit var fabZoomIn: FloatingActionButton
    private lateinit var fabZoomOut: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mindMapView = findViewById(R.id.mindmap_view)
        fabAddNode = findViewById(R.id.fab_add_node)
        fabZoomIn = findViewById(R.id.fab_zoom_in)
        fabZoomOut = findViewById(R.id.fab_zoom_out)

        fabZoomIn.setOnClickListener {
            mindMapView.zoomIn()
        }

        fabZoomOut.setOnClickListener {
            mindMapView.zoomOut()
        }

        fabAddNode.setOnClickListener {
            showNodeDialog()
        }

        mindMapView.setOnNodeEditListener { node ->
            showNodeDialog(node)
        }

        mindMapView.setOnNodeAddListener { parentNode ->
            showSubnodeDialog(parentNode)
        }

        mindMapView.setOnNodeDeleteListener { node ->
            showDeleteConfirmation(node)
        }
    }

    private fun showNodeDialog(nodeToEdit: MindMapNode? = null) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_node, null)
        val input = dialogView.findViewById<TextInputEditText>(R.id.input_node_text)
        val selectedNode = if (nodeToEdit == null) mindMapView.getSelectedNode() else null

        // Pre-fill text jika dalam mode edit
        if (nodeToEdit != null) {
            input.setText(nodeToEdit.text)
        }

        val title = when {
            nodeToEdit != null -> "Edit Node"
            selectedNode != null -> "Add Sub Node"
            else -> "Add Node"
        }

        val dialog = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
            .setTitle(title)
            .setView(dialogView)
            .setPositiveButton(if (nodeToEdit != null) "Update" else "Add") { _, _ ->
                val text = input.text.toString().trim()
                if (text.isNotEmpty()) {
                    when {
                        nodeToEdit != null -> {
                            nodeToEdit.text = text
                            mindMapView.invalidate()
                            showSuccess("Node updated successfully")
                        }
                        selectedNode != null -> {
                            val offsetX = 300f
                            val offsetY = 200f
                            val newNode = mindMapView.addNode(
                                text,
                                selectedNode.x + offsetX,
                                selectedNode.y + offsetY
                            )
                            mindMapView.connectNodes(selectedNode.id, newNode.id)
                            showSuccess("Sub node added successfully")
                        }
                        else -> {
                            mindMapView.addNode(text, mindMapView.width / 2f, mindMapView.height / 2f)
                            showSuccess("Node added successfully")
                        }
                    }
                } else {
                    showError("Node text cannot be empty")
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
        input.requestFocus()
    }

    private fun showNodeOptionsDialog(node: MindMapNode) {
        MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
            .setTitle("Node Options")
            .setItems(arrayOf("Edit", "Add Subnode", "Delete")) { _, which ->
                when (which) {
                    0 -> showNodeDialog(node)
                    1 -> showSubnodeDialog(node)
                    2 -> showDeleteConfirmation(node)
                }
            }
            .show()
    }

    private fun showSubnodeDialog(parentNode: MindMapNode) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_node, null)
        val input = dialogView.findViewById<TextInputEditText>(R.id.input_node_text)

        val dialog = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
            .setTitle("Add Subnode")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val text = input.text.toString().trim()
                if (text.isNotEmpty()) {
                    // Hitung posisi subnode relatif terhadap parent
                    val angle = (Math.random() * 2 * Math.PI).toFloat()
                    val distance = NODE_RADIUS * 3
                    val offsetX = (cos(angle) * distance).toFloat()
                    val offsetY = (sin(angle) * distance).toFloat()

                    val newNode = mindMapView.addNode(
                        text,
                        parentNode.x + offsetX,
                        parentNode.y + offsetY
                    )
                    mindMapView.connectNodes(parentNode.id, newNode.id)
                    showSuccess("Subnode added successfully")
                } else {
                    showError("Node text cannot be empty")
                }
            }
            .setNegativeButton("Cancel", null)
            .create()

        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        dialog.show()
        input.requestFocus()
    }

    private fun showDeleteConfirmation(node: MindMapNode) {
        MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_Rounded)
            .setTitle("Delete Node")
            .setMessage("Are you sure you want to delete this node and its connections?")
            .setPositiveButton("Delete") { _, _ ->
                mindMapView.deleteNode(node)
                showSuccess("Node deleted successfully")
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showSuccess(message: String) {
        Snackbar.make(mindMapView, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(getColor(R.color.success_color))
            .show()
    }

    private fun showError(message: String) {
        Snackbar.make(mindMapView, message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(getColor(R.color.error_color))
            .show()
    }
}