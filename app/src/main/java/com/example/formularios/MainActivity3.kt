package com.example.formularios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.formularios.databinding.ActivityMain2Binding
import com.example.formularios.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Me traigo los datos de la otra actividad
        val nombrePokemon = intent.getStringExtra("nombre")
        val tipoPokemon = intent.getStringExtra("tipo")
        val estaturaPokemon = intent.getStringExtra("estatura")

        // Añade los datos al TextView
        binding.tvListaPokemons.text = "Nombre: $nombrePokemon\nTipo: $tipoPokemon\nEstatura pokemon: $estaturaPokemon\n\n\n"
    }
}