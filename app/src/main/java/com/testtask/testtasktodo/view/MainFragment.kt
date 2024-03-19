package com.testtask.testtasktodo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testtask.testtasktodo.R
import com.testtask.testtasktodo.databinding.FragmentMainBinding
import com.testtask.testtasktodo.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private val viewModel: MainViewModel by viewModel()
    private lateinit var adapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        progressBar = binding.progressBar
        adapter = TodoAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        viewModel.loadData()
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                progressBar.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.GONE
            }
        }
        binding.addNoteButton.setOnClickListener {
            navigateToAddTodoFragment()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeTodoList()
    }

    private fun observeTodoList() {
        viewModel.todoList.observe(viewLifecycleOwner) { todoList ->
            adapter.submitList(todoList)
        }

        adapter.setOnItemClickListener { description, id ->
            navigateToEditFragment(description, id)
        }
    }

    private fun navigateToAddTodoFragment() {
        findNavController().navigate(R.id.action_mainFragment2_to_editFragment2)
    }

    private fun navigateToEditFragment(description: String, id: Long) {
        val bundle = bundleOf("description" to description, "id" to id)
        findNavController().navigate(R.id.action_mainFragment2_to_editFragment2, bundle)
    }
}
