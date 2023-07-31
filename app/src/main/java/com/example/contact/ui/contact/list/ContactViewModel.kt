package com.example.contact.ui.contact.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Contact
import com.example.domain.usecase.AddContactUseCase
import com.example.domain.usecase.DeleteContactUseCase
import com.example.domain.usecase.GetContactsUseCase
import com.example.domain.usecase.UpdateContactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val addContactUseCase: AddContactUseCase,
    private val getContactsUseCase: GetContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
) : ViewModel() {

    val userIntent = Channel<MainIntent>(Channel.UNLIMITED)
    private val _state = MutableStateFlow<MainState>(MainState.Idle)
    val state = _state.asStateFlow()

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is MainIntent.AddContact -> addContacts(it.contact)
                    is MainIntent.GetAllContacts -> getContacts()
                    is MainIntent.DeleteContact -> deleteContact(it.contact)
                    is MainIntent.DeleteAllContacts -> deleteContacts()
                    is MainIntent.UpdateContact -> updateContact(it.contact)
                }
            }
        }
    }

    fun getContacts() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            getContactsUseCase.getContacts().onEach {
                it.onSuccess { contacts ->
                    _state.value = MainState.Contacts(contacts)
                }
                it.onFailure { throwable ->
                    _state.value = MainState.Error(throwable.message)
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun addContacts(contact: Contact) {
        viewModelScope.launch {
            addContactUseCase.addContact(contact).onEach {
                it.onSuccess {
                    _state.value = MainState.AddContact
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            deleteContactUseCase.deleteContact(contact).onEach {
                it.onSuccess {
                    _state.value = MainState.DeleteContact
                    getContacts()
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun updateContact(contact: Contact) {
        viewModelScope.launch {
            updateContactUseCase.updateContacts(contact).onEach {
                it.onSuccess {
                    _state.value = MainState.UpdateContact
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun deleteContacts() {


    }
}