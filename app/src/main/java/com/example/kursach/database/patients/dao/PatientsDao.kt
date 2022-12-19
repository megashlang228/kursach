package com.example.kursach.database.patients.dao

import androidx.room.*
import com.example.kursach.database.notes.entities.NoteEntity
import com.example.kursach.database.notes.entities.NoteUpdateEnabledTuple
import com.example.kursach.database.patients.entities.PatientEntity
import com.example.kursach.database.procedure.entities.ProcedureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun createPatient(patientEntity: PatientEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updatePatient(patientEntity: PatientEntity)

    @Query("SELECT * FROM patient")
    fun getAllPatients(): Flow<List<PatientEntity>>

    @Delete
    suspend fun deletePatient(patientEntity: PatientEntity)

    @Query("SELECT * FROM patient WHERE Id = :id")
    fun getPatientById(id: Int): Flow<PatientEntity>
}