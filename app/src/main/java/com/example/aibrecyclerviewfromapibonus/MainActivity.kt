package com.example.aibrecyclerviewfromapibonus

import android.graphics.Insets.add
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.graphics.Insets.add
import androidx.core.view.OneShotPreDrawListener.add
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
const val BASE_URL="https://dojo-recipes.herokuapp.com/"
class MainActivity : AppCompatActivity() {
    lateinit var RVmain: RecyclerView
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RVmain = findViewById(R.id.RV)

        RVmain.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        RVmain.layoutManager=linearLayoutManager

        getMyData()

    }

    private fun getMyData()
    {
        val retorfitBuilder= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(APIInterface::class.java)

        val retrofitData =retorfitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MyDataItem>?> {
            override fun onResponse(
                call: Call<List<MyDataItem>?>,
                response: Response<List<MyDataItem>?>,
            ) {
                val responseBody=response.body()!!

                recyclerViewAdapter = RecyclerViewAdapter(baseContext,responseBody)
                recyclerViewAdapter.notifyDataSetChanged()
                RVmain.adapter=recyclerViewAdapter



            }

            override fun onFailure(call: Call<List<MyDataItem>?>, t: Throwable) {


            }
        })
    }


}


