package edu.miguelangelmoreno.notespmdm.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.miguelangelmoreno.notespmdm.data.NotesRepository
import edu.miguelangelmoreno.notespmdm.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    val noteList: Flow<List<Note>> = notesRepository.allNotes

    fun insertNote(note: Note) {
        viewModelScope.launch {
            notesRepository.insertNote(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            notesRepository.deleteNote(note)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val notesRepository: NotesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(notesRepository) as T
    }
}