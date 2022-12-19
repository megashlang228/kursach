package com.example.kursach.database.notes.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.kursach.database.patients.entities.PatientEntity
import com.example.kursach.database.procedure.entities.ProcedureEntity

@Entity(tableName = "note",
        foreignKeys = [
            ForeignKey(
                entity = PatientEntity::class,
                parentColumns = ["Id"],
                childColumns = ["patient_id"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
            ),
            ForeignKey(
                entity = ProcedureEntity::class,
                parentColumns = ["Id"],
                childColumns = ["procedure_id"],
                onDelete = ForeignKey.CASCADE,
                onUpdate = ForeignKey.CASCADE
            )
        ]
    )
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    var Id: Int = 0,
    var title: String,
    var description: String = "",
    @ColumnInfo(name = "procedure_id")var procedureId: Int,
    @ColumnInfo(name = "patient_id")var patientId: Int,
    var date: String,
    var time: String,
    var enabled: Boolean = true
)