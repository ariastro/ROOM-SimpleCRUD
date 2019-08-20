package com.astronout.anotherroom.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class TaskRoom(var task: String?, var description: String?) {
    @PrimaryKey(autoGenerate = true)
    var idTask: Int? = null
}