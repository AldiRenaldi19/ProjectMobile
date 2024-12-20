package com.mindmap.mindmaplibrary.node

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import com.mindmap.mindmaplibrary.data.RectangleNodeData
import com.mindmap.mindmaplibrary.model.DrawInfo
import com.mindmap.mindmaplibrary.util.Dp
import com.mindmap.mindmaplibrary.util.toPx

class RectangleNodeDrawer(
    private val node: RectangleNodeData,
    private val drawInfo: DrawInfo,
    private val context: Context,
    private val paint: Paint,
) : NodeDrawer {
    override fun drawNode(canvas: Canvas) {
        canvas.drawRoundRect(
            node.path.leftX().toPx(context),
            node.path.topY().toPx(context),
            node.path.rightX().toPx(context),
            node.path.bottomY().toPx(context),
            Dp(ROUNDED_CORNER_RADIUS).toPx(context),
            Dp(ROUNDED_CORNER_RADIUS).toPx(context),
            paint,
        )
    }

    override fun drawText(canvas: Canvas, lines: List<String>, fontType: Typeface) {
        val textPaint = drawInfo.textPaint.apply {
            color = Color.BLACK
            typeface = fontType
        }
        val bounds = Rect()
        if (lines.size > 1) {
            var y =
                node.path.centerY.toPx(context) - node.path.height.toPx(context) / 2 + drawInfo.padding.toPx(
                    context
                ) / 2
            for (line in lines) {
                textPaint.getTextBounds(line, 0, line.length, bounds)
                canvas.drawText(
                    line,
                    node.path.centerX.toPx(context),
                    y + bounds.height(),
                    textPaint.apply { alpha = paint.alpha },
                )
                y += bounds.height() + drawInfo.lineHeight.dpVal
            }
        } else {
            canvas.drawText(
                node.description,
                node.path.centerX.toPx(context),
                node.path.centerY.toPx(context) + drawInfo.lineHeight.dpVal / 2,
                textPaint,
            )
        }
    }

    companion object {
        private const val ROUNDED_CORNER_RADIUS = 8f
    }
}