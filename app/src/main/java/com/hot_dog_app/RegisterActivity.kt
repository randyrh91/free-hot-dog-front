package com.hot_dog_app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.hot_dog_app.model.LoginRequest
import com.hot_dog_app.model.LoginResponse
import com.hot_dog_app.model.PartnerRequest
import com.hot_dog_app.model.RegisterResponse
import com.hot_dog_app.service.PartnerService
import com.hot_dog_app.service.RetrofitAdapter
import com.hot_dog_app.service.SessionService
import com.hot_dog_app.ui.theme.FreeHotDogTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : ComponentActivity() {

    private val context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val view: View = layoutInflater.inflate(R.layout.view_register, null);
        setContentView(view);
        val nameEditText: EditText = view.findViewById(R.id.register_partner_name);
        val emailEditText: EditText = view.findViewById(R.id.register_partner_email);
        val phoneEditText: EditText = view.findViewById(R.id.register_partner_phone);
        val passwordEditText: EditText = view.findViewById(R.id.register_partner_password);
        val registerButton: TextView = view.findViewById(R.id.register_button_create);

        registerButton.setOnClickListener {
            val service = RetrofitAdapter().getService(PartnerService::class);
            val request = PartnerRequest(
                name = nameEditText.getText().toString().trim(),
                email = emailEditText.getText().toString().trim(),
                phone = phoneEditText.getText().toString().trim(),
                password = passwordEditText.getText().toString().trim(),
                activationCode = ""
            );
            service.create(request)?.enqueue(object :
                Callback<RegisterResponse> {
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if(response.body()?.status  == "OK"){
                        val loginActivity = Intent(context, LoginActivity::class.java)
                        startActivity(loginActivity)
                    }else{
                        Toast.makeText(
                            this@RegisterActivity,
                            "No se pudo registrar. Intente nuevamente",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "No se pudo registrar. Intente nuevamente",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }
}