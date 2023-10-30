package com.example.notepad.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(note: NoteModel)
    @Query("SELECT * FROM note")
    suspend fun getAllNotes(): List<NoteModel>
    @Update
    suspend fun updateNote(note: NoteModel)
    @Delete
    suspend fun deleteNote(note: NoteModel)
    @Query("SELECT * FROM note WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): NoteModel?
}