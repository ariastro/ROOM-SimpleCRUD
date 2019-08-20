package com.astronout.anotherroom

import androidx.room.*
import com.astronout.anotherroom.database.TaskRoom

@Dao
interface AppDaoAccess {

    @Insert
    fun insertTask(taskRoom: TaskRoom)

    @Query("SELECT * FROM TaskRoom")
    fun selectAll(): List<TaskRoom>

    @Query("SELECT * FROM TaskRoom WHERE idTask=:idTask")
    fun getTask(idTask: Int): TaskRoom

    @Update
    fun updateTask(taskRoom: TaskRoom)

    @Delete
    fun deleteTask(taskRoom: TaskRoom)

}