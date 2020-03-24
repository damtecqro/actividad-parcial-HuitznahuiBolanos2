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

class AdapterTypes: RecyclerView.Adapter<AdapterTypes.TypesHolder> () {
    private lateinit var context: Context
    private lateinit var data:JsonArray

    fun AdapterTypes(context: Context, data: JsonArray){
        this.context = context
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypesHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return TypesHolder(layoutInflater.inflate(R.layout.types_view,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size()
    }

    override fun onBindViewHolder(holder: TypesHolder, position: Int) {
        var item: JsonObject = data.get(position).asJsonObject
        holder.bind(item)
    }

    class TypesHolder(view:View): RecyclerView.ViewHolder(view) {
        private var type_element: TextView = view.findViewById(R.id.type_element)


        fun bind(item:JsonObject){
            type_element.setText(item.get("type").asJsonObject.get("name").toString())
        }


    }
}