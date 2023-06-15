package org.idnp.jetpackpagingsample.model

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.idnp.jetpackpagingsample.entities.Country

@Dao
interface CountryDao {
    @Insert
    suspend fun insertCountry(country: Country)

    @Query("SELECT * FROM Country")
    fun pagingSource(): PagingSource<Int, Country>

}