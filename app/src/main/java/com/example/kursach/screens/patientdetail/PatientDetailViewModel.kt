package com.example.kursach.screens.patientdetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kursach.Repositories
import com.example.kursach.database.patients.entities.PatientEntity
import kotlinx.coroutines.launch

class PatientDetailViewModel(application: Application): AndroidViewModel(application) {

    private val _patient = MutableLiveData<PatientEntity>()
    val patient: LiveData<PatientEntity>
        get() = _patient

    fun getPatientById(id: Int){
        viewModelScope.launch {
            Repositories.patientRepository.getPatientById(id).collect{
                _patient.value = it
            }
        }
    }

    fun updatePatient(patientEntity: PatientEntity){
        viewModelScope.launch {
            Repositories.patientRepository.updatePatient(patientEntity)
        }
    }

    fun createPatient(patientEntity: PatientEntity){
        viewModelScope.launch {
            Repositories.patientRepository.createPatient(patientEntity)
        }
    }


}