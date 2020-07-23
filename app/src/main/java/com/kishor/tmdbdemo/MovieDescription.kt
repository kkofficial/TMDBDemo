package com.kishor.tmdbdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.kishor.tmdbdemo.databinding.ActivityMovieDescriptionBinding
import com.kishor.tmdbdemo.dataclasses.Results
import kotlinx.android.synthetic.main.activity_movie_description.*
import org.json.JSONObject

class MovieDescription : AppCompatActivity() {
    private lateinit var binding : ActivityMovieDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_movie_description)

        val intent = intent

        val jsonString = intent.getStringExtra("data")
        val jsonObject = JSONObject(jsonString)

        Glide.with(this)
            .load("http://image.tmdb.org/t/p/w185/"+ jsonObject.getString("poster_path"))
            .placeholder(R.drawable.loading)
            .error(R.drawable.noimageavailable)
            .into(poster)

         Glide.with(this)
            .load("http://image.tmdb.org/t/p/w185/"+ jsonObject.getString("backdrop_path"))
             .placeholder(R.drawable.loading)
            .error(R.drawable.noimageavailable)
            .into(backdrop_path)


        val data = Results(
            jsonObject.getString("original_title"),
           "Release Date  : "+ jsonObject.getString("release_date"),
            jsonObject.getString("overview") )

        rating_bar.rating = jsonObject.getString("vote_average").toFloat()

        binding.setVariable(BR.dataclass, data)

    }
}