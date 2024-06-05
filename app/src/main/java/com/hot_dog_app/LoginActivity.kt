package com.hot_dog_app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.hot_dog_app.model.LoginRequest
import com.hot_dog_app.model.LoginResponse
import com.hot_dog_app.service.PartnerService
import com.hot_dog_app.service.RetrofitAdapter
import com.hot_dog_app.service.SessionService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : ComponentActivity() {

    private val context: Context = this
    private lateinit var session: SessionService

    @SuppressLint("CutPasteId", "InflateParams", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val view: View = layoutInflater.inflate(R.layout.view_login, null);
        setContentView(view);
        val userEditText: EditText = view.findViewById(R.id.partner_user);
        val passwordEditText: EditText = view.findViewById(R.id.partner_password);
        val loginButton: TextView = view.findViewById(R.id.button_login);
        val registerButton: TextView = view.findViewById(R.id.button_register);

        userEditText.setText("manuel@gmail.com");
        passwordEditText.setText("123");

        loginButton.setOnClickListener {
            val service = RetrofitAdapter().getService(PartnerService::class)
            service.login(LoginRequest(email = userEditText.getText().toString().trim(), password = passwordEditText.getText().toString().trim()))?.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val loginResponse = response.body();
                    if (loginResponse != null) {
                        val token = loginResponse.token;
                        session = SessionService(context);
                        session.saveAuthToken(token);
                        val homeActivity = Intent(context, HomeActivity::class.java)
                        startActivity(homeActivity)
                    }else{
                        Toast.makeText(
                            this@LoginActivity,
                            "Credenciales incorrectas. Intente nuevamente",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Credenciales incorrectas. Intente nuevamente",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }

        registerButton.setOnClickListener {
            val registerActivity = Intent(context, RegisterActivity::class.java)
            startActivity(registerActivity)
        }
    }
}