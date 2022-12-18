package com.example.extraedge.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.NoConnectionError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.extraedge.R
import com.example.extraedge.adapter.CartItemAdapter
import com.example.extraedge.databinding.ActivityMainBinding
import com.example.extraedge.models.ProductItem
import com.example.extraedge.utils.ApiUrls
import com.example.extraedge.utils.DisplayUtil
import com.example.extraedge.utils.Helper
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity()
{
    private var binding : ActivityMainBinding? = null
    private var cartItemAdapter: CartItemAdapter? = null
    var cartItems : ArrayList<ProductItem>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(getLayoutInflater())
        val view = binding?.root
        setContentView(view)
        initViews()
    }


    private fun initViews() {
        val url: String
        url = ApiUrls.ROCKETS
        getData(url)
    }

    private fun getData(url: String)
    {
        binding!!.rlLoading.visibility = View.VISIBLE
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(Request.Method.GET, url, Response.Listener<String>
        { response ->
            try {
                if (response != null) {

                    val jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length())
                    {
                        val objectRocket: JSONObject = jsonArray.getJSONObject(i)

                        val jsonImageArray = JSONArray(objectRocket.getString("flickr_images"))

                        var rocketImage = ""

                        for (i in 0 until jsonImageArray.length())
                        {
                            rocketImage =  jsonImageArray.get(i).toString()

                        }

                        val first_stageEg = JSONObject(objectRocket.getString("first_stage"))
                        val firstStateEngCnt = first_stageEg.getString("engines")
                        val second_stageEg = JSONObject(objectRocket.getString("second_stage"))
                        val secodStateEngCnt = second_stageEg.getString("engines")
                        val countKey = firstStateEngCnt.toInt() + secodStateEngCnt.toInt()

                        Log.v("firstStateEngCnt = ",""+firstStateEngCnt)
                        Log.v("secodStateEngCnt = ",""+secodStateEngCnt)
                        Log.v("countKey = ",""+countKey)

                        cartItems!!.add(
                            ProductItem(
                                ""+objectRocket.getString("id"),
                                "" + objectRocket.getString("name"),
                                "" + objectRocket.getString("country"),
                                "" + countKey,
                                "" + rocketImage
                            )
                        )

                    }

                    DisplayUtil.applyStatusBarColor(this, R.color.colorPrimary)
                    initCartAdapter()

                    binding!!.rlLoading.visibility = View.GONE

                } else {
                    binding!!.rlLoading.visibility = View.GONE
                }
            } catch (e: JSONException) {
                binding!!.rlLoading.visibility = View.GONE
            }
        }, Response.ErrorListener { error ->
            binding!!.rlLoading.visibility = View.GONE
            if (error is NoConnectionError){
                val builder = AlertDialog.Builder(this)
                builder.setTitle(R.string.action_nocon)
                builder.setMessage(R.string.title_no_connection)
                builder.setPositiveButton("Ok", { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                builder.create().show()
            }
        }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> = java.util.HashMap()
                // val auth = "JWT "//+token  ????
                // headers["Authorization"] = auth
                return headers
            }
        }
        requestQueue.add(stringRequest)
    }

    private fun initCartAdapter() {
        cartItemAdapter = CartItemAdapter(
            cartItems!!,
            this,
            R.color.black,
            2
        )
        initCartItemViews()
    }


    private fun initCartItemViews() {
        val layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding!!.rvProducts.setItemViewCacheSize(20)
        binding!!.rvProducts.setLayoutManager(layoutManager)
        binding!!.rvProducts.adapter = cartItemAdapter
    }

}