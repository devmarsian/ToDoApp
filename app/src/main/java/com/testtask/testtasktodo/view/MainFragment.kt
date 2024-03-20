package com.testtask.testtasktodo.view

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testtask.testtasktodo.R
import com.testtask.testtasktodo.ToDoApplication
import com.testtask.testtasktodo.databinding.FragmentMainBinding
import com.testtask.testtasktodo.model.TodoNote
import com.testtask.testtasktodo.viewmodel.MainPresenter
import com.testtask.testtasktodo.viewmodel.MainView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class MainFragment : MvpAppCompatFragment(), MainView {
    @InjectPresenter
    lateinit var presenter: MainPresenter
    @ProvidePresenter
    fun providePresenter(): MainPresenter {
        return MainPresenter((requireActivity().application as ToDoApplication).getAppComponentSynthetic().provideTodoDatabase())
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: TodoAdapter
    private lateinit var showDoneTasksButton: Button
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        progressBar = binding.progressBar
        adapter = TodoAdapter(
            onItemClick = { todo -> navigateToEditFragment(todo.description, todo.id) },
            onCheckboxClick = { todo -> presenter.markTaskAsDone(todo) }
        )
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        showDoneTasksButton = binding.showDeleted
        binding.addNoteButton.setOnClickListener {
            val currentTimeMillis = System.currentTimeMillis()
            val randomNumber = (0..999).random()
            val uniqueId = currentTimeMillis * 1000 + randomNumber
            navigateToEditFragment("", uniqueId)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadData()
    }

    override fun showLoading() {
        requireActivity().runOnUiThread {
            progressBar.visibility = View.VISIBLE
        }
    }
    override fun hideLoading() {
        requireActivity().runOnUiThread {
            progressBar.visibility = View.GONE
        }
    }

    override fun showTodoList(todoList: List<TodoNote>) {
        requireActivity().runOnUiThread {
            adapter.submitList(todoList)
        }
    }
    private fun navigateToEditFragment(description: String, id: Long) {
        val bundle = bundleOf("description" to description, "id" to id)
        findNavController().navigate(R.id.action_mainFragment2_to_editFragment2, bundle)
    }

    override fun showDoneTasksButton() {
        requireActivity().runOnUiThread {
            showDoneTasksButton.visibility = View.VISIBLE
        }
    }
    override fun hideDoneTasksButton() {
        showDoneTasksButton.visibility = View.GONE
    }

    override fun showMessage(message: String) {
    }
}

