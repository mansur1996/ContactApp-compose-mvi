package com.example.contact.ui.contact.list

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import com.example.domain.models.Contact
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.contact.ui.contact.add.AddContactScreen
import kotlinx.coroutines.launch

object ContactScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val navigation = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val viewModel = hiltViewModel<ContactViewModel>()
        var list = remember { mutableStateListOf<Contact>() }
        var showProgressBar by remember { mutableStateOf(true) }
        val state by viewModel.state.collectAsStateWithLifecycle()

        LaunchedEffect(Unit) {
            viewModel.userIntent.send(MainIntent.GetAllContacts)
        }
        LaunchedEffect(state) {
            when (state) {
                is MainState.Idle -> {}
                is MainState.Loading -> {
                    showProgressBar = true
                }
                is MainState.AddContact -> {}
                is MainState.Contacts -> {
                    showProgressBar = false
                    list.clear()
                    list.addAll((state as MainState.Contacts).contacts.shuffled())
                }
                is MainState.DeleteContact -> {}
                is MainState.DeleteContacts -> {}
                is MainState.UpdateContact -> {}
                is MainState.Error -> {}

            }
        }
        Scaffold(topBar = {
            AppBar {
                navigation.push(AddContactScreen())
            }
        }) { contentPadding ->
            Box(modifier = Modifier.padding(contentPadding)) {
                AnimatedVisibility(visible = showProgressBar, enter = fadeIn(), exit = fadeOut()) {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                AnimatedVisibility(visible = !showProgressBar, enter = fadeIn(), exit = fadeOut()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .animateContentSize(),
                        contentPadding = PaddingValues(vertical = 10.dp, horizontal = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        items(list.size) { position ->
                            val item = list[position]
                            ListItem(
                                item = item,
                                deleteClick = {
                                    scope.launch {
                                        viewModel.userIntent.send(MainIntent.DeleteContact(item))
                                    }
                                },
                                editClick = {
                                    val contact = Contact(
                                        id = item.id,
                                        name = item.name,
                                        phoneNumber = item.phoneNumber
                                    )
                                    Log.e("TAG", "Content: $contact", )
                                    navigation.push(
                                        AddContactScreen(
                                            contact,
                                            isAdd = false
                                        )
                                    )
                                })
                        }
                    }
                }
            }
        }
    }
}