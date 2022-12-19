package com.example.kursach.database.patients.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient")
data class PatientEntity(
@PrimaryKey(autoGenerate = true)
var Id: Int = 0,
@ColumnInfo(name = "fio")var FIO: String,
var ward: String,
@ColumnInfo(name = "receipt_date")var receiptDate: String,
@ColumnInfo(name = "date_of_discharge")var dateOfDischarge: String
)