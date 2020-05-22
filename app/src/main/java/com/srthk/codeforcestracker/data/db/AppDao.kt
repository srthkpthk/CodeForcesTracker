package com.srthk.codeforcestracker.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAvailableContests(contestsList: List<AvailableContests>)

    @Query("select * from AvailableContests")
    fun getAvailableContests(): LiveData<List<AvailableContests>>
}