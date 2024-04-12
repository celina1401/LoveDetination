package com.example.lovedetinationb2110941.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lovedetinationb2110941.R
import com.example.lovedetinationb2110941.models.UserMap

private const val TAG = "MapsAdapder"
class MapsAdapter(
    val context: Context,
    val userMaps: List<UserMap>,
    val onClickListener: OnClickListener) : RecyclerView
        .Adapter<MapsAdapter.MyViewHolder>() {
        interface OnClickListener{
            fun onItemClick(position: Int)
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =  LayoutInflater.from(context)
            .inflate(R.layout.row_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userMap = userMaps[position]
        val tvTitle = holder.itemView.findViewById<TextView> (R.id.tv_place)
        tvTitle.text = userMap.title
        holder.itemView.setOnClickListener{
            Log.i(TAG, "Click on poition $position")
            onClickListener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int = userMaps.size
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    }

}
