package com.example.kursach.screens.procedurelist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kursach.Repositories
import com.example.kursach.database.NoteDatabase
import com.example.kursach.database.notes.entities.NoteEntity
import com.example.kursach.database.notes.repository.NoteRepositoryImpl
import com.example.kursach.database.procedure.entities.ProcedureEntity
import kotlinx.coroutines.launch

class ProcedureListViewModel(application: Application) : AndroidViewModel(application) {

        val context = application

        private val _notes = MutableLiveData<List<ProcedureEntity>>()
        val notes: LiveData<List<ProcedureEntity>>
            get() = _notes

        fun initDatabase(){
            viewModelScope.launch {
                Repositories.procedureRepository.getAllProcedures().collect{
                    _notes.value = it
                }
            }
        }





    }
