package com.example.contact.ui.contact.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.contact.R
import com.example.contact.ui.contact.list.ContactViewModel
import com.example.contact.ui.contact.list.MainIntent
import com.example.domain.models.Contact
import kotlinx.coroutines.launch

data class AddContactScreen(val contact: Contact? = null, val isAdd : Boolean = true) : Screen {
    @Composable
    override fun Content() {
        val navigation = LocalNavigator.currentOrThrow
        val viewModel = hiltViewModel<ContactViewModel>()
        val scope = rememberCoroutineScope()
        val name = remember { mutableStateOf("") }
        val phoneNumber = remember { mutableStateOf("") }

        LaunchedEffect(Unit) {
            contact?.let {
                name.value = it.name ?: ""
                phoneNumber.value = it.phoneNumber ?: ""
            }
        }

        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.standard))) {
            MyNameTextField(text = name, hint = "Full Name")
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.standard)))
            MyPhoneTextField(text = phoneNumber,  hint = "Phone Number")
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.standard)))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                val newContact = Contact(name = name.value, phoneNumber = phoneNumber.value)
                scope.launch {
                    if (isAdd){
                        viewModel.userIntent.send(MainIntent.AddContact(newContact))
                    }else{
                        newContact.id = contact?.id
                        viewModel.userIntent.send(MainIntent.UpdateContact(newContact))
                    }
                    navigation.pop()
                }
            }) {
                Text(text = if(isAdd) stringResource(id = R.string.add) else stringResource(id = R.string.edit))
            }
        }
    }
}

