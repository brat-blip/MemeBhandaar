package com.example.memebhandaar

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    var currentimgurl : String? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadmeme ()

        findViewById<Button>(R.id.nextbtn).setOnClickListener(){
            nextmeme()
        }
        findViewById<Button>(R.id.sharebtn).setOnClickListener(){
            sharememe()
        }


    }



      private  fun loadmeme (){

           findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
            // Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
            val url  = "https://meme-api.com/gimme"


            val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->
                    currentimgurl = response.getString("url")
                      val memeView = findViewById<ImageView>(R.id.memeview)
                      Glide.with(this).load(currentimgurl ).listener(object : RequestListener<Drawable>{
                          override fun onLoadFailed(
                              e: GlideException?,
                              model: Any?,
                              target: Target<Drawable>?,
                              isFirstResource: Boolean
                          ): Boolean {

                              findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                              return false
                          }

                          override fun onResourceReady(
                              resource: Drawable?,
                              model: Any?,
                              target: Target<Drawable>?,
                              dataSource: DataSource?,
                              isFirstResource: Boolean
                          ): Boolean {
                              findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                              return false

                          }

                      }).into(memeView)
                  },
                {
                    // TODO: Handle error
                    Toast.makeText(this,"something went wrong",Toast.LENGTH_SHORT).show()
                }
            )

            // Add the request to the RequestQueue.
            queue.add(jsonObjectRequest)

        }
        fun nextmeme(){
          loadmeme()
        }
        fun sharememe(){
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"hey checkoout this cool memes : $currentimgurl")
            val chooser =Intent.createChooser(intent,"shared memes on  ")
            startActivity(chooser)
    }
}