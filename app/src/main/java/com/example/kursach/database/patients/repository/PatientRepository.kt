package com.example.kursach.database.patients.repository

import androidx.room.*
import com.example.kursach.database.patients.entities.PatientEntity
import com.example.kursach.database.procedure.entities.ProcedureEntity
import kotlinx.coroutines.flow.Flow

interface PatientRepository {
    suspend fun createPatient(patientEntity: PatientEntity)

    suspend fun updatePatient(patientEntity: PatientEntity)

    suspend fun getAllPatients(): Flow<List<PatientEntity>>

    suspend fun deletePatient(patientEntity: PatientEntity)

    suspend fun getPatientById(id: Int): Flow<PatientEntity>
}