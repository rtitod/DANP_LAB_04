package org.idnp.jetpackpagingsample.model

import androidx.paging.PagingSource
import org.idnp.jetpackpagingsample.entities.Country

class CountryRepository(private val countryDao: CountryDao) {
    val browsecountry : PagingSource<Int, Country> = countryDao.pagingSource()

    suspend fun insertCountryr(country: Country){
        countryDao.insertCountry(country)
    }

}

