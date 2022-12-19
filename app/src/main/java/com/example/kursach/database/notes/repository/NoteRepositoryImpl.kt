package com.example.kursach.database.notes.repository

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import com.example.kursach.database.notes.dao.NotesDao
import com.example.kursach.database.notes.entities.NoteEntity
import com.example.kursach.database.notes.entities.NoteUpdateEnabledTuple
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(private val noteDao: NotesDao): NoteRepository {

    override suspend fun getNotes(): Flow<List<NoteEntity>> {
        return  noteDao.getAllNotes()
    }

    override suspend fun getNotesByDate(date: String): Flow<List<NoteEntity>> {
        return  noteDao.getNotesByDate(date)
    }

    override suspend fun createNote(noteEntity: NoteEntity) {
        noteDao.createNote(noteEntity)
    }

    override suspend fun updateNote(noteEntity: NoteEntity) {
        noteDao.update(noteEntity)
    }

    override suspend fun updateEnabledNote(noteUpdateEnabledTuple: NoteUpdateEnabledTuple) {
        noteDao.updateEnabled(noteUpdateEnabledTuple)
    }

    override suspend fun getNoteById(id: Int): Flow<NoteEntity> {
        return noteDao.getNoteById(id)
    }

    override suspend fun deleteNote(noteEntity: NoteEntity) {
        return noteDao.deleteNote(noteEntity)
    }
}