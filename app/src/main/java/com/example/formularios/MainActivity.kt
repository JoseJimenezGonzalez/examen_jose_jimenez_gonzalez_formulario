package com.example.formularios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.formularios.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btn1.setOnClickListener {
            //Pasamos a actividad añadir pokemon
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            startActivity(intent)
        }
        binding.btn2.setOnClickListener {
            //Pasamos a actividad ver pokemons
            val intent = Intent(this@MainActivity, MainActivity3::class.java)
            startActivity(intent)
        }
    }
}