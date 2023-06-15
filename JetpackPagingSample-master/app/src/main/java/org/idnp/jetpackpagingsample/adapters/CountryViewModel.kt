package org.idnp.jetpackpagingsample.adapters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.idnp.jetpackpagingsample.DB.AppDatabase
import org.idnp.jetpackpagingsample.entities.Country
import org.idnp.jetpackpagingsample.model.CountryRepository

class CountryViewModel(application: Application): AndroidViewModel(application) {
    private val repository: CountryRepository

    init {
        val countryDao = AppDatabase.getInstance(application).countryDao()
        repository = CountryRepository(countryDao)
    }

    fun insertCountry(country: Country) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertCountryr(country)
        }
    }

    fun pagingSource() : Flow<PagingData<Country>> {

        return Pager(
            PagingConfig(
                pageSize = 20,
                initialLoadSize = 20 * 5,
                enablePlaceholders = false,
                prefetchDistance = 3
            )
        ) {
            repository.browsecountry
        }.flow.cachedIn(viewModelScope)
    }

}