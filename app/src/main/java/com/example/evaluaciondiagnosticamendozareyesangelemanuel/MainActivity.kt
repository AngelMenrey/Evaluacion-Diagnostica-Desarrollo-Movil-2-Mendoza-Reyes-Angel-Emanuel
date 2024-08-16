package com.example.evaluaciondiagnosticamendozareyesangelemanuel

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {

    private val credenciales = arrayOf(
        Credencial("admin@gmail.com", "12345"),
        Credencial("angel@gmail.com", "12345"),
        Credencial("emanuel@gmail.com", "12345"),
        Credencial("mendoza@gmail.com","12345"),
        Credencial("reyes@gmail.com","12345")

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val entradaTexto = findViewById<EditText>(R.id.entradaTexto)
        val campoTexto = findViewById<EditText>(R.id.campoTexto)
        val botonIngresar = findViewById<Button>(R.id.botonIngresar)
        val botonSalir = findViewById<Button>(R.id.botonSalir)

        botonIngresar.setOnClickListener {
            val correoElectronico = entradaTexto.text.toString()
            val contraseña = campoTexto.text.toString()

            if (correoElectronico.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val credencialValida = credenciales.find { it.correo == correoElectronico && it.contraseña == contraseña }

            if (credencialValida != null) {
                val intent = Intent(this, Menu::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        botonSalir.setOnClickListener {
            limpiarDatos()
            finishAffinity()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        limpiarDatos()
    }

    private fun limpiarDatos() {
        val elementosSharedPreferences = getSharedPreferences("elementos", Context.MODE_PRIVATE)
        val editor = elementosSharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
    data class Credencial(val correo: String, val contraseña: String)
}
