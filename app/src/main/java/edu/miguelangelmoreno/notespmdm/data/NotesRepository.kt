package edu.miguelangelmoreno.notespmdm.data

import edu.miguelangelmoreno.notespmdm.model.Note
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val dataSource: NotesDataSource) {
    val allNotes : Flow<List<Note>> = dataSource.allNotes

    suspend fun insertNote(note: Note){
        dataSource.insertNote(note)
    }

    suspend fun deleteNote(note: Note){
        dataSource.deleteNote(note)
    }

    suspend fun getNoteById(idNote : Int) : Note{
        return dataSource.getNoteById(idNote)
    }
}