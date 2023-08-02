package com.example.contact.ui.contact.list

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.example.contact.R
import com.example.contact.ui.contact.add.AddContactScreen
import com.example.domain.models.Contact
import kotlinx.coroutines.launch

object ContactScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()
        val navigation = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val viewModel = hiltViewModel<ContactViewModel>()
        val contacts = remember { mutableStateListOf<Contact>() }
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
                    contacts.clear()
                    contacts.addAll((state as MainState.Contacts).contacts.shuffled())
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
                        contentPadding = PaddingValues(
                            vertical = dimensionResource(id = R.dimen.middle),
                            horizontal = dimensionResource(id = R.dimen.middle)
                        ),
                        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.small)),
                    ) {
                        items(contacts.size) { position ->
                            val contactItem = contacts[position]
                            ListItem(
                                item = contactItem,
                                editClick = {
                                    navigation.push(
                                        AddContactScreen(
                                            contactItem,
                                            isAdd = false
                                        )
                                    )
                                },
                                deleteClick = {
                                    scope.launch {
                                        viewModel.userIntent.send(MainIntent.DeleteContact(contactItem))
                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}