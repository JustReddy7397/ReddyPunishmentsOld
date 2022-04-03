package ga.justreddy.wiki.reddypunishments.helper.database

import ga.justreddy.wiki.reddypunishments.plugin
import ga.justreddy.wiki.reddypunishments.utils.Utils
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.PreparedStatement

import java.sql.ResultSet




private lateinit var connection: Connection

lateinit var databaseHelper: DatabaseHelper

class DatabaseHelper {

    init {
        databaseHelper = this
    }

    fun connectH2() {

        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:${plugin.dataFolder.absolutePath}/data/database.db");
        }catch (ex: SQLException) {
            Utils.error(ex, "There was an error connecting to the H2 database", true)
        }catch (ex: ClassNotFoundException){
            Utils.error(ex, "There was an error connecting to the H2 database", true)
        }

    }

    fun connectMySQL(host: String?, username: String?, password: String?, database: String?, port: Int?) {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://$host:$port/$database", username, password);
        }catch (ex: SQLException) {
            Utils.error(ex, "There was an error connecting to the MySQL database", true)
        }

    }

    private fun isConnected(): Boolean {
        return connection != null
    }


    fun update(qry: String?) {
        try {
            getConnection().createStatement().executeUpdate(qry)
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
    }


    fun getResult(qry: String?): ResultSet? {
        try {
            return getConnection().createStatement().executeQuery(qry)
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return null
    }


    fun prepareStatement(qry: String?): PreparedStatement? {
        try {
            getConnection().prepareStatement(qry)
        } catch (ex: SQLException) {
            ex.printStackTrace()
        }
        return null
    }

    fun closeConnection() {
        if (isConnected()) {
            try {
                connection.close()
            } catch (ex: SQLException) {
                ex.printStackTrace()
            }
        }
    }

    companion object {
        fun getConnection() : Connection {
            return connection
        }

    }


}