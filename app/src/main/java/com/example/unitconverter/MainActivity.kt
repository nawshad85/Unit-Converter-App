package com.example.unitconverter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnitConverterTheme {
                Surface(
                    modifier = Modifier.absolutePadding(0.dp, 20.dp, 0.dp, 0.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter() {
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("") }
    var inputUnit by remember { mutableStateOf("Meters") }
    var outputUnit by remember { mutableStateOf("Meters") }
    var iExpanded by remember { mutableStateOf(false) }
    var oExpanded by remember { mutableStateOf(false) }
    var toPrint by remember { mutableStateOf(false) }

    @SuppressLint("DefaultLocale")
    fun convertUnits() {
        val conversionFactors = mapOf(
            "Meters->Meters" to 1.0,
            "Meters->Centimeters" to 100.0,
            "Meters->Millimeters" to 1000.0,
            "Meters->Feet" to 3.28084,

            "Centimeters->Meters" to 0.01,
            "Centimeters->Centimeters" to 1.0,
            "Centimeters->Millimeters" to 10.0,
            "Centimeters->Feet" to 0.0328084,

            "Millimeters->Meters" to 0.001,
            "Millimeters->Centimeters" to 0.1,
            "Millimeters->Millimeters" to 1.0,
            "Millimeters->Feet" to 0.00328084,

            "Feet->Meters" to 0.3048,
            "Feet->Centimeters" to 30.48,
            "Feet->Millimeters" to 304.8,
            "Feet->Feet" to 1.0
        )
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val key = "$inputUnit->$outputUnit"
        val factor = conversionFactors[key] ?: 1.0
        val result = (inputValueDouble * factor * 100.0).roundToInt() / 100.0
        outputValue = String.format("%.3f", result)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text("Unit Converter")
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Input Value") })
        Spacer(Modifier.height(10.dp))
        Row {
            Box {
                //this is input button
                Button(onClick = {
                    iExpanded = true
                    toPrint = false
                }) {
                    Text("Select")
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = iExpanded, onDismissRequest = { iExpanded = false }) {
                    DropdownMenuItem(text = { Text("Centimeters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Centimeters"
                    })
                    DropdownMenuItem(text = { Text("Meters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Meters"
                    })
                    DropdownMenuItem(text = { Text("Feet") }, onClick = {
                        iExpanded = false
                        inputUnit = "Feet"
                    })
                    DropdownMenuItem(text = { Text("Millimeters") }, onClick = {
                        iExpanded = false
                        inputUnit = "Millimeters"
                    })
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box {
                //this is output button
                Button(onClick = {
                    oExpanded = true
                    toPrint = false
                }) {
                    Text("Select")
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(expanded = oExpanded, onDismissRequest = { oExpanded = false }) {
                    DropdownMenuItem(
                        text = { Text("Centimeters") }, onClick = {
                            oExpanded = false
                            outputUnit = "Centimeters"
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Meters") }, onClick = {
                            oExpanded = false
                            outputUnit = "Meters"
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Feet") }, onClick = {
                            oExpanded = false
                            outputUnit = "Feet"
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Millimeters") }, onClick = {
                            oExpanded = false
                            outputUnit = "Millimeters"
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }
        Row{
            Button(onClick = {
                convertUnits()
                toPrint = true
            }) {
                Text("Convert")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                inputValue = ""
                outputValue = ""
            }){
                Text("Reset")
            }
        }
        if(toPrint){
            Text(
                text = "$inputValue $inputUnit is equal to $outputValue $outputUnit"
            )
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun UnitConverterPreview() {
    UnitConverter()
}