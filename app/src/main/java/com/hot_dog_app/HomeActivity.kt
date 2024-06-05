package com.hot_dog_app

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hot_dog_app.adapter.AdapterListView
import com.hot_dog_app.model.CompanyResponse
import com.hot_dog_app.service.CompanyService
import com.hot_dog_app.service.RetrofitAdapter
import com.hot_dog_app.service.SessionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : ComponentActivity() {

    private var listView: AdapterListView? = null
    private var recyclerView: RecyclerView? = null
    private lateinit var session: SessionService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.view_home);

        session = SessionService(this);
        val token = "Bearer ${session.fetchAuthToken()}";
        val service = RetrofitAdapter().getService(CompanyService::class)
        service.getAllCompanies(token)?.enqueue(object : Callback<List<CompanyResponse?>?> {
            override fun onResponse(
                call: Call<List<CompanyResponse?>?>,
                response: Response<List<CompanyResponse?>?>
            ) {
                generateDataList(response.body());
            }

            override fun onFailure(call: Call<List<CompanyResponse?>?>, t: Throwable) {
                Toast.makeText(
                    this@HomeActivity,
                    "No nos pudimos conectar con la API, revise su coneccion a internet e intente de nuevo",
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    private fun generateDataList(companyList: List<CompanyResponse?>?) {
        recyclerView = findViewById<View>(R.id.customRecyclerView) as RecyclerView?
        listView = AdapterListView(this, companyList)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this@HomeActivity)
        recyclerView?.setLayoutManager(layoutManager)
        recyclerView?.setAdapter(listView)
    }
}