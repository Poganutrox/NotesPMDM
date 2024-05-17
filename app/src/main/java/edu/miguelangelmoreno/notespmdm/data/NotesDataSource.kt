package edu.miguelangelmoreno.notespmdm.data

import edu.miguelangelmoreno.notespmdm.model.Note
import kotlinx.coroutines.flow.Flow

class NotesDataSource(private val db : NotesDao) {
    var allNotes : Flow<List<Note>> = db.getAllNotes()

    suspend fun insertNote(note: Note){
        db.insertNote(note)
    }

    suspend fun deleteNote(note: Note){
        db.deleteNote(note)
    }

    suspend fun getNoteById(idNote : Int) : Note{
        return db.getNoteById(idNote)
    }
}