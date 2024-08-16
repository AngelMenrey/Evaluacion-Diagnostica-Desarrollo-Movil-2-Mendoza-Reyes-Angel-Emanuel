package com.example.evaluaciondiagnosticamendozareyesangelemanuel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Consultar : AppCompatActivity() {
    private lateinit var textViewMostrar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consultar)

        textViewMostrar = findViewById(R.id.textViewMostrar)
        val botonRegresar = findViewById<TextView>(R.id.botonRegresar)
        if (!isElementosVacios()) {
            cargarElementosGuardados()
        } else {
            textViewMostrar.text = "No hay datos para mostrar."
        }
        botonRegresar.setOnClickListener {
            val intent = Intent(this, Menu::class.java)
            startActivity(intent)
        }
    }

    private fun cargarElementosGuardados() {
        val elementosSharedPreferences = getSharedPreferences("elementos", MODE_PRIVATE)
        val json = elementosSharedPreferences.getString("elementos_lista", null)

        if (json != null) {
            val gson = Gson()
            val type = object : TypeToken<MutableList<Herramienta>>() {}.type
            val elementos = gson.fromJson<MutableList<Herramienta>>(json, type)

            val textoMostrar = StringBuilder()
            for (herramienta in elementos) {
                textoMostrar.append("Nombre: ${herramienta.nombre}\n")
                textoMostrar.append("Descripción: ${herramienta.descripcion}\n")
                textoMostrar.append("Categoría: ${herramienta.categoria}\n")
                textoMostrar.append("Activo: ${if (herramienta.activo) "Sí" else "No"}\n")
                textoMostrar.append("Precio: ${herramienta.precio}\n\n")
            }

            textViewMostrar.text = textoMostrar.toString()
        }
    }

    private fun isElementosVacios(): Boolean {
        val elementosSharedPreferences = getSharedPreferences("elementos", MODE_PRIVATE)
        val json = elementosSharedPreferences.getString("elementos_lista", null)
        return json.isNullOrEmpty()
    }
}
