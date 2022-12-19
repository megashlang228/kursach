package com.example.kursach.database.procedure.dao

import androidx.room.*
import com.example.kursach.database.notes.entities.NoteEntity
import com.example.kursach.database.notes.entities.NoteUpdateEnabledTuple
import com.example.kursach.database.procedure.entities.ProcedureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProcedureDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createProcedure(procedureEntity: ProcedureEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateProcedure(procedureEntity: ProcedureEntity)

    @Query("SELECT * FROM procedure")
    fun getAllProcedures(): Flow<List<ProcedureEntity>>

    @Delete
    suspend fun deleteProcedure(procedureEntity: ProcedureEntity)

    @Query("SELECT * FROM procedure WHERE Id = :id")
    fun getProcedureById(id: Int): Flow<ProcedureEntity>
}