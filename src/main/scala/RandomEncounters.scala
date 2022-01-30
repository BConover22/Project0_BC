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
  // Monster : monstertype m_power
  var monstername: String = ""
  var monsterpower: Int = 0
  var monsterId: Int = 0
  // Item
  var itemname: String = ""
  var itembonus: Int = 0
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
      createName = readLine("Name: ")
      if (nameIsUnique(connection, createName)) {
        flag = false
      }

    }
    println(createName)

    //Display Name and starting  stats (10) -> Saves (id + u_power)

// Gameplay
    // Either Item or Monster

    //Monster
    //match statement:
    /*
        combat: match statement
      case 1 : player > monster
      -- add bonus power
      case 2: monster < player
      -- -1
      case 3: monster == player
      -- add bonus power
    //Item
      // add Bonus to U_Power
     */
    // Repeat
// End
    // would you like to play again
    //quit
    //connection.close()
  }
//global functions

  def nameIsUnique(con: Connection, name: String): Boolean = {
    //HARDCODED
    true
    /*
    val stmt = connection.createStatement()
    val result = stmt.executeQuery(
      s"SELECT username FROM Users WHERE username = '$createName'"
    )
    result.next()
    if (result.getString("username") == name) {
      true
    } else {
      false
    }
     */
  }

}
