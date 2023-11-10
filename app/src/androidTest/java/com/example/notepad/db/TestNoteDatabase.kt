package com.example.notepad.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NoteModel::class], version = 1)
abstract class TestNoteDatabase : RoomDatabase() {
    abstract fun getNotesDao() : NoteDao
}