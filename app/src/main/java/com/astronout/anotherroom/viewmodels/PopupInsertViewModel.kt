package com.astronout.anotherroom.viewmodels

import android.app.Dialog
import android.os.AsyncTask
import android.util.Log.d
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import com.astronout.anotherroom.database.TaskDatabase
import com.astronout.anotherroom.database.TaskRoom
import com.astronout.anotherroom.views.MainActivity

class PopupInsertViewModel(private val dialog: Dialog, private val taskDatabase: TaskDatabase, private val mainViewModel: MainViewModel) {

    var title: ObservableField<String> = ObservableField()
    var description: ObservableField<String> = ObservableField()

    var titleError: ObservableField<String> = ObservableField()
    var descriptionError: ObservableField<String> = ObservableField()

    fun clickCancel(view: View){
        dialog.dismiss()
    }

    fun clickSave(view: View){
        d("insert", "${title.toString()} ${description.toString()}")
        titleError.set(null)
        descriptionError.set(null)
        when{
            title.get()?.trim().equals("") -> {
                titleError.set("Title is empty")
            }
            description.get()?.trim().equals("") -> {
                descriptionError.set("Description is empty")
            }
            else ->{
                val myTask = TaskRoom(title.get(), description.get())
                DatabaseAsync(myTask, taskDatabase, mainViewModel).execute()
                dialog.dismiss()
            }
        }
    }

    class DatabaseAsync(private val taskRoom: TaskRoom, private val taskDatabase: TaskDatabase, private val mainViewModel: MainViewModel): AsyncTask<Void, Void, Void>(){

        override fun doInBackground(vararg p0: Void?): Void? {
            taskDatabase.appDaoAccess().insertTask(taskRoom)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            mainViewModel.showAllTask()
        }

    }

}