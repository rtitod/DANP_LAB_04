package org.idnp.jetpackpagingsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.danp4.database.AdapterCountryPaging
import com.romzc.labpaging.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.idnp.jetpackpagingsample.adapters.CountryViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)
        val adapterCountry = AdapterCountryPaging()
        val rv = findViewById<RecyclerView>(R.id.recycler_view)
        rv.adapter = adapterCountry

        GlobalScope.launch(Dispatchers.IO){
            viewModel.pagingSource().collect(){
                adapterCountry.submitData(it)
            }
        }
    }
}