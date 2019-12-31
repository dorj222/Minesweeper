package wabash.minesweeper.Model

data class Field(var hasRevealed : Boolean, var mine : Boolean, var minesAround : Int, var flagged : Boolean){}

