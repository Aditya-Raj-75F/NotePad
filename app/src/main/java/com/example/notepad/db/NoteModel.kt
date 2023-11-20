package com.example.notepad.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note")
data class NoteModel(
    val title: String?,
    val content: String?,
    val noteColor: Int?
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}