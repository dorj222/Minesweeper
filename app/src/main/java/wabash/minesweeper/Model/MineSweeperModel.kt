package wabash.minesweeper.Model


object MineSweeperModel {

    var sizeRow = 7
    var sizeCol = sizeRow
    var sizeMines = sizeRow - 2
    var fieldMatrix = newField()
    val modeTRY = true
    val modeFLAG = false
    var mode = modeTRY

    fun newField(): Array<Array<Field>> {
        val matrix = Array(sizeRow) {
            Array(sizeCol) { Field(false, false, 0, false) }
        }

        // Each row will have exactly one mine except the last two rows.
        // So, there will be no collision, as a result of two setting up two mines in one square.
        val temp = IntArray(sizeCol-2)

        for (i in 0 until sizeCol-2) {

            temp[i] = (0..sizeRow-1).random()

            matrix[i][temp[i]].mine = true
        }


        return matrix
    }


    //If the game is over, shows everything to the player.
    fun showEverything() {
        for (i in 0 until sizeCol) {
            for (j in 0 until sizeRow) {
                if (fieldMatrix[i][j].mine) {
                    fieldMatrix[i][j].hasRevealed = true
                }
            }
        }
    }

    //Recursively opens nearby squares which do not have a mine.
    fun showNearAreas(x: Int, y: Int) {


        var k = 3
        if (((x >= k && y >= k) && (fieldMatrix[x - 1][y - 1]).mine == false)) {
            modifySquare(x - 1, y - 1)
        }

        if (((x >= k && y >= k) && fieldMatrix[x - 1][y].mine == false)) {
            modifySquare(x - 1, y)
        }

        if (((x >= k && y >= k) && (fieldMatrix[x][y - 1]).mine == false)) {
            modifySquare(x, y - 1)

        }

    }


    fun getHasMine(x: Int, y: Int): Boolean = fieldMatrix[x][y].mine

    fun getHasRevealed(x: Int, y: Int): Boolean = fieldMatrix[x][y].hasRevealed

    fun getHasFlagged(x: Int, y: Int): Boolean = fieldMatrix[x][y].flagged

    //If there is no square with a mine surrounding the "X" square, then return empty square.
    //Otherwise, just return "temp".
    fun getMinesAround(x: Int, y: Int): String {
        var temp = ""
        if (fieldMatrix[x][y].minesAround.toString() == "0") {
            temp = ""
        } else if (fieldMatrix[x][y].minesAround.toString() != "0") {
            temp = fieldMatrix[x][y].minesAround.toString()
        }
        return temp
    }


    fun getVictory(): Boolean {
        var temp = sizeMines
        var temp2 = sizeRow * sizeRow - sizeMines

        var result = false
        for (i in 0 until sizeCol) {
            for (j in 0 until sizeRow) {
                if (fieldMatrix[i][j].flagged == true) {
                    temp -= 1
                }
                if (fieldMatrix[i][j].hasRevealed == true) {
                    temp2 -= 1
                    if (temp2 == 0 && temp == 0) {
                        result = true
                    }
                }
            }
        }
        return result
    }


    fun modifySquare(x: Int, y: Int) {
        when (mode) {

            modeTRY -> {
                fieldMatrix[x][y].hasRevealed = true

                if (fieldMatrix[x][y].mine == false) {
                    showNearAreas(x, y)
                } else if (fieldMatrix[x][y].mine == true) {
                    showEverything()
                }
            }

            modeFLAG -> {
                fieldMatrix[x][y].flagged = true

                if (fieldMatrix[x][y].mine == false) {
                    showEverything()
                }
            }
        }
    }


    fun mineCounter(i: Int, j: Int) {
        var temp = 0

        if (i + 1 < sizeCol && j + 1 < sizeRow && fieldMatrix[i + 1][j + 1].mine) temp += 1
        if (i - 1 >= 0 && j - 1 >= 0 && fieldMatrix[i - 1][j - 1].mine) temp += 1
        if (i - 1 >= 0 && fieldMatrix[i - 1][j].mine) temp += 1
        if (j + 1 < sizeRow && fieldMatrix[i][j + 1].mine) temp += 1
        if (i + 1 < sizeCol && j - 1 >= 0 && fieldMatrix[i + 1][j - 1].mine) temp += 1
        if (i + 1 < sizeCol && fieldMatrix[i + 1][j].mine) temp += 1
        if (i - 1 >= 0 && j + 1 < sizeRow && fieldMatrix[i - 1][j + 1].mine) temp += 1
        if (j - 1 >= 0 && fieldMatrix[i][j - 1].mine) temp += 1


        fieldMatrix[i][j].minesAround = temp

    }

    fun resetModel() {
        fieldMatrix = newField()
    }
}
