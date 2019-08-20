package com.astronout.anotherroom

import com.astronout.anotherroom.models.Task

interface AdapterOnClickListener {
    fun onItemAdapterClickedEdit(task: Task)
    fun onItemAdapterClickedDelete(task: Task, position: Int)
    fun onUpdateCallback()
}