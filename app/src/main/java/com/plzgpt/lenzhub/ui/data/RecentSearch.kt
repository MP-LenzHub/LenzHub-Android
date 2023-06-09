package com.plzgpt.lenzhub.ui.data

import androidx.room.*

@Entity(tableName = "lh_recent_database")
data class RecentSearch(
    @ColumnInfo(name = "searchText")
    val searchText: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Dao
interface RecentSearchDao {
    @Query("SELECT * FROM lh_recent_database")
    fun getAll(): List<RecentSearch>

    @Insert
    fun insert(recentSearch: RecentSearch)

    @Insert
    fun insertAll(recentData: List<RecentSearch>)

    @Delete
    fun delete(recentData: RecentSearch)

    @Query("SELECT * FROM lh_recent_database WHERE searchText = :searchTxt")
    fun findRecentSearchBySearchText(searchTxt: String): RecentSearch

}

@Database(entities = [RecentSearch::class], version = 1)
abstract class RecentSearchDatabase: RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao
}