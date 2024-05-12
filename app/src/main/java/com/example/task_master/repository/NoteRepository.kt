package com.example.task_master.repository

import android.util.Log
import com.example.task_master.database.NoteDatabase
import com.example.task_master.model.Note

// managing task data
class NoteRepository(private val db: NoteDatabase) {

    // Insert a task in the database
    suspend fun insertNote(note: Note) {
        db.getNoteDao().insertNote(note)
    }

    // Delete a dask from the database
    suspend fun deleteNote(note: Note) = db.getNoteDao().deleteNote(note)

    // Update a task in the database
    suspend fun updateNote(note: Note) = db.getNoteDao().updateNote(note)

    // Get all task from the database
    fun getAllNotes() = db.getNoteDao().getAllNotes()

    // Search notes in the database
    fun searchNote(query: String?) = db.getNoteDao().searchNote(query)
}
