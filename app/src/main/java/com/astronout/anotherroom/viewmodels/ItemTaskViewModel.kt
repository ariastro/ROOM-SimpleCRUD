package com.astronout.anotherroom.viewmodels

import android.view.View
import androidx.databinding.ObservableField
import com.astronout.anotherroom.AdapterOnClickListener
import com.astronout.anotherroom.models.Task

class ItemTaskViewModel(private val task: Task, private val adapterOnClickListener: AdapterOnClickListener, private val position: Int) {

    var title: ObservableField<String?> = ObservableField(task.titleTask)
    var description: ObservableField<String?> = ObservableField(task.descriptionTask)

    fun clickDelete(view: View){
        adapterOnClickListener.onItemAdapterClickedDelete(task, position)
    }

    fun clickEdit(view: View){
        adapterOnClickListener.onItemAdapterClickedEdit(task)
    }

}