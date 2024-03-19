package com.testtask.testtasktodo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.testtask.testtasktodo.databinding.FragmentEditBinding
import com.testtask.testtasktodo.viewmodel.EditViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val viewModel: EditViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        val description = arguments?.getString("description")
        viewModel.setDescription(description)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("id")
        if (id != null) {
            viewModel.titleText = "Заметка"
            binding.deleteButton.visibility = View.VISIBLE
            binding.deleteButton.setOnClickListener{
                viewModel.deleteFromDB(id)
                findNavController().navigateUp()
            }
        }
        binding.addButton.setOnClickListener {
            val textFromDesc = binding.descriptionEditText.text.toString()
            if (textFromDesc.isBlank()) {
                Toast.makeText(requireContext(), "Добавьте описание!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val toSave = viewModel.capitalizeSentences(textFromDesc)
            if (id == null) {
                viewModel.saveTask(toSave)
            } else {
                viewModel.updateTextInDatabase(id, toSave)
            }
            findNavController().navigateUp()
        }
    }
}


