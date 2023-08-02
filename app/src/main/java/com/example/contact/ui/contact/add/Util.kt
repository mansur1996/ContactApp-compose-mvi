package com.example.contact.ui.contact.add

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyNameTextField(text: MutableState<String>, hint: String) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        onValueChange = { if (it.length < 26) text.value = it },
        placeholder = { Text(hint) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPhoneTextField(text: MutableState<String>, hint: String) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = text.value,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
        onValueChange = { if (it.length < 14) text.value = it },
        placeholder = { Text(hint) }
    )
}