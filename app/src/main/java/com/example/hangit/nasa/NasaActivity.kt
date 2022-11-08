package com.example.hangit.nasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import com.example.hangit.databinding.ActivityNasaBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NasaActivity : AppCompatActivity() {

    private lateinit var binding:ActivityNasaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNasaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nasaSearchBar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                preformSearch(query?:return true)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return true
            }


        })

    }

    private fun preformSearch(query: String) {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://images-api.nasa.gov/")
        .addConverterFactory(GsonConverterFactory.create()).build()

        val call = retrofit.create(ApiNasa::class.java)
        call.performSearch(query).enqueue(object: Callback<ImageList> {

            override fun onResponse(call: Call<ImageList>, response: Response<ImageList>) {
                val images = response.body() ?: return
                images.collection.items.map{
                    val data = it.data.getOrNull(0)
                    val link = it.links.getOrNull(0)?.href ?: ""
                    NasaImage(data?.title?:"",data?.description ?: "",link )
                }
                Picasso.get().load(nasaImage[0].link).into(binding.imageView)
            }

            override fun onFailure(call: Call<ImageList>, t: Throwable) {
                Toast.makeText(this@NasaActivity,"Something went wrong",Toast.LENGTH_SHORT).show()
            }

        })


    }
}