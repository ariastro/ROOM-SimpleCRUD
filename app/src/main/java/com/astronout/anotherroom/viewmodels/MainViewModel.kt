package com.astronout.anotherroom.viewmodels

import android.app.Activity
import android.app.Dialog
import android.graphics.Point
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.astronout.anotherroom.AdapterOnClickListener
import com.astronout.anotherroom.R
import com.astronout.anotherroom.data.AppConstant
import com.astronout.anotherroom.database.TaskDatabase
import com.astronout.anotherroom.database.TaskRoom
import com.astronout.anotherroom.databinding.InsertTaskBinding
import com.astronout.anotherroom.databinding.UpdateTaskBinding
import com.astronout.anotherroom.models.Task
import com.astronout.anotherroom.utils.Utility

class MainViewModel(private val activity: Activity) {

    private val taskDatabase: TaskDatabase = Room.databaseBuilder(activity, TaskDatabase::class.java, AppConstant.DATABASE_NAME).build()

    var listTask: MutableLiveData<MutableList<Task>> = MutableLiveData()

    fun showAllTask(){
        val listTaskRoom: List<TaskRoom> = DatabaseAsyncSelectAll(taskDatabase).execute().get()
        val listTaskAll: MutableList<Task> = mutableListOf()
        for (i: Int in 0 until (listTaskRoom.size)){
            listTaskAll.add(Task(listTaskRoom[i].idTask, listTaskRoom[i].task, listTaskRoom[i].description))
        }
        this.listTask.value = listTaskAll
    }

    fun showInsertTask(view: View){
        val dialog = Dialog(activity)
        val popupBinding: InsertTaskBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.insert_task, null, false)
        popupBinding.popupInsert = PopupInsertViewModel(dialog, taskDatabase, this)
        dialog.setContentView(popupBinding.root)
        val size: Point = Utility.getScreenSize(activity)
        val width = size.x
        val widthReduce = (width * 10) / 100
        val widthShow = width - widthReduce
        dialog.window!!.setLayout(widthShow, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun showUpdateTask(task: Task, adapterOnClickListener: AdapterOnClickListener){
        val dialog = Dialog(activity)
        val popUpBinding: UpdateTaskBinding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.update_task, null, false)
        popUpBinding.popupUpdate = PopupUpdateViewModel(dialog, task, taskDatabase, this, adapterOnClickListener)
        dialog.setContentView(popUpBinding.root)
        val size:Point = Utility.getScreenSize(activity)
        val width = size.x
        val widthReduce = (width * 10) /100
        val widthShow = width - widthReduce
        dialog.window!!.setLayout(widthShow, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun deleteTask(task: Task){
        val taskRoom = DatabaseAsyncGetTask(taskDatabase, task.idTask!!).execute().get()
        DatabaseAsyncDelete(taskDatabase, taskRoom).execute()
    }

    class DatabaseAsyncSelectAll(private val taskDatabase: TaskDatabase): AsyncTask<Void, Void, List<TaskRoom>>(){

        override fun doInBackground(vararg p0: Void?): List<TaskRoom> {
            return taskDatabase.appDaoAccess().selectAll()
        }
    }

    class DatabaseAsyncGetTask(private val taskDatabase: TaskDatabase, val id:Int): AsyncTask<Void, Void, TaskRoom>(){
        override fun doInBackground(vararg p0: Void?): TaskRoom {
            return taskDatabase.appDaoAccess().getTask(id)
        }
    }

    class DatabaseAsyncDelete(private val taskDatabase: TaskDatabase, private val taskRoom: TaskRoom): AsyncTask<Void, Void, List<TaskRoom>>(){
        override fun doInBackground(vararg p0: Void?): List<TaskRoom>? {
            taskDatabase.appDaoAccess().deleteTask(taskRoom)
            return null
        }
    }

}