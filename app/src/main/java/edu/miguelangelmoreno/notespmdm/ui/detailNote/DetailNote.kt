package edu.miguelangelmoreno.notespmdm.ui.detailNote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import edu.miguelangelmoreno.notespmdm.NotesPMDMAApplication
import edu.miguelangelmoreno.notespmdm.R
import edu.miguelangelmoreno.notespmdm.data.NotesDataSource
import edu.miguelangelmoreno.notespmdm.data.NotesRepository
import edu.miguelangelmoreno.notespmdm.databinding.ActivityDetailNoteBinding
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailNote : AppCompatActivity() {
    companion object {
        private const val NOTE_ID = "note_id"

        fun navigate(activity: AppCompatActivity, noteId: Int = 0) {
            val intent = Intent(activity, DetailNote::class.java).apply {
                putExtra(NOTE_ID, noteId)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            activity.startActivity(intent)
        }
    }

    private val viewModel: DetailNoteViewModel by viewModels {
        val db = (application as NotesPMDMAApplication).notesDatabase
        val dataSource = NotesDataSource(db.notesDao())
        val repository = NotesRepository(dataSource)
        val noteId = intent.extras!!.getInt(NOTE_ID)
        DetailNoteViewModelFactory(repository, noteId)
    }
    private lateinit var binding: ActivityDetailNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()

        binding.imSave.setOnClickListener {
            if (saveNote())
                finish()
        }
    }

    private fun initUi() {
        lifecycleScope.launch {
            viewModel.stateNote.collect { note ->
                if (note.idNote != 0) {
                    binding.mtDetail.title = getString(R.string.txt_opEditNote)
                }

                binding.textTitle.setText(note.title)
                binding.textDescription.setText(note.description)
            }

        }

    }

    private fun saveNote(): Boolean {
        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
        val formattedDate = date.format(formatter)
        val title = binding.textTitle.text.toString()
        val description = binding.textDescription.text.toString()

        if (title.isEmpty()) {
            binding.textTitle.error = getString(R.string.txt_errorTitle)
            return false
        } else {
            var note = viewModel.stateNote.value
            note.title = title
            note.description = description
            if (note.date.isNullOrEmpty()) {
                note.date = formattedDate
            }
            viewModel.saveNote(note)

            return true
        }
    }
}