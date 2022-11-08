//package com.davidson.gamesdb.database
//
//import android.content.Context
//import android.provider.ContactsContract.Data
//import androidx.room.*
//
//@Dao
//interface GamesDao {
//    @Query("SELECT * FROM table_games_all")
//    suspend fun getAllGamesFromDb(): List<DatabaseGame>
//
//    @Query("SELECT * FROM table_games_all where gameName like '%' || :gameName || '%' LIMIT :limit OFFSET :offset")
//    suspend fun getAllGamesFromDbInPaged(gameName: String, limit:Int, offset:Int): List<DatabaseGame>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(strangers: List<DatabaseGame>)
//
//    @Query("DELETE FROM table_games_all")
//    fun deleteAll()
//}
//
//@Database(entities = [DatabaseGame::class], version = 1, exportSchema = false)
//abstract class GamesListDatabase : RoomDatabase() {
//    abstract val GamesDao: GamesDao
//}
//
//private lateinit var INSTANCE: GamesListDatabase
//
//fun getDatabase(context: Context): GamesListDatabase {
//    synchronized(GamesListDatabase::class.java) {
//        if (!::INSTANCE.isInitialized) {
//            INSTANCE = Room.databaseBuilder(
//                context.applicationContext,
//                GamesListDatabase::class.java,
//                "db_games"
//            ).build()
//        }
//    }
//    return INSTANCE
//}