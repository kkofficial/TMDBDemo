package com.kishor.tmdbdemo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.kishor.tmdbdemo.adapter.MovieAdapter
import com.kishor.tmdbdemo.databinding.ActivityMainBinding
import com.kishor.tmdbdemo.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var model: MovieViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        model  = ViewModelProviders.of(this).get(MovieViewModel::class.java  )



        onclickHandler()
        showTopRatetedMovies()
        initObserver()

    }

    private fun onclickHandler() {


        btnSearch.setOnClickListener{
            if(et_search.text.isNotEmpty()) {

                try {
                    val inputManager: InputMethodManager =getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
                } catch (e: Exception) {
                }
                model.volleyStringRequest(et_search.text.toString().trim(), this,"1","search")
            }else
                Toast.makeText(this,R.string.enter_movie_name, Toast.LENGTH_SHORT).show()
        }

        btn_highest_rated.setOnClickListener {
            model.volleyStringRequest(et_search.text.toString().trim(), this,"1","top_rated")
        }

        btn_most_popular.setOnClickListener {
            model.volleyStringRequest(et_search.text.toString().trim(), this,"1","popular")
        }

    }

    private fun showTopRatetedMovies() {
        model.volleyStringRequest(et_search.text.toString().trim(), this,"1","top_rated")
    }

    private fun initObserver() {

        model.listdata.observe(this, Observer {
            rvMoviesList.apply {
                layoutManager = LinearLayoutManager(
                        this@MainActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                )
                layoutManager = GridLayoutManager(this@MainActivity, 2)

                adapter = MovieAdapter(this@MainActivity, it)
            }

        })
    }
}