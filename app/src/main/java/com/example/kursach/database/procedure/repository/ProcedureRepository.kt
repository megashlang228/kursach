package com.example.kursach.database.procedure.repository

import androidx.room.*
import com.example.kursach.database.procedure.entities.ProcedureEntity
import kotlinx.coroutines.flow.Flow

interface ProcedureRepository {
    suspend fun createProcedure(procedureEntity: ProcedureEntity)

    suspend fun updateProcedure(procedureEntity: ProcedureEntity)

    suspend fun getAllProcedures(): Flow<List<ProcedureEntity>>

    suspend fun deleteProcedure(procedureEntity: ProcedureEntity)

    suspend fun getProcedureById(id: Int): Flow<ProcedureEntity>
}