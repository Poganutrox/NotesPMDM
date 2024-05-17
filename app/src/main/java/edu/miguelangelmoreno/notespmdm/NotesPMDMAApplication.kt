package edu.miguelangelmoreno.notespmdm

import android.app.Application
import androidx.room.Room
import edu.miguelangelmoreno.notespmdm.data.NotesDatabase

class NotesPMDMAApplication : Application() {
    lateinit var notesDatabase: NotesDatabase
        private set
    override fun onCreate() {
        super.onCreate()
        notesDatabase = Room.databaseBuilder(this,NotesDatabase::class.java, "notes-db").build()
    }
}