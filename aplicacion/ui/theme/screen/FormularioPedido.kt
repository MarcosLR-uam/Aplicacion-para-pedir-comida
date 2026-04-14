package com.example.aplicacion.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aplicacion.ui.theme.AplicacionTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun  FormularioPedido() {

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var producto by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }

    var cargando by remember { mutableStateOf(false) }
    var mensajeEstado by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


    val nombreError = nombre.isBlank() || nombre.length < 3 // las validaciones
    val telefonoError = telefono.isBlank() ||
            telefono.length < 8 ||
            !telefono.all { it.isDigit() }

    val direccionError = direccion.isBlank()
    val productoError = producto.isBlank()

    val cantidadNumero = cantidad.toIntOrNull()
    val cantidadError = cantidad.isBlank() || cantidadNumero == null || cantidadNumero <= 0

    val formularioValido = !nombreError &&
            !telefonoError &&
            !direccionError &&
            !productoError &&
            !cantidadError

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Pedido de comida Mexicana",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del cliente") },
                isError = nombreError,
                supportingText = {
                    if (nombreError) {
                        Text("Primer nomobre y primer apellido")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = telefono,
                onValueChange = { telefono = it },
                label = { Text("Teléfono") },
                isError = telefonoError,
                supportingText = {
                    if (telefonoError) {
                        Text("Digite si numero telefonico")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), // esto es para que el numerico el teclado
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = direccion,
                onValueChange = { direccion = it },
                label = { Text("Dirección") },
                isError = direccionError,
                supportingText = {
                    if (direccionError) {
                        Text("La dirección es obligatoria")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = producto,
                onValueChange = { producto = it },
                label = { Text("Producto") },
                isError = productoError,
                supportingText = {
                    if (productoError) {
                        Text("El producto es obligatorio")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = cantidad,
                onValueChange = { cantidad = it },
                label = { Text("Cantidad") },
                isError = cantidadError,
                supportingText = {
                    if (cantidadError) {
                        Text("Debe ser un número mayor que 0")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )


            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        scope.launch {
                            cargando = true
                            mensajeEstado = ""

                            delay(2000)

                            cargando = false
                            mensajeEstado = "Pedido enviado correctamente"

                            snackbarHostState.showSnackbar(
                                message = "Pedido enviado con éxito"
                            )

                            nombre = ""
                            telefono = ""
                            direccion = ""
                            producto = ""
                            cantidad = ""
                        }
                    },
                    enabled = formularioValido && !cargando,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Enviar")
                }

                TextButton(
                    onClick = {
                        nombre = ""
                        telefono = ""
                        direccion = ""
                        producto = ""
                        cantidad = ""
                        mensajeEstado = ""
                    },
                    enabled = !cargando,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Limpiar")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))


            if (mensajeEstado.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = mensajeEstado,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewFormularioPedido() {
    AplicacionTheme {
        FormularioPedido()
    }
}
