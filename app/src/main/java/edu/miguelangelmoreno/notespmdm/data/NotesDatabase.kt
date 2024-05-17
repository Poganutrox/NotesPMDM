package edu.miguelangelmoreno.notespmdm.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Transaction
import edu.miguelangelmoreno.notespmdm.model.Note
import kotlinx.coroutines.flow.Flow

@Database(entities = [Note::class], version = 1)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao() : NotesDao
}
@Dao
interface NotesDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Transaction
    @Query("SELECT * FROM Notes")
    fun getAllNotes() : Flow<List<Note>>

    @Query("SELECT * FROM Notes WHERE idNote = :idNote")
    suspend fun getNoteById(idNote : Int) : Note


}