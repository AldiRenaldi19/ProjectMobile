package com.mindmap.mindnotes


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mindmap.mindnotes.nodeview.MindMapView


class MainActivity : AppCompatActivity() {

    private lateinit var mindMapView: MindMapView
    private lateinit var btnAddNode: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mindMapView = findViewById(R.id.mindMapView)
        btnAddNode = findViewById(R.id.btnAddNode)

        btnAddNode.setOnClickListener {
            mindMapView.addNode("Node ${mindMapView.nodeCount + 1}")
        }
        mindMapView.setOnClickListener {
            // Menangani klik pada node untuk mengedit

            mindMapView.editNode(mindMapView.nodes.last())
        }
    }
}