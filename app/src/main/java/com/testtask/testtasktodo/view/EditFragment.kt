package com.testtask.testtasktodo.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.testtask.testtasktodo.NotificationHelper
import com.testtask.testtasktodo.ToDoApplication
import com.testtask.testtasktodo.databinding.FragmentEditBinding
import com.testtask.testtasktodo.viewmodel.EditPresenter
import com.testtask.testtasktodo.viewmodel.EditView
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter

class EditFragment : MvpAppCompatFragment(), EditView {

    @InjectPresenter
    lateinit var presenter: EditPresenter
    lateinit var mContext: Context
    lateinit var textFromEditText: EditText
    lateinit var titleEditText: TextView
    lateinit var deleteButton: Button
    private lateinit var binding: FragmentEditBinding

    @ProvidePresenter
    fun providePresenter(): EditPresenter {
        return EditPresenter(
            (requireActivity().application as ToDoApplication).getAppComponentSynthetic()
                .provideTodoDatabase()
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        deleteButton = binding.deleteButton
        textFromEditText = binding.descriptionEditText
        titleEditText = binding.titleEditText
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = requireArguments().getLong("id")
        val description = requireArguments().getString("description")
        presenter.setDescription(description)
        if (description!!.isEmpty()) {
            setupEmptyDescription(id)
        } else {
            setupNonEmptyDescription(id)
        }
        binding.addButton.setOnClickListener {
            presenter.updateTextInDatabase(id, textFromEditText.text.toString())
            findNavController().navigateUp()
        }
        binding.alert.setOnClickListener {
            presenter.toggleNotificationForTodo(id)
        }
    }

    override fun setDescription(description: String?) {
        binding.descriptionEditText.setText(description)
    }
    override fun showMessage(message: String) {
        if (isAdded) {
            activity?.runOnUiThread {
                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun setPush() {
        activity?.runOnUiThread {
            val push = NotificationHelper(requireContext())
            push.setNotification(textFromEditText.text.toString(), 5000)
        }
    }
    @SuppressLint("CheckResult")
    override fun setupEmptyDescription(id: Long) {
        titleEditText.text = "Новая заметка"
        presenter.observeTextChanges()
            .subscribe { text ->
                presenter.saveTask(text, id)
            }
        textFromEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                presenter.onTextChanged(s.toString())
            }
        })
    }

    override fun setupNonEmptyDescription(id: Long) {
        titleEditText.text = "Заметка"
        deleteButton.visibility = View.VISIBLE
        deleteButton.setOnClickListener {
            presenter.deleteFromDB(id)
            findNavController().navigateUp()
        }
    }
}


