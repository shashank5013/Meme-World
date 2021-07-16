package com.example.android.memeworld

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.android.memeworld.databinding.ActivityMainBinding
import org.json.JSONObject

const val url="https://meme-api.herokuapp.com/gimme/20"
class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        binding= ActivityMainBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var list: ArrayList<MemeClass>


        /** Networking requests start */
        val queue= Volley.newRequestQueue(this)

        val request=JsonObjectRequest(Request.Method.GET,
            url,
            null,
            {
                //Action when we get the response
                binding.shimmerLayout.stopShimmer()
                binding.shimmerLayout.visibility= View.GONE
                list=getMemeList(it)
                binding.rv.apply {
                    visibility=View.VISIBLE
                    layoutManager=LinearLayoutManager(this@MainActivity)
                    adapter=MemeAdapter(this@MainActivity,list)
                }
            },
            {
                //Toast message if there is error in response
                Toast.makeText(this,"Error Occurred",Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(request)




    }

    /**
     * Function which converts json response received  into Memes Object
     */
    private fun getMemeList(response: JSONObject): ArrayList<MemeClass> {
        val list=ArrayList<MemeClass>()
        val count=response.getInt("count")
        val array=response.getJSONArray("memes")
        for(i in 0 until count){
            val meme=array.getJSONObject(i)
            val subreddit=meme.getString("subreddit")
            val title=meme.getString("title")
            val imageUrl=meme.getString("url")
            val postLink=meme.getString("postLink")
            val author=meme.getString("author")
            val likeCount=meme.getLong("ups")
            val temporary=MemeClass(subreddit,author,title,imageUrl,likeCount,postLink)
            list.add(temporary)
        }

        return list
    }


    /**
     * Clears the cache memory whenever on destroy is called
     */
    override fun onDestroy() {
        super.onDestroy()
        Glide.get(this).clearMemory()
    }
}