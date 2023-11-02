package com.example.notepad.db

import android.content.Context
import android.widget.Toast
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [NoteModel::class],          // list of tables/entities to be introduced into database
    version = 1                             // current version of the database
)
// Abstract class acting as entry point to the database. Room provides implementation for the abstraction.
abstract class NoteDatabase : RoomDatabase() {
//    Abstract method to be implemented by Room to retrieve a Data Access Object of Note
    abstract fun getNotesDao(): NoteDao
// singleton patern ensuring that there is only instance of DB throughout the application
    companion object {
//        readily available instance of database to all threads
        @Volatile private var instance : NoteDatabase? = null
//    An object to support synchronization and avoid building of multiple database
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
//            Build database and store it in instance variable
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,                 // context of global application object with lifecycle separate from current context
            NoteDatabase::class.java,                   // Database reference
            "notedatabase"                        // name of the database
        ).fallbackToDestructiveMigration().build()      // builds corresponding database
    }
}