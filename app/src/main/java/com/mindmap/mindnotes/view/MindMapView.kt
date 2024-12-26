package com.mindmap.mindnotes.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import android.graphics.RectF
import android.graphics.PointF
import com.mindmap.mindnotes.model.Connection
import com.mindmap.mindnotes.model.MindMapNode

class MindMapView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val nodes = mutableListOf<MindMapNode>()
    private var selectedNode: MindMapNode? = null
    private var isDragging = false
    private var dragStartX = 0f
    private var dragStartY = 0f

    private val nodePaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
        isAntiAlias = true
        setShadowLayer(8f, 0f, 4f, Color.parseColor("#20000000"))
    }

    private val nodeStrokePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = Color.parseColor("#2196F3")
        isAntiAlias = true
    }

    private val selectedNodePaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 4f
        color = Color.parseColor("#FF4081")
        isAntiAlias = true
        pathEffect = DashPathEffect(floatArrayOf(15f, 15f), 0f)
    }

    private val textPaint = Paint().apply {
        color = Color.parseColor("#212121")
        textSize = 40f
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
    }

    private val actionButtonPaint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val actionIconPaint = Paint().apply {
        color = Color.WHITE
        textSize = 40f
        typeface = Typeface.DEFAULT_BOLD
        textAlign = Paint.Align.CENTER
    }

    private fun drawNodeActions(canvas: Canvas, node: MindMapNode) {
        if (node == selectedNode) {
            val buttonRadius = 30f
            val spacing = 20f
            val startX = node.x + node.width/2 + spacing
            val centerY = node.y

            // Tombol Edit (Biru)
            actionButtonPaint.color = Color.parseColor("#2196F3")
            canvas.drawCircle(startX + buttonRadius, centerY - (buttonRadius * 2 + spacing), buttonRadius, actionButtonPaint)
            canvas.drawText("✎", startX + buttonRadius, centerY - (buttonRadius * 2 + spacing) + 15f, actionIconPaint)

            // Tombol Add (Hijau)
            actionButtonPaint.color = Color.parseColor("#4CAF50")
            canvas.drawCircle(startX + buttonRadius, centerY, buttonRadius, actionButtonPaint)
            canvas.drawText("+", startX + buttonRadius, centerY + 15f, actionIconPaint)

            // Tombol Delete (Merah)
            actionButtonPaint.color = Color.parseColor("#F44336")
            canvas.drawCircle(startX + buttonRadius, centerY + (buttonRadius * 2 + spacing), buttonRadius, actionButtonPaint)
            canvas.drawText("×", startX + buttonRadius, centerY + (buttonRadius * 2 + spacing) + 15f, actionIconPaint)
        }
    }

    private fun checkActionButtonClick(x: Float, y: Float): String? {
        if (selectedNode == null) return null

        val buttonRadius = 30f
        val spacing = 20f
        val startX = selectedNode!!.x + selectedNode!!.width/2 + spacing
        val centerY = selectedNode!!.y

        // Cek tombol Edit
        if (isPointInCircle(x, y, startX + buttonRadius, centerY - (buttonRadius * 2 + spacing), buttonRadius)) {
            return "edit"
        }
        // Cek tombol Add
        if (isPointInCircle(x, y, startX + buttonRadius, centerY, buttonRadius)) {
            return "add"
        }
        // Cek tombol Delete
        if (isPointInCircle(x, y, startX + buttonRadius, centerY + (buttonRadius * 2 + spacing), buttonRadius)) {
            return "delete"
        }
        return null
    }

    private fun isPointInCircle(x: Float, y: Float, centerX: Float, centerY: Float, radius: Float): Boolean {
        val dx = x - centerX
        val dy = y - centerY
        return dx * dx + dy * dy <= radius * radius
    }

    private var scaleFactor = 1f
    private val minScale = 0.5f
    private val maxScale = 2.0f

    fun zoomIn() {
        scaleFactor = minOf(scaleFactor * 1.2f, maxScale)
        invalidate()
    }

    fun zoomOut() {
        scaleFactor = maxOf(scaleFactor / 1.2f, minScale)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.save()
        canvas.scale(scaleFactor, scaleFactor, width / 2f, height / 2f)

        // Gambar koneksi
        nodes.forEach { node ->
            node.connections.forEach { connection ->
                val fromNode = nodes.find { it.id == connection.fromNode }
                val toNode = nodes.find { it.id == connection.toNode }
                if (fromNode != null && toNode != null) {
                    drawConnection(canvas, fromNode, toNode)
                }
            }
        }

        // Gambar nodes
        nodes.forEach { node ->
            drawNode(canvas, node)
            drawNodeActions(canvas, node)
        }

        canvas.restore()
    }

    private fun drawNode(canvas: Canvas, node: MindMapNode) {
        // Gambar oval dengan bayangan
        val rect = RectF(
            node.x - node.width/2,
            node.y - node.height/2,
            node.x + node.width/2,
            node.y + node.height/2
        )

        // Gambar bayangan node
        canvas.drawOval(rect, nodePaint)

        // Gambar outline node
        if (node == selectedNode) {
            nodeStrokePaint.setShadowLayer(12f, 0f, 0f, Color.parseColor("#802196F3"))
            canvas.drawOval(rect, selectedNodePaint)
        } else {
            nodeStrokePaint.clearShadowLayer()
        }
        canvas.drawOval(rect, nodeStrokePaint)

        // Gambar teks dengan wrapping
        val lines = wrapText(node.text, node.width * 0.8f, textPaint)
        val lineHeight = textPaint.fontSpacing
        val totalHeight = lineHeight * lines.size
        var yPos = node.y - (totalHeight / 2) + (lineHeight / 2)

        lines.forEach { line ->
            canvas.drawText(line, node.x, yPos, textPaint)
            yPos += lineHeight
        }
    }

    private fun wrapText(text: String, maxWidth: Float, paint: Paint): List<String> {
        val words = text.split(" ")
        val lines = mutableListOf<String>()
        var currentLine = StringBuilder()

        words.forEach { word ->
            val testLine = if (currentLine.isEmpty()) word else "$currentLine $word"
            val measureWidth = paint.measureText(testLine)

            if (measureWidth <= maxWidth) {
                currentLine.append(if (currentLine.isEmpty()) word else " $word")
            } else {
                if (currentLine.isNotEmpty()) {
                    lines.add(currentLine.toString())
                    currentLine = StringBuilder(word)
                } else {
                    // Jika kata terlalu panjang, potong saja
                    lines.add(word)
                }
            }
        }

        if (currentLine.isNotEmpty()) {
            lines.add(currentLine.toString())
        }

        return lines
    }

    private val bezierPath = Path()
    private val connectionPaint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.parseColor("#546E7A")
        isAntiAlias = true
        // Efek bayangan untuk garis
        setShadowLayer(4f, 0f, 2f, Color.parseColor("#20000000"))
    }

    private fun drawConnection(canvas: Canvas, fromNode: MindMapNode, toNode: MindMapNode) {
        // Hitung titik-titik kontrol untuk kurva Bezier
        val startPoint = getIntersectionPoint(fromNode, toNode)
        val endPoint = getIntersectionPoint(toNode, fromNode)

        val controlX = (startPoint.x + endPoint.x) / 2
        val controlY = (startPoint.y + endPoint.y) / 2
        val offsetY = (endPoint.y - startPoint.y) * 0.2f

        bezierPath.reset()
        bezierPath.moveTo(startPoint.x, startPoint.y)
        bezierPath.quadTo(controlX, controlY - offsetY, endPoint.x, endPoint.y)

        canvas.drawPath(bezierPath, connectionPaint)
        drawArrow(canvas, endPoint.x, endPoint.y, controlX, controlY - offsetY)
    }

    private fun drawArrow(canvas: Canvas, tipX: Float, tipY: Float, tailX: Float, tailY: Float) {
        val angle = atan2((tipY - tailY).toDouble(), (tipX - tailX).toDouble())
        val arrowPath = Path()

        // Buat panah yang lebih modern
        arrowPath.moveTo(tipX, tipY)
        arrowPath.lineTo(
            tipX - (ARROW_SIZE * cos(angle - ARROW_ANGLE)).toFloat(),
            tipY - (ARROW_SIZE * sin(angle - ARROW_ANGLE)).toFloat()
        )
        arrowPath.lineTo(
            tipX - (ARROW_SIZE * cos(angle + ARROW_ANGLE)).toFloat(),
            tipY - (ARROW_SIZE * sin(angle + ARROW_ANGLE)).toFloat()
        )
        arrowPath.close()

        canvas.drawPath(arrowPath, Paint().apply {
            style = Paint.Style.FILL
            color = connectionPaint.color
            isAntiAlias = true
        })
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        // Konversi koordinat touch dengan mempertimbangkan zoom dan posisi canvas center
        val centerX = width / 2f
        val centerY = height / 2f

        // Hitung offset dari titik tengah canvas
        val offsetX = event.x - centerX
        val offsetY = event.y - centerY

        // Konversi koordinat dengan mempertimbangkan skala dan offset
        val scaledX = centerX + (offsetX / scaleFactor)
        val scaledY = centerY + (offsetY / scaleFactor)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Cek apakah klik pada tombol aksi
                val action = checkActionButtonClick(scaledX, scaledY)
                if (action != null && selectedNode != null) {
                    when (action) {
                        "edit" -> onNodeEditListener?.invoke(selectedNode!!)
                        "add" -> onNodeAddListener?.invoke(selectedNode!!)
                        "delete" -> onNodeDeleteListener?.invoke(selectedNode!!)
                    }
                    return true
                }

                // Cek untuk drag node
                val touchedNode = findNodeAtPoint(scaledX, scaledY)
                if (touchedNode != null) {
                    selectedNode = touchedNode
                    isDragging = true
                    dragStartX = scaledX
                    dragStartY = scaledY
                    invalidate()
                    return true
                } else {
                    selectedNode = null
                    invalidate()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (isDragging && selectedNode != null) {
                    val deltaX = scaledX - dragStartX
                    val deltaY = scaledY - dragStartY
                    selectedNode?.x = selectedNode?.x!! + deltaX
                    selectedNode?.y = selectedNode?.y!! + deltaY
                    dragStartX = scaledX
                    dragStartY = scaledY
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                isDragging = false
            }
        }
        return true
    }

    private fun findNodeAtPoint(x: Float, y: Float): MindMapNode? {
        for (node in nodes.asReversed()) {
            // Hitung jarak dari titik ke pusat node dengan mempertimbangkan skala
            val dx = (x - node.x) / (node.width/2)
            val dy = (y - node.y) / (node.height/2)
            // Gunakan persamaan elips yang lebih toleran saat zoom out
            val tolerance = if (scaleFactor <= 0.7f) 1.5f else 1f
            if (dx * dx + dy * dy <= tolerance) {
                return node
            }
        }
        return null
    }

    fun addNode(text: String, x: Float, y: Float): MindMapNode {
        val newNode = MindMapNode(text = text, x = x, y = y)
        adjustNodeSize(newNode, text)
        nodes.add(newNode)
        invalidate()
        return newNode
    }

    fun connectNodes(fromId: String, toId: String) {
        val fromNode = nodes.find { it.id == fromId }
        fromNode?.connections?.add(Connection(fromId, toId))
        invalidate()
    }

    // Mengembalikan node yang sedang dipilih
    fun getSelectedNode(): MindMapNode? {
        return selectedNode
    }

    fun deleteNode(node: MindMapNode) {
        // Hapus semua koneksi yang terhubung ke node ini
        nodes.forEach { n ->
            n.connections.removeAll { it.fromNode == node.id || it.toNode == node.id }
        }
        // Hapus node
        nodes.remove(node)
        selectedNode = null
        invalidate()
    }

    // Fungsi untuk menyesuaikan ukuran node berdasarkan teks
    private fun adjustNodeSize(node: MindMapNode, text: String) {
        val textWidth = textPaint.measureText(text)
        val lines = wrapText(text, MindMapNode.DEFAULT_WIDTH * 0.8f, textPaint)
        val textHeight = textPaint.fontSpacing * lines.size

        // Sesuaikan ukuran node
        node.width = maxOf(MindMapNode.MIN_WIDTH, textWidth * 1.4f)
        node.height = maxOf(MindMapNode.MIN_HEIGHT, textHeight * 1.8f)
    }

    // Fungsi untuk mendapatkan titik perpotongan garis dengan oval
    private fun getIntersectionPoint(node1: MindMapNode, node2: MindMapNode): PointF {
        val angle = atan2(node2.y - node1.y, node2.x - node1.x)
        val cos = cos(angle)
        val sin = sin(angle)

        // Hitung titik perpotongan dengan oval
        val rx = node1.width / 2
        val ry = node1.height / 2
        val px = rx * cos
        val py = ry * sin

        return PointF(
            node1.x + px,
            node1.y + py
        )
    }

    companion object {
        const val NODE_RADIUS = 120f // Sedikit lebih besar untuk tampilan yang lebih baik
        const val ARROW_SIZE = 20f
        const val ARROW_ANGLE = Math.PI / 6
    }

    private var onNodeEditListener: ((MindMapNode) -> Unit)? = null
    private var onNodeAddListener: ((MindMapNode) -> Unit)? = null
    private var onNodeDeleteListener: ((MindMapNode) -> Unit)? = null

    fun setOnNodeEditListener(listener: (MindMapNode) -> Unit) {
        onNodeEditListener = listener
    }

    fun setOnNodeAddListener(listener: (MindMapNode) -> Unit) {
        onNodeAddListener = listener
    }

    fun setOnNodeDeleteListener(listener: (MindMapNode) -> Unit) {
        onNodeDeleteListener = listener
    }
}