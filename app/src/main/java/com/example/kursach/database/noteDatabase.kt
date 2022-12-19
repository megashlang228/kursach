package com.example.kursach.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kursach.database.notes.dao.NotesDao
import com.example.kursach.database.notes.entities.NoteEntity
import com.example.kursach.database.patients.dao.PatientsDao
import com.example.kursach.database.procedure.dao.ProcedureDao
import com.example.kursach.database.patients.entities.PatientEntity
import com.example.kursach.database.procedure.entities.ProcedureEntity

@Database(entities = [NoteEntity::class
                     , PatientEntity::class
                     , ProcedureEntity::class], version = 3,autoMigrations = [
    AutoMigration (from = 2, to = 3)
])
abstract class NoteDatabase: RoomDatabase() {
    abstract fun getNoteDao(): NotesDao
    abstract fun getProcedureDao(): ProcedureDao
    abstract fun getPatientDao(): PatientsDao

}