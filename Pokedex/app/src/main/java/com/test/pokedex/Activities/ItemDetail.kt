package com.test.pokedex.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.test.pokedex.Adapters.AdapterMoves
import com.test.pokedex.Adapters.AdapterTypes
import com.test.pokedex.R
import kotlinx.android.synthetic.main.activity_item_detail.*


class ItemDetail : AppCompatActivity() {

    private lateinit var data: JsonObject
    private  var url: String? = ""
    private lateinit var id: TextView
    private lateinit var name: TextView
    private lateinit var sprite: ImageView
    private lateinit var height: TextView
    private lateinit var weight: TextView
    private lateinit var moves: TextView

    private lateinit var typesLayoutManager: LinearLayoutManager
    private lateinit var movesLayoutManager: LinearLayoutManager

    private lateinit var adapterTypes: AdapterTypes
    private lateinit var adapterMoves: AdapterMoves

    private lateinit var typesData: JsonArray
    private lateinit var movesData: JsonArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(toolbar)

        manageIntent(this.intent)

        initializeComponents()
        initializeListeners()
        initializeData()
    }

    fun manageIntent(intent: Intent?) {
        if (intent != null) {
            url = intent.getStringExtra("pokemon_id")
        }
    }

    fun initializeComponents(){
        id  = findViewById(R.id.number)
        name = findViewById(R.id.name)
        sprite = findViewById(R.id.sprite)
        height = findViewById(R.id.height)
        weight = findViewById(R.id.weight)
        moves = findViewById(R.id.moves)

        typesLayoutManager = LinearLayoutManager(this)
        movesLayoutManager = LinearLayoutManager(this)

        adapterTypes = AdapterTypes()
        adapterMoves = AdapterMoves()
    }

    fun initializeListeners(){

    }

    fun initializeData(){
        Log.i("Bandera","Initializing data")
        Ion.with(this)
            .load(url)
            .asJsonObject()
            .done { e, result ->
                if(e == null){
                    Log.i("Done", "True")
                    data = result
                    Log.i("Nombre",data.get("name").toString())
                    bind()
                }
            }
    }

    fun bind() {
        name.setText(data.get("name").toString())
        id.setText(data.get("id").toString() + ":")
        height.setText("Altura: " + data.get("height").toString())
        weight.setText("Peso: " + data.get("weight").toString())
        Glide
            .with(this)
            .load(data.get("sprites").asJsonObject.get("front_default").asString)
            .placeholder(R.drawable.pokemon_logo_min)
            .error(R.drawable.pokemon_logo_min)
            .into(sprite)
        typesData = data.get("types").asJsonArray
        movesData = data.get("moves").asJsonArray

        adapterTypes.AdapterTypes(this, typesData)
        adapterMoves.AdapterMoves(this,movesData)

        typesList.layoutManager = typesLayoutManager
        typesList.adapter = adapterTypes

        moveList.layoutManager = movesLayoutManager
        moveList.adapter = adapterMoves
    }

}
