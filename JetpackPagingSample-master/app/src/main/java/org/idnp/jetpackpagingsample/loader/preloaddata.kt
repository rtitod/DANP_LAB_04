package org.idnp.jetpackpagingsample.loader

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.romzc.labpaging.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.idnp.jetpackpagingsample.DB.AppDatabase
import org.idnp.jetpackpagingsample.entities.Country
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

class preloaddata(private val context: Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        GlobalScope.launch(Dispatchers.IO){
            prePopulateUsers(context)
        }
    }

    private fun loadJsonArray(context: Context): JSONArray? {
        val builder = StringBuilder()
        val resources_a = context.resources
        val inputStream: InputStream = resources_a.openRawResource(R.raw.countries)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?

        try {
            while (reader.readLine().also { line = it } != null) {
                builder.append(line)
            }
            val json = JSONObject(builder.toString())
            return json.getJSONArray("countries")
        } catch (exception: IOException) {
            exception.printStackTrace()
        } catch (exception: JSONException) {
            exception.printStackTrace()
        }
        return null
    }

    suspend fun prePopulateUsers(context: Context) {
        val countryDao = AppDatabase.getInstance(context).countryDao()
        val countries: JSONArray? = loadJsonArray(context)

        try {
            var i = 0
            while (i < countries?.length() ?: 0) {

                val country: JSONObject? = countries?.getJSONObject(i)

                var CouNamEn: String = country?.getString("name_en")?: ""
                var CouNamEs: String = country?.getString("name_es")?: ""
                var CouConEn: String = country?.getString("continent_en")?: ""
                var CouConEs: String = country?.getString("continent_es")?: ""
                var CouCapEn: String = country?.getString("capital_en")?: ""
                var CouCapEs: String = country?.getString("capital_es")?: ""
                var CouDia: String = country?.getString("dial_code")?: ""
                var CouCod2: String = country?.getString("code_2")?: ""
                var CouCod3: String = country?.getString("code_3")?: ""
                var CouTld: String = country?.getString("tld")?: ""
                var CouKm2: String = country?.getString("km2")?: ""

                countryDao.insertCountry(
                    Country(0,CouNamEn, CouNamEs, CouConEn, CouConEs, CouCapEn, CouCapEs, CouDia, CouCod2, CouCod3, CouTld, CouKm2)
                )

                i++
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

}