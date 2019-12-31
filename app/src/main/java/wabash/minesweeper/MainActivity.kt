package wabash.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import wabash.minesweeper.MineSweeper.View.MineSweeperView
import wabash.minesweeper.Model.MineSweeperModel
import wabash.minesweeper.Model.MineSweeperModel.modeFLAG
import wabash.minesweeper.Model.MineSweeperModel.modeTRY


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnReset.setOnClickListener {
            mineSwView.clearGame()
        }

        swFlag.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                MineSweeperModel.mode = modeFLAG
            } else {
                MineSweeperModel.mode = modeTRY
            }
        }
    }

    fun displaySnackbar(txt: String) {
        Snackbar.make(mineSwView, txt, Snackbar.LENGTH_SHORT).show()
    }

}

