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
            /*
             MySQL.con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS BannedPlayers
             (NAME VARCHAR(100),UUID VARCHAR(100),END VARCHAR(100),REASON VARCHAR(100),BANNER VARCHAR(100) )");
*/
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:${plugin.dataFolder.absolutePath}/data/database.db");
            update("CREATE TABLE IF NOT EXISTS bans (UUID VARCHAR(100)," +
                    " MODERATOR VARCHAR(100)," +
                    " NAME VARCHAR(100)," +
                    " END VARCHAR(100)," +
                    " REASON VARCHAR(100)," +
                    "DATE LONG(100)," +
                    "ACTIVE VARCHAR(100))")
            update("CREATE TABLE IF NOT EXISTS mutes (UUID VARCHAR(100)," +
                    " MODERATOR VARCHAR(100)," +
                    " NAME VARCHAR(100)," +
                    " END VARCHAR(100)," +
                    "REASON VARCHAR(100)," +
                    "DATE LONG(100)," +
                    "ACTIVE VARCHAR(100))")
        } catch (ex: SQLException) {
            Utils.error(ex, "There was an error connecting to the H2 database", true)
        } catch (ex: ClassNotFoundException) {
            Utils.error(ex, "There was an error connecting to the H2 database", true)
        }

    }

    fun connectMySQL(host: String?, username: String?, password: String?, database: String?, port: Int?) {

        try {
            connection = DriverManager.getConnection("jdbc:mysql://$host:$port/$database", username, password);
            update("CREATE TABLE IF NOT EXISTS bans (UUID VARCHAR(100)," +
                    " MODERATOR VARCHAR(100)," +
                    " NAME VARCHAR(100)," +
                    " END VARCHAR(100)," +
                    " REASON VARCHAR(100)," +
                    " DATE LONG(100)," +
                    " ACTIVE VARCHAR(100))")
            update("CREATE TABLE IF NOT EXISTS mutes (UUID VARCHAR(100)," +
                    " MODERATOR VARCHAR(100)," +
                    " NAME VARCHAR(100)," +
                    " END VARCHAR(100)," +
                    " REASON VARCHAR(100)," +
                    " DATE LONG(100)," +
                    " ACTIVE VARCHAR(100))")
        } catch (ex: SQLException) {
            Utils.error(ex, "There was an error connecting to the MySQL database", true)
        }

    }

    fun isConnected(): Boolean {
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

    fun getConnection(): Connection {
        return connection
    }

}