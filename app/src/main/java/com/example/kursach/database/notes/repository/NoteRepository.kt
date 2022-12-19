package com.example.kursach.database.notes.repository

import androidx.lifecycle.LiveData
import com.example.kursach.database.notes.entities.NoteEntity
import com.example.kursach.database.notes.entities.NoteUpdateEnabledTuple
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    suspend fun getNotes(): Flow<List<NoteEntity>>

    suspend fun getNotesByDate(date: String): Flow<List<NoteEntity>>

    suspend fun createNote(noteEntity: NoteEntity)

    suspend fun updateNote(noteEntity: NoteEntity)

    suspend fun updateEnabledNote(noteUpdateEnabledTuple: NoteUpdateEnabledTuple)

    suspend fun getNoteById(id: Int): Flow<NoteEntity>

    suspend fun deleteNote(noteEntity: NoteEntity)
}