package com.example.task_master

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.task_master.database.NoteDatabase
import com.example.task_master.repository.NoteRepository
import com.example.task_master.viewmodel.NoteViewModel
import com.example.task_master.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // start the ViewModel
        setUpViewModel()
    }

    // implement NoteViewModel
    private fun setUpViewModel(){
        // Create NoteRepository
        val noteRepository = NoteRepository(NoteDatabase(this))

        // Create NoteViewModelFactory
        val viewModelProviderFactory = NoteViewModelFactory(application, noteRepository)

        // Get NoteViewModel instance
        noteViewModel = ViewModelProvider(this, viewModelProviderFactory)[NoteViewModel::class.java]
    }
}
