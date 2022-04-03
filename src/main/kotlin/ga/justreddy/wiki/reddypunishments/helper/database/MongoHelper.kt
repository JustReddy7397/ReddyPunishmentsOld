package ga.justreddy.wiki.reddypunishments.helper.database

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document

lateinit var mongoHelper: MongoHelper

class MongoHelper(uri: String) {

    private val database: MongoDatabase

    init {
        mongoHelper = this
        val connectionString = MongoClientURI(uri)
        val mongoClient = MongoClient(connectionString)
        database = mongoClient.getDatabase("reddypunishments")
    }

    fun getDatabase(collectionName: String): MongoCollection<Document> {
        return database.getCollection(collectionName)
    }

}