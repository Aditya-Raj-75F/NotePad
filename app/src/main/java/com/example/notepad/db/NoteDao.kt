package com.example.notepad.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

// Data Access Object for specifiying database operations permissible
@Dao
interface NoteDao {
    @Insert
    suspend fun addNote(note: NoteModel)
    @Query("SELECT * FROM note")
    fun getAllNotes(): LiveData<List<NoteModel>>
    @Update
    suspend fun updateNote(note: NoteModel)
    @Delete
    suspend fun deleteNote(note: NoteModel)
    @Query("SELECT * FROM note WHERE id = :noteId")
    suspend fun getNoteById(noteId: Int): NoteModel?
    @Query("DELETE FROM note WHERE id IN (:noteIds)")
    suspend fun deleteNotes(noteIds: List<Int>)
}