package ie.setu.config

import org.jetbrains.exposed.sql.Database
import mu.KotlinLogging
import org.jetbrains.exposed.sql.name

/**
 * Configuration class for establishing a database connection to the PostgreSQL database.
 *
 * This class provides the necessary parameters and settings to connect to the database.
 */
class DbConfig{

    private val logger = KotlinLogging.logger {}

    /**
     * Establishes a connection to the PostgreSQL database and returns a [Database] instance.
     *
     * @return A [Database] instance representing the established database connection.
     */
    fun getDbConnection() :Database{

        logger.info{"Starting DB Connection..."}

        val PGHOST = "flora.db.elephantsql.com"
        val PGPORT = "5432"
        val PGUSER = "ofnpknxi"
        val PGPASSWORD = "IGakuJ2D6KxDuLqIoLdtUNJfm85i3yJ_"
        val PGDATABASE = "ofnpknxi"

        //url format should be jdbc:postgresql://host:port/database
        val dbUrl = "jdbc:postgresql://$PGHOST:$PGPORT/$PGDATABASE"

        val dbConfig = Database.connect(
            url = dbUrl,
            driver="org.postgresql.Driver",
            user = PGUSER,
            password = PGPASSWORD
        )

        logger.info{"DbConfig name = " + dbConfig.name}
        logger.info{"DbConfig url = " + dbConfig.url}

        return dbConfig
    }
}
