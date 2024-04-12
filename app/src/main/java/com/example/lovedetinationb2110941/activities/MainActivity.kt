package com.example.lovedetinationb2110941.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lovedetinationb2110941.R
import com.example.lovedetinationb2110941.adapter.MapsAdapter
import com.example.lovedetinationb2110941.databinding.ActivityMainBinding
import com.example.lovedetinationb2110941.models.Place
import com.example.lovedetinationb2110941.models.UserMap
import com.example.lovedetinationb2110941.objects.Utils

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var userMaps: MutableList<UserMap>
    lateinit var mapsAdapter: MapsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Khởi tạo dữ liệu:
        userMaps = generateSimpleData().toMutableList()
        // Tao layout manager cho recycler view
        binding.rvMaps.layoutManager = LinearLayoutManager(this)
        // Tao Adapter cho recycler view
        mapsAdapter = MapsAdapter(this, userMaps,
            object: MapsAdapter.OnClickListener{
                override fun onItemClick(position: Int) {
                    Log.i(TAG, "onItemClick $position")
                    val intent = Intent(this@MainActivity, DisplayMapActivity::class.java)
                    intent.putExtra(Utils.EXTRA_USER_MAP, userMaps[position])
                    startActivity(intent)
                }
            })
        binding.rvMaps.adapter = mapsAdapter
        binding.floatBtnAdd.setOnClickListener{
            val mapFormView =
                LayoutInflater.from(this).inflate(R.layout.dialog_create_map, null)
            AlertDialog.Builder(this).setTitle("Map title")
                .setView(mapFormView)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK"){
                    _,_ ->
                    val _title =
                        mapFormView.findViewById<EditText>(R.id.et_title_map).text.toString()
                    if(_title.trim().isEmpty()){
                        Toast.makeText(this, "Fill out title", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }
                    val intent = Intent(this@MainActivity, CreateMapActivity::class.java)
                    intent.putExtra(Utils.EXTRA_MAP_TITLE,_title)
                    getResult.launch(intent)
                }
                .show()
        }
    }

    private fun generateSimpleData() : List<UserMap>{
        return listOf(
            UserMap("Đại học Cần Thơ",
                listOf(
                    Place ("Trường CNTT&TT", "thuộc ĐH Cần Thơ",10.0308541,105.768986),
                    Place("Trường Nông Nghiệp", "thuộc ĐH Cần Thơ", 10.0302655,105.7679642),
                )
            ),
            UserMap("Quán Cafe chạy Deadline thầy Trí",
                listOf(
                    Place("Highland", "đường Mậu Thân", 10.0327649,105.7717592),
                    Place("Pi Kafe'", "Hẻm 51",10.0376643,105.7628787),
                    Place("The 80s", "đường Mạc Thiên Tích", 10.0288849,105.7582326)
                )
            )
        )
    }

    private val getResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            val usermap = it.data?.getSerializableExtra(Utils.EXTRA_USER_MAP) as UserMap
            userMaps.add(usermap)
            mapsAdapter.notifyItemInserted(userMaps.size-1)
            Log.i(TAG,usermap.title)
        }
    }
}