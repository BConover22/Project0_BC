import java.sql.{Connection, DriverManager}
import scala.io.StdIn.readLine

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
      if (nameIsUnique(connection, createName)) {
        flag = false
      } else {
        println("That username is already taken")
      }
    }
    // Did it escape:
    // println(createName)

    //Finish creating character (INSERT ROW)
    createUser(connection, createName)
    updatePower()

    //Display Name and starting  stats (10) -> Saves (id + u_power)
    println(s"Hello, " + playerName + "your starting power is: " + playerPower)

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

  def nameIsUnique(con: Connection, name: String): Boolean = {
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
  def createUser(con: Connection, name: String): Unit = {
    val stmt = connection.createStatement()
    val insert1 = s"INSERT INTO users VALUES (0,'$name');"
    stmt.executeUpdate(insert1)
    val getId = stmt.executeQuery(s"SELECT u_id FROM users WHERE username = '$name';")
    getId.next()
    val newId = getId.getInt("u_id")
    val insert2 = s"INSERT INTO saves VALUES (10, $newId);"
    stmt.executeUpdate(insert2)
  }



}



// FIXME for fixing purposes