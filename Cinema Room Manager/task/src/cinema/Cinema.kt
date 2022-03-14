package cinema

import java.util.*

const val SEAT = 'S'
const val NUMB = 60
const val PRICE = 10
const val REDUCED_PRICE = 8
const val HALF = 2
const val TICKET_PURCHASED = "That ticket has already been purchased!"
const val OUT_OF_BOUNDS = "Wrong input!"
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
    var n: Int
    do {
        println("1. Show the seats")
        println("2. Buy a ticket")
        println("3. Statistics")
        println("0. Exit")
        n = readLine()!!.toInt()
        when (n){
            1 -> cinema.printSeatingScheme()
            2 -> {
                var show: String
                do {
                    show = cinema.buyTicket()
                    println(show)
                } while (show == TICKET_PURCHASED || show == OUT_OF_BOUNDS)
            }
            3 -> println(cinema.statistics())
            0 -> break
        }
    } while(n != 0)
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
    private var ticketsPurchased = 0
    private var totalIncome = getTotalIncome()
    private var seatNumber = 0
    private var rowNumber = 0
    private var currentIncome = 0
    private var percentSold = "0.00"

    /**
     * printSeatingScheme - A method that prints out the seats in the cinema
     * It prints the scheme in a specific format
     * @return - Nothing / unit
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
     * assignSeat - It takes two arguments and check if a seat is booked, it returns false if it is booked
     * and returns true if it is not booked, it also increments the total tickets purchased by 1 if not booked
     * and marks the seat as booked. It also calculates the percentage of seats booked
     * @param yCor - This is the seat row number
     * @param xCor - This is the seat number
     * @return: Boolean
     */
    private fun assignSeat(yCor: Int, xCor: Int): Boolean {
        rowNumber = yCor - 1
        seatNumber = xCor - 1
        return if (cinemaScheme[rowNumber][seatNumber] == 'B') false
        else {
            ticketsPurchased++
            cinemaScheme[rowNumber][seatNumber] = 'B'
            percentSold = String.format(locale = Locale.ENGLISH, format =  "%.2f", ticketsPurchased * 100.0 / totalSeats)
            true
        }
    }

    /**
     * ticketPrice - It sets the ticket price based on the number of seats in the cinema
     * and on the row position if the seat.
     * It also updates currentIncome based on the ticket price
     * @return: ticketPrice
     */
    private fun getTicketPrice(): Int {
        return if (totalSeats <= NUMB) {
            currentIncome += PRICE
            PRICE
        } else {
            val frontHalf = (rows / HALF)
            if ((rowNumber + 1) in 0..frontHalf) {
                currentIncome += PRICE
                PRICE
            }
            else {
                currentIncome += REDUCED_PRICE
                REDUCED_PRICE
            }
        }
    }

    /**
     * getTotalIncome - Calculates total expected income based on the total number of seats
     * and if the seats are more than 60
     * @return Int - the total expected income
     */
    private fun getTotalIncome(): Int {
        return if (totalSeats <= NUMB) {
            PRICE * totalSeats
        } else {
            val frontHalf = (rows / HALF)
            val backHalf = rows - frontHalf
            (frontHalf * seats * PRICE) + (backHalf * seats * REDUCED_PRICE)
        }
    }

    /**
     * buyTicket - Takes an arg which is of type Cinema,
     * takes 2 inputs from the user that are row and seat numbers
     * it then assigns a seat based on the input and prints out ticket price
     * @return - Returns string to the user depending on if ticket purchase was successful or not
     */
    fun buyTicket(): String {
        println("Enter a row number:")
        val rowNumber = readLine()!!.toInt()
        println("Enter a seat number in that row:")
        val seatNumber = readLine()!!.toInt()
        return if (seatNumber in 1..seats && rowNumber in 1..rows) {
            if (assignSeat(rowNumber, seatNumber)) {
                "Ticket price: \$${getTicketPrice()}"
            } else TICKET_PURCHASED
        } else OUT_OF_BOUNDS
    }

    /**
     * -statistics - Prints out the current state of the cinema
     * @return String
     */
    fun statistics(): String {
        return "Number of purchased tickets: $ticketsPurchased\n" +
                "Percentage: $percentSold%\n" +
                "Current income: \$$currentIncome\n" +
                "Total income: \$$totalIncome"
    }
}