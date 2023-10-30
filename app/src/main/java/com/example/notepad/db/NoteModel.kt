package com.example.notepad.db

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note")
data class NoteModel(
    val title: String?,
    val content: String?
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

//      Parcellable Constrcutor to deserialize a parcel to NoteModel
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
        id = parcel.readInt()
    }

//    Seializable method for conversion to parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int {
        return 0
    }

//Contains methods to construct NoteModel from Parcel
    companion object CREATOR : Parcelable.Creator<NoteModel> {
        override fun createFromParcel(parcel: Parcel): NoteModel {
            return NoteModel(parcel)
        }

        override fun newArray(size: Int): Array<NoteModel?> {
            return arrayOfNulls(size)
        }
    }
}