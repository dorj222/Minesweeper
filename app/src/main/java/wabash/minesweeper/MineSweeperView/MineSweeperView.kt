package wabash.minesweeper.MineSweeper.View

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.view.drawToBitmap
import wabash.minesweeper.MainActivity
import wabash.minesweeper.Model.MineSweeperModel
import wabash.minesweeper.R
import java.util.jar.Attributes


class MineSweeperView(context: Context?, attributes: AttributeSet?) : View(context, attributes) {

    var paintBackground: Paint = Paint()
    var paintSquare: Paint = Paint()
    var paintLine: Paint = Paint()
    val paintText2 = Paint()
    val paintText3 = Paint()
    val paintText4 = Paint()
    var colsRows = MineSweeperModel.sizeRow


    init {
        paintBackground.color = Color.parseColor("#00b7cc")
        paintBackground.style = Paint.Style.FILL
        paintSquare.color = Color.parseColor("#00ffff")
        paintLine.color = Color.BLACK
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 10f
        paintText2.color = Color.BLACK
        paintText2.textSize = 80F
        paintText3.color = Color.BLACK
        paintText3.textSize = 80F
        paintText4.color = Color.BLACK
        paintText4.textSize = 60F
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        drawBoard(canvas)
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        paintText2.textSize = (height - 350).toFloat() / (colsRows)
        paintText3.textSize = (height - 350).toFloat() / (colsRows)
        paintText4.textSize = (height - 100).toFloat() / (colsRows)

    }

    fun drawBoard(canvas: Canvas?) {
        for (i in 0 until colsRows) {
            for (j in 0 until colsRows) {
                drawSquares(i, j, canvas)
                drawFlags(i, j, canvas)
                MineSweeperModel.mineCounter(i, j)
            }
        }

        // border
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        // # of  horizontal lines
        for (x in 1..colsRows) canvas?.drawLine(
            0f,
            (x * height / colsRows).toFloat(),
            width.toFloat(),
            (x * height / colsRows).toFloat(),
            paintLine
        )
        // # of vertical lines
        for (x in 1..colsRows) canvas?.drawLine(
            (x * width / colsRows).toFloat(),
            0f,
            (x * width / colsRows).toFloat(),
            height.toFloat(),
            paintLine
        )

    }


    private fun drawSquares(i: Int, j: Int, canvas: Canvas?) {

        val sWidth = width.toFloat() / colsRows
        val sHeight = height.toFloat() / colsRows
        val padding = (0.1 * sWidth).toFloat()
        val padding2 = (0.3 * sHeight).toFloat()
        val padding3 = (0.17 * sHeight).toFloat()
        val padding4 = (0.24 * sWidth).toFloat()


        if (MineSweeperModel.getHasRevealed(i, j) == true) {


            if (MineSweeperModel.getHasMine(i, j) == true) {
                canvas?.drawText(
                    "\uD83D\uDCA3",
                    i * sWidth + padding,
                    (j + 1) * sHeight - padding2,
                    paintText2
                )
                (context as MainActivity).displaySnackbar("Game Over!")
            } else if (MineSweeperModel.getHasMine(i, j) == false) {
                canvas?.drawRect(
                    i * sWidth,
                    j * sHeight,
                    (i + 1) * sWidth,
                    (j + 1) * sHeight,
                    paintSquare
                )
                canvas?.drawText(
                    MineSweeperModel.getMinesAround(i, j),
                    i * sWidth + padding4,
                    (j + 1) * sHeight - padding3,
                    paintText4
                )
                if (MineSweeperModel.getVictory()) (context as MainActivity).displaySnackbar("Victory!")
            }
        }
    }

    private fun drawFlags(i: Int, j: Int, canvas: Canvas?) {

        val sWidth = width.toFloat() / colsRows
        val sHeight = height.toFloat() / colsRows
        val sWidth2 = (0.1 * sWidth).toFloat()
        val sHeight2 = (0.3 * sHeight).toFloat()

        if (MineSweeperModel.getHasFlagged(i, j) == true) {

            canvas?.drawRect(
                i * sWidth,
                j * sHeight,
                (i + 1) * sWidth,
                (j + 1) * sHeight,
                paintSquare
            )
            canvas?.drawText(
                "\uD83D\uDEA9",
                i * sWidth + sWidth2,
                (j + 1) * sHeight - sHeight2,
                paintText3
            )
            if (!(MineSweeperModel.getHasMine(i, j))) {
                (context as MainActivity).displaySnackbar("Game Over!")
            }
            if (MineSweeperModel.getVictory()) (context as MainActivity).displaySnackbar("Victory!")
        }

    }


    override fun onTouchEvent(event: MotionEvent): Boolean {

        val tX = event.x.toInt() / (width / colsRows)
        val tY = event.y.toInt() / (height / colsRows)

        if ((tX < colsRows) && (tY < colsRows)) {
            MineSweeperModel.modifySquare(tX, tY)
        }

        invalidate()
        return super.onTouchEvent(event)
    }

    fun clearGame() {
        MineSweeperModel.resetModel()
        invalidate()
    }
}






