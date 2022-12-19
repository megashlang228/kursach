package com.example.kursach.database.patients.repository

import com.example.kursach.database.patients.dao.PatientsDao
import com.example.kursach.database.patients.entities.PatientEntity
import kotlinx.coroutines.flow.Flow

class PatientRepositoryImpl(private val patientsDao: PatientsDao) : PatientRepository {

    override suspend fun createPatient(patientEntity: PatientEntity) {
        return patientsDao.createPatient(patientEntity)
    }

    override suspend fun updatePatient(patientEntity: PatientEntity) {
        return patientsDao.updatePatient(patientEntity)
    }

    override suspend fun getAllPatients(): Flow<List<PatientEntity>> {
        return patientsDao.getAllPatients()
    }

    override suspend fun deletePatient(patientEntity: PatientEntity) {
        return patientsDao.deletePatient(patientEntity)
    }

    override suspend fun getPatientById(id: Int): Flow<PatientEntity> {
        return patientsDao.getPatientById(id)
    }
}