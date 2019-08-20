package com.astronout.anotherroom.viewmodels

import android.app.Dialog
import android.os.AsyncTask
import android.view.View
import androidx.databinding.ObservableField
import com.astronout.anotherroom.AdapterOnClickListener
import com.astronout.anotherroom.database.TaskDatabase
import com.astronout.anotherroom.database.TaskRoom
import com.astronout.anotherroom.models.Task

class PopupUpdateViewModel(private val dialog: Dialog, private val task: Task,private val taskDatabase: TaskDatabase, private val mainViewModel: MainViewModel, private val iface: AdapterOnClickListener) {

    var title: ObservableField<String> = ObservableField(task.titleTask!!)
    var description: ObservableField<String> = ObservableField(task.descriptionTask!!)
    var titleError: ObservableField<String> = ObservableField()
    var descriptionError: ObservableField<String> = ObservableField()

    fun clickCancel(view: View) {
        dialog.dismiss()
    }

    fun clickUpdate(view: View) {
        titleError.set(null)
        descriptionError.set(null)
        when {
            title.get()?.trim().equals("") -> {
                titleError.set("Title is empty")
            }
            description.get()?.trim().equals("") -> {
                descriptionError.set("Description is empty")
            }
            else -> {
                dialog.dismiss()
                val taskRoom = DatabaseAsyncGetTask(taskDatabase, task.idTask!!).execute().get()
                taskRoom.task = title.get()
                taskRoom.description = description.get()
                DatabaseAsyncUpdateTask(taskDatabase, taskRoom, mainViewModel).execute()
                iface.onUpdateCallback()
            }
        }
    }

    class DatabaseAsyncGetTask(private val taskDatabase: TaskDatabase, val id: Int): AsyncTask<Void, Void, TaskRoom>(){
        override fun doInBackground(vararg p0: Void?): TaskRoom {
            return taskDatabase.appDaoAccess().getTask(id)
        }

    }

    class DatabaseAsyncUpdateTask(private val taskDatabase: TaskDatabase, private val taskRoom: TaskRoom, private val mainViewModel: MainViewModel): AsyncTask<Void, Void, Void>(){

        override fun doInBackground(vararg p0: Void?): Void? {
            taskDatabase.appDaoAccess().updateTask(taskRoom)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            mainViewModel.showAllTask()
        }

    }

}