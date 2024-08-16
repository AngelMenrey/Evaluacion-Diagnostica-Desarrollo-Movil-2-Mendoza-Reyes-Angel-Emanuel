package com.example.evaluaciondiagnosticamendozareyesangelemanuel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Registrar : AppCompatActivity() {

    private lateinit var nombreEditText: EditText
    private lateinit var descripcionEditText: EditText
    private lateinit var categoriaSpinner: Spinner
    private lateinit var activoCheckBox: CheckBox
    private lateinit var precioEditText: EditText
    private lateinit var registrarButton: Button
    private lateinit var botonRegresar: Button

    private lateinit var elementosSharedPreferences: SharedPreferences
    private lateinit var elementosEditor: SharedPreferences.Editor
    private var elementos: MutableList<Herramienta> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        nombreEditText = findViewById(R.id.editTextNombre)
        descripcionEditText = findViewById(R.id.editTextDescripcion)
        categoriaSpinner = findViewById(R.id.spinnerCategorias)
        activoCheckBox = findViewById(R.id.checkBoxActivo)
        precioEditText = findViewById(R.id.editTextPrecio)
        registrarButton = findViewById(R.id.botonRegistrar)
        botonRegresar = findViewById(R.id.botonRegresar)

        elementosSharedPreferences = getSharedPreferences("elementos", Context.MODE_PRIVATE)
        elementosEditor = elementosSharedPreferences.edit()

        cargarElementosGuardados()

        val categorias = arrayOf("Construcción", "Electricidad", "Plomería", "Jardinería", "Pintura")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categoriaSpinner.adapter = adapter

        registrarButton.setOnClickListener {
            val nombre = nombreEditText.text.toString()
            val descripcion = descripcionEditText.text.toString()
            val categoria = categoriaSpinner.selectedItem.toString()
            val activo = activoCheckBox.isChecked
            val precio = precioEditText.text.toString()

            if (nombre.isNotBlank() && descripcion.isNotBlank() && precio.isNotBlank()) {
                val nuevaHerramienta = Herramienta(nombre, descripcion, categoria, activo, precio)
                elementos.add(nuevaHerramienta)
                guardarElementos()
                Toast.makeText(this, "Se agregó correctamente la información", Toast.LENGTH_SHORT).show()
                limpiarCampos()
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        botonRegresar.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
    }

    private fun limpiarCampos() {
        nombreEditText.text.clear()
        descripcionEditText.text.clear()
        categoriaSpinner.setSelection(0)
        activoCheckBox.isChecked = false
        precioEditText.text.clear()
    }

    private fun cargarElementosGuardados() {
        val json = elementosSharedPreferences.getString("elementos_lista", null)
        val gson = Gson()
        val type = object : TypeToken<MutableList<Herramienta>>() {}.type
        elementos = gson.fromJson(json, type) ?: mutableListOf()
    }

    private fun guardarElementos() {
        val gson = Gson()
        val json = gson.toJson(elementos)
        elementosEditor.putString("elementos_lista", json)
        elementosEditor.apply()
    }
}
