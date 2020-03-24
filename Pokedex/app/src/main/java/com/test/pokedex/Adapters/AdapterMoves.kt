package com.test.pokedex.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.test.pokedex.R

class AdapterMoves: RecyclerView.Adapter<AdapterMoves.MovesHolder> () {
    private lateinit var context: Context
    private lateinit var data:JsonArray

    fun AdapterMoves(context: Context, data: JsonArray){
        this.context = context
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovesHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return MovesHolder(layoutInflater.inflate(R.layout.moves_view,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size()
    }

    override fun onBindViewHolder(holder: MovesHolder, position: Int) {
        var item: JsonObject = data.get(position).asJsonObject
        holder.bind(item)
    }

    class MovesHolder(view:View): RecyclerView.ViewHolder(view) {
        private var move_element: TextView = view.findViewById(R.id.move_element)


        fun bind(item:JsonObject){
            move_element.setText(item.get("move").asJsonObject.get("name").toString())
        }


    }
}