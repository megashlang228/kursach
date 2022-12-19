package com.example.kursach.database.notes.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kursach.database.notes.entities.NoteEntity
import com.example.kursach.database.notes.entities.NoteUpdateEnabledTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createNote(noteEntity: NoteEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(noteEntity: NoteEntity)

    @Update(entity = NoteEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateEnabled(noteUpdateEnabledTuple: NoteUpdateEnabledTuple)

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note WHERE date = :dateNote")
    fun getNotesByDate(dateNote: String): Flow<List<NoteEntity>>

    @Query("SELECT * From note WHERE Id = :id")
    fun getNoteById(id:Int): Flow<NoteEntity>

    @Delete
    suspend fun deleteNote(noteEntity: NoteEntity)

}