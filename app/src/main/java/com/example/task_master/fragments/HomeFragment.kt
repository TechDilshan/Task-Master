package com.example.task_master.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task_master.R
import com.example.task_master.adapter.NoteAdapter
import com.example.task_master.databinding.FragmentHomeBinding
import com.example.task_master.model.Note
import com.example.task_master.viewmodel.NoteViewModel

//display the all tasks
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    // Create the fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        // implement the note adapter
        noteAdapter = NoteAdapter()
        binding.homeRecyclerView.apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        // implement the ViewModel
        noteViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

        noteViewModel.getAllNotes().observe(viewLifecycleOwner) { notes ->
            noteAdapter.differ.submitList(notes)
            updateUI(notes)
        }

        // adding a new task
        binding.addNoteFab.setOnClickListener {
            navigateToAddNoteFragment()
        }
    }

    // Navigate to the AddNoteFragment
    private fun navigateToAddNoteFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToAddNoteFragment()
        requireView().findNavController().navigate(action)
    }

    private fun updateUI(notes: List<Note>) {
        if (notes.isEmpty()) {
            binding.emptyNotesImage.visibility = View.VISIBLE
            binding.homeRecyclerView.visibility = View.GONE
        } else {
            binding.emptyNotesImage.visibility = View.GONE
            binding.homeRecyclerView.visibility = View.VISIBLE
        }
    }

    // Create options menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)

        // Set search function
        val searchItem = menu.findItem(R.id.searchMenu)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchNotes(newText)
                return true
            }
        })
    }

    // Search notes
    private fun searchNotes(query: String?) {
        val searchQuery = "%$query%"
        noteViewModel.searchNotes(searchQuery).observe(viewLifecycleOwner) { notes ->
            noteAdapter.differ.submitList(notes)
            updateUI(notes)
        }
    }

    // Clean view binding
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
