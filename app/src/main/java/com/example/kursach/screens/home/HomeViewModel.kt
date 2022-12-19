package com.example.kursach.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.kursach.Repositories
import com.example.kursach.database.NoteDatabase
import com.example.kursach.database.notes.repository.NoteRepositoryImpl
import com.example.kursach.database.notes.entities.NoteEntity
import com.example.kursach.database.notes.entities.NoteUpdateEnabledTuple
import kotlinx.coroutines.launch

class HomeViewModel(application: Application): AndroidViewModel(application) {

    val context = application

    private val _notes = MutableLiveData<List<NoteEntity>>()
    val notes: LiveData<List<NoteEntity>>
        get() = _notes

    fun initDatabase(){
        viewModelScope.launch {
            Repositories.noteRepository.getNotes().collect{
                _notes.value = it
            }
        }
    }

    fun getNotesByDate(date: String){
        viewModelScope.launch {
            Repositories.noteRepository.getNotesByDate(date).collect{
                _notes.value = it
            }
        }
    }

    fun updateEnabledNote(note: NoteEntity){
        viewModelScope.launch {
            Repositories.noteRepository.updateEnabledNote(
                NoteUpdateEnabledTuple(
                    note.Id,
                    !note.enabled
                )
            )
        }
    }

    fun deleteNote(note: NoteEntity){
        viewModelScope.launch {
            Repositories.noteRepository.deleteNote(note)
        }
    }





}