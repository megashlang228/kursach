package com.example.kursach.screens.proceduredetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kursach.Repositories
import com.example.kursach.database.procedure.entities.ProcedureEntity
import kotlinx.coroutines.launch

class ProcedureDetailViewModel(application: Application): AndroidViewModel(application) {

    private val _procedure = MutableLiveData<ProcedureEntity>()
    val procedure: LiveData<ProcedureEntity>
        get() = _procedure

    fun createProcedure(procedureEntity: ProcedureEntity){
        viewModelScope.launch {
            Repositories.procedureRepository.createProcedure(procedureEntity)
        }
    }
    fun updateProcedure(procedureEntity: ProcedureEntity){
        viewModelScope.launch {
            Repositories.procedureRepository.updateProcedure(procedureEntity)
        }
    }
    fun getProcedureById(id: Int){
        viewModelScope.launch {
            Repositories.procedureRepository.getProcedureById(id).collect{
                _procedure.value = it
            }
        }
    }
}