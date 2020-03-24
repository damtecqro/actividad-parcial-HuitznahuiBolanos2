package com.test.pokedex.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.test.pokedex.Activities.ItemDetail
import com.test.pokedex.R

class AdapterList :RecyclerView.Adapter<AdapterList.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var data:JsonArray

    fun AdapterList(context:Context,data:JsonArray){
        this.context = context
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterList.ViewHolder {
        var layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_list,parent,false))
    }

    override fun getItemCount(): Int {
        return data.size()
    }

    override fun onBindViewHolder(holder: AdapterList.ViewHolder, position: Int) {
        var item:JsonObject = data.get(position).asJsonObject

        holder.bind(item,context)

    }

    class ViewHolder(view:View):RecyclerView.ViewHolder(view), View.OnClickListener {
        private var imagePokemon: ImageView = view.findViewById(R.id.pokemon_image)
        private var namePokemon: TextView   = view.findViewById(R.id.pokemon_name)
        private lateinit var pokemon_id: String;
        private lateinit var  context: Context;

        init {
            view.setOnClickListener(this)
        }

        fun bind(item:JsonObject,context: Context){
            this.context = context;
            namePokemon.setText(item.get("name").asString)
            this.pokemon_id = item.get("url").asString

            Ion.with(context)
                .load(item.get("url").asString)
                .asJsonObject()
                .done { e, result ->
                    if(e == null){
                        if(!result.get("sprites").isJsonNull){
                            if(result.get("sprites").asJsonObject.get("front_default") != null){
                                Log.i("Salida", result.get("sprites").asJsonObject.get("front_default").asString)

                                Glide
                                    .with(context)
                                    .load(result.get("sprites").asJsonObject.get("front_default").asString)
                                    .placeholder(R.drawable.pokemon_logo_min)
                                    .error(R.drawable.pokemon_logo_min)
                                    .into(imagePokemon);


                            }else{
                                imagePokemon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.pokemon_logo_min))
                            }

                        }else{
                            imagePokemon.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.pokemon_logo_min))
                        }

                    }
                }

        }

        override fun onClick(v: View?) {
//            Log.i("Presionado","Muy presionado")
            var intent: Intent = Intent(this.context,
                ItemDetail::class.java)
            intent.setFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP
                        or Intent.FLAG_ACTIVITY_NEW_TASK
                        or Intent.FLAG_ACTIVITY_SINGLE_TOP)
            intent.putExtra("pokemon_id",this.pokemon_id)

            this.context.startActivity(intent)
        }

    }






}