import java.sql.{Connection, DriverManager}
import scala.io.StdIn.{readLine, readChar}


object RandomEncounters {
  //CONNECTION TO MYSQL
// connect to the database on localhost
  val url = "jdbc:mysql://localhost:3306/projectzero"
  val driver = "com.mysql.cj.jdbc.Driver"
  val username = "root"
  val password = "root"
  var connection: Connection =
    DriverManager.getConnection(url, username, password)
// Global variables
  // Player : FROM users | username ||  u_power
  var playerName: String = ""
  var playerPower: Int = 0
  var playerId: Int = 0
  // Monster : m_type m_power
  var monsterName: String = ""
  var monsterPower: Int = 0
  var monsterId: Int = 0
  // Item
  var itemName: String = ""
  var itemBonus: Int = 0
  var itemId: Int = 0

// main
  def main(args: Array[String]): Unit = {
    val stmt = connection.createStatement()
// Opening
    // Welcome
    println(
      "Welcome to Random Encounters!"
    )
    // Create Character
    var createName: String = ""
    //check name is unique
    var flag: Boolean = true
    while (flag) {
      //USER INPUT: Name: -> users (username + userID)
      println("Please enter a new username")
      createName = readLine("Name: ")
      if (nameIsUnique(createName)) {
        flag = false
      } else {
        println("That username is already taken")
      }
    }
    // Did it escape:
    createUser(createName)
    playerName = createName

    val getU_Id = stmt.executeQuery(s"SELECT u_id FROM users WHERE username = '$playerName'")
    getU_Id.next()
    playerId = getU_Id.getInt("u_id")

    val getU_Power = stmt.executeQuery(s"SELECT u_power FROM saves WHERE u_id = $playerId")
    getU_Power.next()
    playerPower = getU_Power.getInt("u_power")
    //Finish creating character (INSERT ROW)
    updatePower()

    //Display Name and starting  stats (10) -> Saves (id + u_power)

    println(s"Hello, " + playerName + " your starting power is: " + playerPower)

    var playing = true
    while(playing){
      println("Would you like to continue?")
      println("Type '1' for Yes | Type '2' for No")
      //MAIN MENU
      choose123(2) match {
        case 1 =>

        case 2 =>
          // QUIT MENU
          println("Would you like to Quit or Quit-and-Remove-Record")
          println("Type '1' for Quit | Type '2' for Quit-and-Remove-Record")
          choose123(2) match {
            case 1 =>
              println("Thank you for playing!")
              sys.exit(0)
            case 2 =>
              println("Record Removed. Thank you for playing!")
              removeUser()
          }
      }

    }
// Gameplay
    // Either Monster or Item

    val randomizer = scala.util.Random
    if (randomizer.nextInt(2) == 0) {
      //Monster
        // make random int , grab monster w/ primary key (starts at 1, so use max index and add 1)
      //match statement:
      /*
      (playerPower).compare(monsterPower) ??
                combat: match statement
      match {
        case playerPower > monsterPower =>
          -- add bonus power
          updatePower()
        case playerPower < monsterPower =>
          -- -1 playerPower
          updatePower()
        case monster == player =>
          (player wins)

           }

        */
    } else {

      //Item
       // add Bonus to U_Power

    }

    // Repeat
// End
    /*
    var isAlive: Boolean = true
    while (isAlive) {
      //Play
    }
     */
    // would you like to play again
    //quit


    //connection.close()
  }
//global functions

  def nameIsUnique(name: String): Boolean = {
    //HARDCODED
    //true
    val stmt = connection.createStatement()
    val result = stmt.executeQuery(
      s"SELECT count(username) FROM users WHERE username = '$name'"
    )
    result.next()
      if (result.getInt(1)!= 0) {
        false
      } else {
        true
      }
  }

  //createPower func??
  def createPower(): Unit = {
    // TODO : update Power in db
  }
  //Update power func
  def updatePower(): Unit = {
  // TODO : update Power in db
  }
  def createUser(name: String): Unit = {
    val stmt = connection.createStatement()
    val insert1 = s"INSERT INTO users VALUES (0,'$name');"
    stmt.executeUpdate(insert1)
    val getId = stmt.executeQuery(s"SELECT u_id FROM users WHERE username = '$name';")
    getId.next()
    val newId = getId.getInt("u_id")
    val insert2 = s"INSERT INTO saves VALUES (10, $newId);"
    stmt.executeUpdate(insert2)
  }

  def removeUser(): Unit = {
    val stmt = connection.createStatement()
    //create new save
    stmt.executeUpdate(s"DELETE FROM saves WHERE u_id = $playerId;")
    stmt.executeUpdate(s"DELETE FROM users WHERE username = '$playerName';")
    sys.exit(0)
  }

  //menu
  def choose123(n: Byte): Byte ={
    var input: Char = readChar()
    var inByte: Byte = 0
    var goodIn: Boolean = false

    n match {
      case 1 =>
        if (input == '1') {
          goodIn = true; inByte =  1.toByte
        } else {
          print("Sorry, but you have to choose '1', or '2': "); input = readChar()
        }
      case 2 =>
        while (!goodIn){
          input match {
            case '1'  => goodIn = true; inByte = 1.toByte
            case '2'  => goodIn = true; inByte = 2.toByte
            case _ => print("Sorry, but you have to choose '1', or '2': "); input = readChar()
          }
        }
      case 3 =>
        while (!goodIn){
          input match {
            case '1'  => goodIn = true; inByte = 1.toByte
            case '2'  => goodIn = true; inByte = 2.toByte
            case '3'  => goodIn = true; inByte = 3.toByte
            case _ => print("Sorry, but you have to choose '1', '2', or '3': "); input = readChar()
          }
        }
    }
    inByte
  }


}



// FIXME for fixing purposes