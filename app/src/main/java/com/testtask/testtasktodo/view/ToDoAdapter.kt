package com.testtask.testtasktodo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.testtask.testtasktodo.model.TodoNote
import com.testtask.testtasktodo.databinding.TodoItemBinding

import androidx.recyclerview.widget.ListAdapter

class TodoAdapter : ListAdapter<TodoNote, TodoAdapter.ViewHolder>(TodoNoteDiffCallback()) {
    private var onItemClick: (String, Long) -> Unit = { _, _ -> }

    fun setOnItemClickListener(listener: (String, Long) -> Unit) {
        onItemClick = listener
    }

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
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val todo = getItem(position)
                    onItemClick(todo.description, todo.id)
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
