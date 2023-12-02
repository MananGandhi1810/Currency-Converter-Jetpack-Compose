package tech.manangandhi.currencyconverter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import tech.manangandhi.currencyconverter.ui.theme.CurrencyConverterTheme
import tech.manangandhi.currencyconverter.viewmodels.CurrencyViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val viewmodel = hiltViewModel<CurrencyViewModel>()
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CurrencyDropdown(
                                    value = viewmodel.from,
                                    onChanged = {
                                        viewmodel.from = it
                                        Log.d("MainActivity", "From Currency: $it")
                                    },
                                )
                                CurrencyDropdown(
                                    value = viewmodel.to,
                                    onChanged = {
                                        viewmodel.to = it
                                        Log.d("MainActivity", "To Currency: $it")
                                    },
                                )
                            }
                            CurrencyTextField(
                                value = viewmodel.amount,
                                onValueChanged = {
                                    viewmodel.amount = it
                                    Log.d("MainActivity", "Amount: $it")
                                },
                            )
                            TextButton(
                                onClick = {
                                    viewmodel.exchange()
                                    Log.d("MainActivity", "Exchange: ${viewmodel.result}")
                                },
                            ) {
                                Text("Convert")
                            }
                            viewmodel.isLoading.let {
                                if (it) {
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .padding(16.dp),
                                        strokeWidth = ProgressIndicatorDefaults.CircularStrokeWidth,
                                    )
                                } else {
                                    Text("Result: ${viewmodel.result}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CurrencyDropdown(
    value: String = "USD",
    onChanged: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf(
        "SGD",
        "MYR",
        "EUR",
        "USD",
        "AUD",
        "JPY",
        "CNH",
        "HKD",
        "CAD",
        "INR",
        "DKK",
        "GBP",
        "RUB",
        "NZD",
        "MXN",
        "IDR",
        "TWD",
        "THB",
        "VND",
    )
    var selected by remember { mutableStateOf(value) }

    Box {
        TextButton(
            onClick = {
                expanded = true
            },
        ) {
            Text(selected)
            Icon(
                Icons.Outlined.KeyboardArrowDown, contentDescription = null
            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    text = { Text(s) },
                    onClick = {
                        selected = s
                        expanded = false
                        onChanged(s)
                    },
                )
            }
        }
    }
}

@Composable
fun CurrencyTextField(
    value: Double = 1.0,
    onValueChanged: (Double) -> Unit = {}
) {
    var text by remember { mutableStateOf(value.toString()) }

    TextField(
        modifier = Modifier
            .padding(16.dp),
        supportingText = { Text("Amount") },
        value = text,
        onValueChange = {
            text = it
            if (it.isEmpty()) {
                onValueChanged(0.0)
            } else {
                onValueChanged(it.toDouble())
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CurrencyTextField(
        value = 10.0,
        onValueChanged = {
            Log.d("MainActivity", "Amount: $it")
        },
    )
}
