package com.testtask.testtasktodo.view

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.testtask.testtasktodo.model.TodoNote
import com.testtask.testtasktodo.databinding.TodoItemBinding

import androidx.recyclerview.widget.ListAdapter
import com.testtask.testtasktodo.R

class TodoAdapter (private val onItemClick: (TodoNote) -> Unit,
                   private val onCheckboxClick: (TodoNote) -> Unit): ListAdapter<TodoNote, TodoAdapter.ViewHolder>(TodoNoteDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }

    inner class ViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val animation = AnimationUtils.loadAnimation(binding.root.context, R.anim.vanish)
                    binding.descriptionTextView.paintFlags = binding.descriptionTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    binding.root.startAnimation(animation)
                } else {
                    binding.descriptionTextView.paintFlags = binding.descriptionTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val todo = getItem(position)
                    onItemClick(todo)
                    onCheckboxClick(todo)
                }
            }
        }
        fun bind(todo: TodoNote) {
            binding.todo = todo
            binding.executePendingBindings()
        }
    }
}

class TodoNoteDiffCallback : DiffUtil.ItemCallback<TodoNote>() {
    override fun areItemsTheSame(oldItem: TodoNote, newItem: TodoNote): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: TodoNote, newItem: TodoNote): Boolean {
        return oldItem == newItem
    }
}
