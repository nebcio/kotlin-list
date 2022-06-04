class Board {
    var board = mutableListOf<Char>()
    var empty = 9

    constructor() {
        board = mutableListOf<Char>('_','_','_',
                                    '_','_','_',
                                    '_','_','_' )
    }

    fun show() {
        for (i in 0 until 3) {
            for (j in 0 until 3) {
                print(board[i * 3 + j])
                print(" ") 
            }
            println("")
        }
        println("")
    }

    fun checkIndex(id: Int) : Boolean {
        if(board[id] != '_') return false
        return true
    }

    fun check() : Char {
        for (i in 0 until 3) {
            if (board[i] != '_' && board[i * 3] != '_') {
                if (board[i * 3] == board[i * 3 + 1] && board[i * 3 + 1] == board[i * 3 + 2]) return board[i * 3]   // hor
                if (board[i] == board[i + 3] && board[i + 3] == board[i + 6]) return board[i]   // ver
            }
        }
        if (board[4] != '_') {
            if (board[0] == board[4] && board[4] == board[8]) return board[0]
            if (board[2] == board[4] && board[4] == board[6]) return board[2]
        }
        return '_'
    }

    fun type(number: Int, letter: Char) {
        board[number] = letter
        --empty
        show()
    }
}


abstract class Player {
    var name : String
    var char : Char
    var board : Board

    constructor(name : String, char : Char, board: Board) {
        this.name = name
        this.char = char
        this.board = board
    }

    abstract fun move()
}


class HumanPlayer : Player {
    constructor(name : String, char : Char, board: Board) : super(name, char, board){ }

    override fun move() {
        var index = readLine()!!
        var index_taken = false
        while (!index_taken) {
            index_taken = true
            if (index.length > 1) {
                println("Type one digit.")
                index_taken = false
            }
            else if (!index.matches("[1-9]".toRegex())) {
                println("Wrong character.")
                index_taken = false
            }
            else if (!board.checkIndex(index.toInt() - 1)) {
                println("Wrong place.")
                index_taken = false
            }
            if (!index_taken) index = readLine()!!
        }
        board.type(index.toInt() - 1, this.char)
    }
}


class RandomPlayer : Player {

    var possibilities = mutableListOf(0, 1, 2, 3, 4, 5, 6, 7, 8)

    constructor(name : String, char : Char, board: Board) : super(name, char, board) {}

    override fun move() {
        var index : Int = 0 
        var id_set = false
        while (!id_set) {
            index = possibilities.random() 
            if (board.checkIndex(index)) id_set = true
            possibilities.remove(index)
        }
        println(index)
        board.type(index, this.char)
    }
}


class AIPlayer : Player {

    constructor(name : String, char : Char, board: Board) : super(name, char, board) {}

    override fun move() {

        var bestVal = if (this.char == 'x') -1000 else 1000
        var bestMove = -1
    
        for (i in 0 until 3) {	
            for (j in 0 until 3) { 
                if (board.board[i*3 + j] == '_') {
                    board.board[i*3 + j] = this.char
                    var moveVal = minimax(board.board, if(this.char == 'x') false else true)
                    board.board[i*3 + j] = '_'
                    if (this.char == 'x') {
                        if (moveVal > bestVal) {			
                            bestMove = i*3 + j
                            bestVal = moveVal
                        }
                    }
                    else {
                        if (moveVal < bestVal) {
                            bestMove = i*3 + j
                            bestVal = moveVal
                        }
                    }
                }
            }
        }
        println(bestMove)
        board.type(bestMove, this.char)
    }

    fun eval(node : MutableList<Char>) : Char {
        for (i in 0 until 3) {
            if (node[i] != '_') {
                if (node[i * 3] == node[i * 3 + 1] && node[i * 3 + 1] == node[i * 3 + 2]) return node[i * 3]   // hor
                if (node[i] == node[i + 3] && node[i + 3] == node[i + 6]) return node[i]   // ver
            }
        }
        if (node[4] != '_') {
            if (node[0] == node[4] && node[4] == node[8]) return node[0]
            if (node[2] == node[4] && node[4] == node[6]) return node[2]
        }
        return '_'
    }

    fun minimax(node : MutableList<Char>, maxPlayer : Boolean) : Int{
        return alfabeta(node, maxPlayer, -1000, 1000)
        
    /*
        var score = eval(node)
        if (score == 'x') return 10
        else if (score == 'o') return -10

        if (!node.contains('_')) return 0

        var best : Int
        if (maxPlayer) {
            best = -1000

            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    if (node[i*3 + j] == '_') {
                        node[i*3 + j] = 'x'
                        best = listOf(best, minimax(node, false)).maxOrNull()!!
                        node[i*3 + j] = '_'
                    }
                }
            }
            return best
        }
        else {
            best = 1000
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    if (node[i*3 + j] == '_') {
                        node[i*3 + j] = 'o'

                        best = listOf(best, minimax(node, true)).minOrNull()!!
                        node[i*3 + j] = '_'
                    }
                }
            }
            return best
        }
    */
    }

    fun alfabeta(node : MutableList<Char>, maxPlayer : Boolean, a : Int, b : Int) : Int {
        var score = eval(node)
        if (score == 'x') return 10
        else if (score == 'o') return -10

        if (!node.contains('_')) return 0

        var beta = b
        var alfa = a
        var best : Int

        if (maxPlayer) {
            best = -1000
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    if (node[i*3 + j] == '_') {
                        node[i*3 + j] = 'x'
                        best = listOf(best, alfabeta(node, false, alfa, beta)).maxOrNull()!!
                        alfa = listOf(best, alfa).maxOrNull()!!
                        node[i*3 + j] = '_'
                        if (alfa >= beta) break
                    }
                }
            }
            return alfa
        }
        else {
            best = 1000
            for (i in 0 until 3) {
                for (j in 0 until 3) {
                    if (node[i*3 + j] == '_') {
                        node[i*3 + j] = 'o'
                        best = listOf(best, alfabeta(node, true, alfa, beta)).minOrNull()!!
                        beta = listOf(beta, best).minOrNull()!!
                        node[i*3 + j] = '_'
                        if (alfa >= beta) break
                    }
                }
            }
            return beta
        }
    }
}


final class Game {
    var board : Board
    var playerOne : Player
    var playerTwo : Player

    constructor() {
        println("GRA TICTACTOE\n")
        this.board = Board()
        this.playerOne = getPlayerO()
        this.playerTwo = getPlayerT()        
        println(" 1 2 3\n 4 5 6\n 7 8 9")
        play()
    }
    
    fun play() {
        var ended = false
        var i = 0
        while (!ended) {
            if (i % 2 == 0) {
                print("Player One (${playerOne.char}): ")
                playerOne.move()
            }
            else {
                print("Player Two (${playerTwo.char}): ")
                playerTwo.move()
            }
            var ch = board.check()
            if (ch != '_') {
                ended = true
                if (ch == playerOne.char) {
                    println("First player (${playerOne.char}) won")
                }
                else if (ch == playerTwo.char) println("Second player (${playerTwo.char}) won")
            }
            ++i
            if (i == 9 && ended != true) {
                ended = true
                println("Nobody won. Game ended.")
            }
        }
    }

    fun getPlayerO() : Player {
        println("Choose type of first player: H - Human, R - Random, A - AI Player")
        print("Player: "); var player = readLine()!!.lowercase()
        var player_taken = false
        while (!player_taken) {
            player_taken = true
            if (!player.matches("[ahr]".toRegex())) {
                println("Wrong type of player.")
                player_taken = false
            }
            if (player.length > 1) {
                println("Use one letter.")
                player_taken = false
            }
            if (!player_taken) player = readLine()!!.lowercase()
        }

        println("Enter letter for player (X, O)")
        print("Your letter: "); var letter = readLine()!!.lowercase()
        var letter_taken = false
        while (!letter_taken) {
            letter_taken = true
            if (!letter.matches("[xo]".toRegex())) {
                println("Wrong letter.")
                letter_taken = false
            }
            if (letter.length > 1) {
                println("Use one letter.")
                letter_taken = false
            }
            if (!letter_taken) letter = readLine()!!.lowercase()
        }

        if (player == "h") return HumanPlayer("one", letter.toCharArray()[0], this.board)
        else if (player == "a") return AIPlayer("one", letter.toCharArray()[0], this.board)
        else return RandomPlayer("one", letter.toCharArray()[0], this.board)
    }

    fun getPlayerT() : Player {
        println("Choose type of second player: H - Human, R - Random, A - AI Player")
        print("Player: "); var player = readLine()!!.lowercase()
        var player_taken = false
        while (!player_taken) {
            player_taken = true
            if (!player.matches("[ahr]".toRegex())) {
                println("Wrong type of player.")
                player_taken = false
            }
            if (player.length > 1) {
                println("Use one letter.")
                player_taken = false
            }
            if (!player_taken) player = readLine()!!.lowercase()
        }
        
        var letter : Char
        if (playerOne.char == 'x') letter = 'o'
        else letter = 'x'

        if (player == "h") return HumanPlayer("two", letter, this.board)
        else if (player == "a") return AIPlayer("two", letter, this.board)
        else return RandomPlayer("two", letter, this.board)
    }
}


fun main() {
    Game()
}