package com.astronout.anotherroom.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.astronout.anotherroom.AppDaoAccess

@Database(entities = [(TaskRoom::class)], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase(){
    abstract fun appDaoAccess(): AppDaoAccess
}