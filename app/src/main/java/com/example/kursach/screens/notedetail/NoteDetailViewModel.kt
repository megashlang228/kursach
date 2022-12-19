package com.example.kursach.screens.notedetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kursach.Repositories
import com.example.kursach.database.notes.entities.NoteEntity
import com.example.kursach.database.patients.entities.PatientEntity
import com.example.kursach.database.procedure.entities.ProcedureEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NoteDetailViewModel(application: Application): AndroidViewModel(application) {

    private val _note = MutableLiveData<NoteEntity>()
    val note: LiveData<NoteEntity>
        get() = _note

    private val _patient = MutableLiveData<PatientEntity>()
    val patient: LiveData<PatientEntity>
        get() = _patient

    private val _procedure = MutableLiveData<ProcedureEntity>()
    val procedure: LiveData<ProcedureEntity>
        get() = _procedure

    fun insert(noteEntity: NoteEntity){
        viewModelScope.launch (Dispatchers.IO) {
            Repositories.noteRepository.createNote(noteEntity)
        }
    }

    fun update(noteEntity: NoteEntity){
        viewModelScope.launch (Dispatchers.IO) {
            Repositories.noteRepository.updateNote(noteEntity)
        }
    }

    fun getNoteById(id: Int){
        viewModelScope.launch (Dispatchers.IO) {
            Repositories.noteRepository.getNoteById(id).collect{
                _note.postValue(it)
            }
        }
    }

    fun getPatientById(id: Int){
        viewModelScope.launch {
            Repositories.patientRepository.getPatientById(id).collect{
                _patient.postValue(it)
            }
        }
    }

    fun getProcedureById(id: Int){
        viewModelScope.launch {
            Repositories.procedureRepository.getProcedureById(id).collect{
                _procedure.postValue(it)
            }
        }
    }
}