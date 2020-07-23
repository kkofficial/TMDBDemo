package com.kishor.tmdbdemo.viewmodel

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.StringRequest
import com.kishor.tmdbdemo.apiservices.AppSingleton
import com.kishor.tmdbdemo.utils.SharingCode
import org.json.JSONObject

class MovieViewModel : ViewModel() {
    var listdata = MutableLiveData<ArrayList<String>>()
    var data: ArrayList<String> = ArrayList()

    fun volleyStringRequest(movieName: String, context: Context,pagenumber :String,movieType :String) {

        SharingCode.showDailog(context)
        val REQUEST_TAG = "com.kishor.tmdbdemo"

        val strReq =
            StringRequest(getURL(movieName,pagenumber,movieType), Response.Listener { response ->
                Log.d("kishor", response!!)
                val jsonObject = JSONObject(response)

                if (jsonObject.getString("total_pages").equals("0")) {
                    Toast.makeText(
                        context,
                        "Something wen wrong, Check Movie Name",
                        Toast.LENGTH_SHORT
                    ).show()

                    SharingCode.dismissDailog()
                } else {
                    Log.d("result",response.toString())
                    data.clear()
                    listdata.value?.clear()
                    val jsonArray = jsonObject.getJSONArray("results")
                    for (i in 0 until jsonArray.length()) {

                        data.add(jsonArray.getString(i))

                       // setData(data)

                    }

                    listdata.value = data
                    SharingCode.dismissDailog()
                }

            }, Response.ErrorListener { error ->
                VolleyLog.d(ContentValues.TAG, "Error: " + error.message)
                SharingCode.dismissDailog()


            })
        AppSingleton.getInstance(context)?.addToRequestQueue(strReq, REQUEST_TAG)


    }

    private fun getURL(movieName: String, pagenumber: String, movieType: String): String? {

        if(movieType.equals("search")){
            val builder = Uri.Builder()
            builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("search")
                .appendPath("movie")
                .appendQueryParameter("api_key", "00c89f30157db2788e8e7075ee65aa9b")
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("query", movieName)
                .appendQueryParameter("page", pagenumber)
                .appendQueryParameter("include_adult","false")
            return  builder.build().toString()
        }else if(movieType.equals("top_rated")){
            val builder = Uri.Builder()
            builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("top_rated")
                .appendQueryParameter("api_key", "00c89f30157db2788e8e7075ee65aa9b")
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", pagenumber)
            return   builder.build().toString()
        }else if(movieType.equals("popular")){
            val builder = Uri.Builder()
            builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath("popular")
                .appendQueryParameter("api_key", "00c89f30157db2788e8e7075ee65aa9b")
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", pagenumber)
            return   builder.build().toString()
        }else{
            return  null
        }

    }

//    }

}



