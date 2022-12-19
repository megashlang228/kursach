package com.example.kursach.database.procedure.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "procedure")
data class ProcedureEntity(
    @PrimaryKey(autoGenerate = true)
    var Id: Int = 0,
    var name: String
)