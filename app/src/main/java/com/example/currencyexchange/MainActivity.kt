package com.example.currencyexchange

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.currencyexchange.databinding.ActivityMainBinding
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    // SET VARIABLE BINDING TO MAIN ACTIVITY BINDING
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //
        binding = ActivityMainBinding.inflate(layoutInflater)

        // SET CONTENT VIEW TO BINDING THE ROOT
        setContentView(binding.root)

        // CALL FUNCTION
        fetchCurrencyData().start()
    }

    // FETCH DATA FROM API
    @SuppressLint("SetTextI18n")
    private fun fetchCurrencyData(): Thread
    {
        return Thread {

            // PLACE API URL IN A VARIABLE
            val url = URL("https://open.er-api.com/v6/latest/aud")

            // USE VARIABLE CONNECTION AS HTTPS CONNECTION
            val connection = url.openConnection() as HttpsURLConnection

            // IF CONNECTION RESPONSE IS EQUAL TO 200 (OK)
            if (connection.responseCode == 200) {

                //
                val inputSystem = connection.inputStream

                // THEN LOG (LOGCAT) RESULTS
                // println(inputSystem.toString())

                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request::class.java)

                // UPDATE OUR FUNCTION UI REQUEST
                updateUI(request)

                inputStreamReader.close()
                inputSystem.close()
            }
            else {
                // ELSE LOG "FAILED TO CONNECT"
                binding.baseCurrency.text = "Failed to Connect!"
            }
        }
    }

    private fun updateUI(request: Request) {
        //
        runOnUiThread {
            kotlin.run {
                binding.lastUpdated.text = request.time_last_update_utc
                binding.nzd.text = String.format("NZD: %.2f", request.rates.NZD)
                binding.usd.text = String.format("USD: %.2f", request.rates.USD)
                binding.gbp.text = String.format("GBP: %.2f", request.rates.GBP)
                binding.eur.text = String.format("EUR: %.2f", request.rates.EUR)
                binding.cad.text = String.format("CAD: %.2f", request.rates.CAD)
                binding.mxn.text = String.format("MXN: %.2f", request.rates.MXN)

            }
        }
    }
}