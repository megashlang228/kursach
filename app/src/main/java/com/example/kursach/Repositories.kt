package com.example.kursach

import android.content.Context
import androidx.room.Room
import com.example.kursach.database.NoteDatabase
import com.example.kursach.database.notes.repository.NoteRepository
import com.example.kursach.database.notes.repository.NoteRepositoryImpl
import com.example.kursach.database.patients.repository.PatientRepository
import com.example.kursach.database.patients.repository.PatientRepositoryImpl
import com.example.kursach.database.procedure.repository.ProcedureRepository
import com.example.kursach.database.procedure.repository.ProcedureRepositoryImpl

object Repositories {

    private lateinit var applicationContext: Context

    private val database: NoteDatabase by lazy<NoteDatabase> {
        Room.databaseBuilder(applicationContext, NoteDatabase::class.java, "db")
            .createFromAsset("initial_database.db")
            .build()
    }

    val noteRepository: NoteRepository by lazy {
        NoteRepositoryImpl(database.getNoteDao())
    }

    val procedureRepository: ProcedureRepository by lazy {
        ProcedureRepositoryImpl(database.getProcedureDao())
    }

    val patientRepository: PatientRepository by lazy {
        PatientRepositoryImpl(database.getPatientDao())
    }

    fun init(context: Context){
        applicationContext = context
    }
}