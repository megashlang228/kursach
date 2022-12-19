package com.example.kursach.screens.parientslist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kursach.Repositories
import com.example.kursach.database.patients.entities.PatientEntity
import kotlinx.coroutines.launch

class PatientsViewModel(application: Application): AndroidViewModel(application) {

    private val _patients = MutableLiveData<List<PatientEntity>>()
    val patients: LiveData<List<PatientEntity>>
    get() = _patients

    fun initDatabase(){
        viewModelScope.launch {
            Repositories.patientRepository.getAllPatients().collect{
                _patients.value = it
            }
        }
    }
}