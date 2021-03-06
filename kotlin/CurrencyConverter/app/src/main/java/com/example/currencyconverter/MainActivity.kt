package com.example.currencyconverter

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getRates(view: View)
    {
        val download=Download()

        try
        {
            val url ="http://data.fixer.io/api/latest?access_key=7ab3657b31fc5922f0df5b0412f47a03&format=1"
            download.execute(url)
        }

        catch (e: Exception)
        {

        }


    }

    inner class Download : AsyncTask<String,Void,String>()
    {
        override fun doInBackground(vararg params: String?): String {

            var result = ""

            var url : URL
            var httpURLConnection : HttpURLConnection

            try
            {

                url = URL(params[0])
                httpURLConnection=url.openConnection() as HttpURLConnection

                val inputStream =httpURLConnection.inputStream
                val inputStreamReader=InputStreamReader(inputStream)

                var data =inputStreamReader.read()

                while (data > 0)
                {
                    val character=data.toChar()

                    result+= character

                    data=inputStreamReader.read()

                }

                return result

            }
            catch(e: Exception)
            {
                e.printStackTrace()

                return result

            }



        }

        override fun onPostExecute(result: String?) {

            try
            {
                val jsonObject=JSONObject(result)
                val base = jsonObject.getString("base")
                println(base)
                val rates=jsonObject.getString("rates")
                println(rates)


                val jsonObject1=JSONObject(rates)
                val turkishLira=jsonObject1.getString("TRY")
                println(turkishLira)

                val cad =jsonObject1.getString("CAD")
                val gbp =jsonObject1.getString("GBP")
                val usd =jsonObject1.getString("USD")
                val chf=jsonObject1.getString("CHF")

                tryText.text="TRY : "+turkishLira
                cadText.text="CAD : "+cad
                gbpText.text="GBP : "+gbp
                usdText.text="USD : "+usd
                chfText.text="CHF : "+chf




            }
            catch (e:Exception)
            {

            }


            super.onPostExecute(result)
        }


    }
}
