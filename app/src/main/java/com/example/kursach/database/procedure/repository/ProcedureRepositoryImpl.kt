package com.example.kursach.database.procedure.repository

import com.example.kursach.database.procedure.dao.ProcedureDao
import com.example.kursach.database.procedure.entities.ProcedureEntity
import kotlinx.coroutines.flow.Flow

class ProcedureRepositoryImpl(private val procedureDao: ProcedureDao) : ProcedureRepository {
    override suspend fun createProcedure(procedureEntity: ProcedureEntity) {
        return procedureDao.createProcedure(procedureEntity)
    }

    override suspend fun updateProcedure(procedureEntity: ProcedureEntity) {
        return procedureDao.updateProcedure(procedureEntity)
    }

    override suspend fun getAllProcedures(): Flow<List<ProcedureEntity>> {
        return procedureDao.getAllProcedures()
    }

    override suspend fun deleteProcedure(procedureEntity: ProcedureEntity) {
        return procedureDao.deleteProcedure(procedureEntity)
    }

    override suspend fun getProcedureById(id: Int): Flow<ProcedureEntity> {
        return procedureDao.getProcedureById(id)
    }
}