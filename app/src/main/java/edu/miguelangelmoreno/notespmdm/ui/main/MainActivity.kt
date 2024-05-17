package edu.miguelangelmoreno.notespmdm.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import edu.miguelangelmoreno.notespmdm.NotesPMDMAApplication
import edu.miguelangelmoreno.notespmdm.R
import edu.miguelangelmoreno.notespmdm.data.NotesDataSource
import edu.miguelangelmoreno.notespmdm.data.NotesRepository
import edu.miguelangelmoreno.notespmdm.databinding.ActivityMainBinding
import edu.miguelangelmoreno.notespmdm.ui.adapters.NotesAdapter
import edu.miguelangelmoreno.notespmdm.ui.detailNote.DetailNote
import kotlinx.coroutines.launch
import java.time.Duration

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterRecycler: NotesAdapter

    private val viewModel: MainViewModel by viewModels {
        val db = (application as NotesPMDMAApplication).notesDatabase
        val dataSource = NotesDataSource(db.notesDao())
        val repository = NotesRepository(dataSource)
        MainViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        initListenerMenu()

    }

    private fun initUi() {
        binding.materialToolbar.inflateMenu(R.menu.menu_toolbar)
        binding.imgAddNote.setOnClickListener {
            DetailNote.navigate(this@MainActivity)
        }
    }

    private fun initListenerMenu() {
        binding.materialToolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.opt_sortByTitle -> {
                    lifecycleScope.launch {
                        viewModel.noteList.collect { list ->
                            adapterRecycler.submitList(list.sortedBy { note -> note.title })
                        }
                    }
                    true
                }

                R.id.opt_filtersOff -> {
                    lifecycleScope.launch {
                        viewModel.noteList.collect { list ->
                            adapterRecycler.submitList(list)
                        }
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun initRecyclerView() {
        adapterRecycler = NotesAdapter(
            onNoteClick = { noteId ->
                DetailNote.navigate(this@MainActivity, noteId)
            },
            onNoteDeleteClick = { note, pos ->
                viewModel.deleteNote(note)

                Snackbar.make(
                    binding.root,
                    getString(R.string.txt_noteDeleted, note.title),
                    Snackbar.LENGTH_LONG
                ).setAction(R.string.txt_undo) {
                    viewModel.insertNote(note)
                }.show()

            }
        )

        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            this.adapter = adapterRecycler
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.noteList.collect {
                    if (it.isEmpty()) {
                        binding.tvHidden.visibility = View.VISIBLE
                        binding.rvNotes.visibility = View.GONE
                    } else {
                        binding.tvHidden.visibility = View.GONE
                        binding.rvNotes.visibility = View.VISIBLE
                    }
                    adapterRecycler.submitList(it)
                }
            }
        }
    }
}