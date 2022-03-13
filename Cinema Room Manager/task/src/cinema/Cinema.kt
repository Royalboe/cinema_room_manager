package cinema

const val SEAT = 'S'
const val NUMB = 60
const val PRICE = 10
const val REDUCED_PRICE = 8
const val HALF = 2
fun main() {
    // Ask user for the number of rows in the cinema
    println("Enter the number of rows:")
    // Receive the user's input
    val rows = readLine()!!.toInt()
    // Ask user for the number of columns
    println("Enter the number of seats in each row:")
    // Receive user's input
    val columns = readLine()!!.toInt()
    // Initialize a Cinema class with user's Input
    val cinema = Cinema(rows, columns)

    // print out to the console the cinema scheme
    cinema.printSeatingScheme()

    println("Enter a row number:")
    val rowNumber = readln().toInt()

    println("Enter a seat number in that row:")
    val seatNumber = readln().toInt()
    cinema.assignSeat(rowNumber, seatNumber)

    // Print out the ticket price to the console
    println("Ticket price: \$${cinema.ticketPrice()}")

    // print out the new cinema scheme
    cinema.printSeatingScheme()
}

/**
 * A cinema class to instantiate a cinema object.
 * The class states are: cinemaScheme - A 2-D array representing the seating arrangement of the cinema
 * seatNumber, rowNumber, ticketPrice
 * The class methods are: printSeatingScheme, assignSeat and ticketPrice
 */
class Cinema(private val rows: Int, private val seats: Int) {
    private val cinemaScheme = MutableList(rows) { MutableList(seats) { SEAT } }

    private val totalSeats = rows * seats
    //private var totalPrice = 0
    private var seatNumber = 0
    private var rowNumber = 0
    private var ticketPrice = 0

    /**
     * printSeatingScheme - A method that prints out the seats in the cinema
     * It prints the scheme in a specific format
     * Return: Void
     */
    fun printSeatingScheme() {
        println("Cinema:")
        print(" ")
        for (i in 1..seats){
            if (i == seats){
                println(" $i")
                break
            }
            print(" $i")
        }
        cinemaScheme.forEachIndexed { index, list ->
            println("${index + 1} ${list.joinToString(" ")}")
        }
    }

    /**
     * assignSeat - marks a seat as occupied, and it takes two arguments
     * @param yCor - This is the seat row number
     * @param xCor - This is the seat number
     * Return: Void
     */
    fun assignSeat(yCor: Int, xCor: Int) {
        rowNumber = yCor - 1
        seatNumber = xCor - 1
        cinemaScheme[rowNumber][seatNumber] = 'B'
    }

    /**
     * ticketPrice - It sets the ticket price based on the number of seats in the cinema
     * and on the row position if the seat.
     * @return: ticketPrice
     */
    fun ticketPrice(): Int {
        ticketPrice = if (totalSeats <= NUMB) {
            PRICE
        } else {
            val frontHalf = (rows / HALF)
            //val backHalf = rows - frontHalf
            if ((rowNumber + 1) in 0..frontHalf) PRICE
            else REDUCED_PRICE
        }
        return ticketPrice
    }
}