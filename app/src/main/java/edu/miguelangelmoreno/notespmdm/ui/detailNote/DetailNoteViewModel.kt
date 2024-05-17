package edu.miguelangelmoreno.notespmdm.ui.detailNote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.miguelangelmoreno.notespmdm.data.NotesRepository
import edu.miguelangelmoreno.notespmdm.model.Note
import edu.miguelangelmoreno.notespmdm.ui.main.MainViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailNoteViewModel(private val repository: NotesRepository, private val noteId: Int) :
    ViewModel() {

    private val _stateNote = MutableStateFlow(Note())
    val stateNote: StateFlow<Note> = _stateNote.asStateFlow()

    init {
        viewModelScope.launch {
            val noteAux = repository.getNoteById(noteId)
            if (noteAux != null) {
                _stateNote.value = noteAux
            }
        }
    }

    fun saveNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class DetailNoteViewModelFactory(
    private val notesRepository: NotesRepository,
    private val noteId: Int
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailNoteViewModel(notesRepository, noteId) as T
    }
}