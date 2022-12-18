package com.example.extraedge.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.NoConnectionError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.extraedge.R
import com.example.extraedge.adapter.ViewPagerAdapter
import com.example.extraedge.databinding.ActivityRocketDetailsBinding
import com.example.extraedge.models.ProductItem
import com.example.extraedge.utils.ApiUrls
import com.example.extraedge.utils.DisplayUtil
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class RocketDetails : AppCompatActivity()
{
    private var binding : ActivityRocketDetailsBinding? = null
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var imageList: List<String>
    private  var proId: String? = null
    private  var proName: String? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityRocketDetailsBinding.inflate(getLayoutInflater())
        val view = binding?.root
        setContentView(view)

        initViews()
        initActions()

    }

    private fun initViews() {
        DisplayUtil.applyStatusBarColor(this, R.color.black)

        if ( intent.getStringExtra("proId") != null){
            proId = intent.getStringExtra("proId")
        }

        if ( intent.getStringExtra("proName") != null){
            proName = intent.getStringExtra("proName")
        }

        val url: String
        url = ApiUrls.ROCKETS_DETAILS
        getData(url)

        binding!!.tvRocketName.text = proName

    }

    private fun initActions()
    {
        binding!!.ivBack.setOnClickListener({
            onBackPressed()
        })

        binding!!.tvWikiLink.setOnClickListener({
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(""+binding!!.tvWikiLink.text))
            startActivity(browserIntent)
        })
    }

    private fun getData(url: String)
    {
        binding!!.rlLoading.visibility = View.VISIBLE
        val requestQueue = Volley.newRequestQueue(this)
        val stringRequest: StringRequest = object : StringRequest(Request.Method.GET, url+""+proId, Response.Listener<String>
        { response ->
            try {
                if (response != null)
                {

                    val jsonObj = JSONObject(response)

                    val jsonHeight = JSONObject(jsonObj.getString("height"))
                    val hightMeter = jsonHeight.getString("meters")
                    val hightFeet = jsonHeight.getString("feet")

                    val jsonDiameter = JSONObject(jsonObj.getString("diameter"))
                    val diaMeter = jsonDiameter.getString("meters")
                    val diaFeet = jsonDiameter.getString("feet")

                    val jsonImageArray = JSONArray(jsonObj.getString("flickr_images"))

                    imageList = ArrayList<String>()

                    for (i in 0 until jsonImageArray.length())
                    {
                        imageList = imageList + jsonImageArray.get(i).toString()

                    }

                    viewPagerAdapter = ViewPagerAdapter(this, imageList)
                    binding!!.viewPager.adapter = viewPagerAdapter

                    binding!!.tvActiveStat.text = jsonObj.getString("active")
                    binding!!.tvDesc.text = jsonObj.getString("description")
                    binding!!.tvCost.text = jsonObj.getString("cost_per_launch")
                    binding!!.tvSuccessRate.text = jsonObj.getString("success_rate_pct")
                    binding!!.tvHeightPer.text = "Meters : "+hightMeter+", Feet : "+hightFeet
                    binding!!.tvDiameterPer.text = "Meters : "+diaMeter+", Feet : "+diaFeet
                    binding!!.tvWikiLink.text = jsonObj.getString("wikipedia")


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
}