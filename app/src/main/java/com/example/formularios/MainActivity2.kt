package com.example.formularios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.Toast
import com.example.formularios.databinding.ActivityMain2Binding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity2 : AppCompatActivity() {


    private lateinit var binding: ActivityMain2Binding
    //Esta es para guardar la opcion seleccionada del spinner
    var opcionSeleccionada: String = ""

    var esNombrePokemonValido = false
    var esNombreEntrenadorValido = false
    var esEstaturaValida = false
    var esFechaValida = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        setupTextListeners()

        setupBotonAdd()

        //Configurar el spinner
        //Obtenemos el spinner
        val spinner = findViewById<Spinner>(R.id.spinner)
        //Creamos un array con el que vamos a inflar al spinner
        val opciones = arrayOf("Lucha", "Normal", "Planta", "Roca", "Siniestro", "Tierra", "Veneno", "Volador", "Bicho", "Agua", "Acero")
        // Crear un ArrayAdapter utilizando la lista de opciones y el diseño predeterminado
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, opciones)
        // Aplicar el adaptador al Spinner
        spinner.adapter = adapter

        // Configurar un listener para manejar la selección de elementos en el Spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                // Obtener la opción seleccionada
                val selectedItem = opciones[position]

                // Asignar la opción seleccionada
                opcionSeleccionada = selectedItem

                // Mostrar la opción seleccionada
                Toast.makeText(this@MainActivity2, "Seleccionaste: $selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Manejar caso en que no se selecciona nada
            }
        }

        //Volver a la principal
        binding.ivBack.setOnClickListener {
            //Pasamos a la siguiente actividad
            val intent = Intent(this@MainActivity2, MainActivity::class.java)
            startActivity(intent)
        }


    }

    private fun setupBotonAdd() {
        binding.ivAdd.setOnClickListener {
            if(esEstaturaValida && esFechaValida && esNombrePokemonValido && esNombreEntrenadorValido){
                val nombrePokemon = binding.tietNombrePokemon.text.toString()
                val estaturaPokemon = binding.tietEstatura.text.toString()
                val intent = Intent(this@MainActivity2, MainActivity3::class.java)
                intent.putExtra("nombre", nombrePokemon)
                intent.putExtra("tipo", opcionSeleccionada)
                intent.putExtra("estatura", estaturaPokemon)
                Toast.makeText(this, "Pokemon añadido", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Falla algo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupTextListeners() {
        //Nombre de pokemon
        //Nombre: La primera letra debe empezar por mayúscula o almacenarse con
        //la primera letra mayúscula y contener al menos 3 caracteres.
        binding.tietNombrePokemon.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            //Este es el que vamos a modificar
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val texto: String = p0.toString()
                //Tenemos que comprobar que es válido
                validarNombrePokemon(texto)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.tietNombreEntranador.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            //Este es el que vamos a modificar
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val texto: String = p0.toString()
                //Tenemos que comprobar que es válido
                validarNombreEntrenador(texto)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        binding.tietEstatura.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            //Este es el que vamos a modificar
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val texto: String = p0.toString()
                //Tenemos que comprobar que es válido
                validarEstatura(texto)
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })

        //Fecha de nacimiento
        binding.tietFechaAtrapado.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            val picker = builder.build()

            picker.addOnPositiveButtonClickListener { selectedDateInMillis ->
                val selectedDate = Date(selectedDateInMillis)
                val currentDate = Date()

                if(currentDate.after(selectedDate)){
                    esFechaValida = true
                    val fechaCapturaFormateada = obtenerFechaNacimientoFormateada(selectedDate)
                    binding.tietFechaAtrapado.setText(fechaCapturaFormateada)
                }else{
                    Toast.makeText(this, "Fecha captura pokemon erronea", Toast.LENGTH_SHORT).show()
                    esFechaValida = false
                }
            }

            picker.show(supportFragmentManager, "jeje")
        }


    }

    private fun validarEstatura(texto: String) {
        //Estatura: Debe ser un número entero medido en cm
        var sonSoloDigitos = texto.all { it.isDigit() }
        if(sonSoloDigitos){
            binding.tilEstatura.error = null //Borra el mensaje de error si lo hubiera de antes
            esEstaturaValida = true
        }else{
            esEstaturaValida = false
            binding.tilEstatura.error = "Tiene que ser numero entero"
        }
    }

    //Entrenador: Debe tener al menos una vocal y no podrá tener más de 25
    //caracteres.
    private fun validarNombreEntrenador(texto: String) {
        var contieneVocal = contieneVocales(texto)
        if(contieneVocal && texto.length <= 25){
            binding.tilNombreEntrenador.error = null //Borra el mensaje de error si lo hubiera de antes
            esNombreEntrenadorValido = true
        }else{
            esNombreEntrenadorValido = false
            binding.tilNombreEntrenador.error = "Tiene que tener vocal y no puede superar 25 caracteres"
        }
    }

    private fun validarNombrePokemon(texto: String) {
        if(texto[0].isUpperCase() && texto.length >= 3){
            binding.tilNombrePokemon.error = null //Borra el mensaje de error si lo hubiera de antes
            esNombrePokemonValido = true
        }else{
            esNombrePokemonValido = false
            binding.tilNombrePokemon.error = "Debe tener minimo 3 caracteres"
        }
    }
    // Función para obtener la fecha de nacimiento en formato yyyy/MM/dd
    fun obtenerFechaNacimientoFormateada(fechaNacimiento: Date): String {
        val formato = SimpleDateFormat("dd/mm/yy", Locale.getDefault())
        return formato.format(fechaNacimiento)
    }
    fun contieneVocales(texto: String): Boolean {
        val patron = Regex("[aeiouAEIOU]")
        return patron.containsMatchIn(texto)
    }
}