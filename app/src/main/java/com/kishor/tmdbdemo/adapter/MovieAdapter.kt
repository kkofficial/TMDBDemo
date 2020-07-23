package com.kishor.tmdbdemo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kishor.tmdbdemo.MovieDescription
import com.kishor.tmdbdemo.R
import kotlinx.android.synthetic.main.movie_list_rv_layout.view.*
import org.json.JSONException
import org.json.JSONObject


class MovieAdapter(var context: Context, private val listdata: ArrayList<String>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_rv_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return listdata.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val parent = listdata[position]
        try {
            val jsonObject = JSONObject(parent)
            
            
            holder.title.text = jsonObject.getString("title")

            Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185/"+ jsonObject.getString("poster_path"))
                .placeholder(R.drawable.loading)
                .error(R.drawable.noimageavailable)
                .into(holder.moviePic)

            holder.cvDescriptiom.setOnClickListener {
                val intent = Intent(context, MovieDescription::class.java)
                intent.putExtra("data", jsonObject.toString())
                context.startActivity(intent)
            }

        } catch (err: JSONException) {

        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePic: ImageView = itemView.movie_poster
        val title: TextView = itemView.title
        val cvDescriptiom: CardView = itemView.card_view
    }
}
