package com.example.contact.ui.contact.list

import com.example.domain.models.Contact

sealed class MainState {
    object Idle : MainState()
    object Loading : MainState()
    object AddContact : MainState()
    data class Contacts(val contacts: List<Contact>) : MainState()
    object DeleteContact : MainState()
    object DeleteContacts : MainState()
    object UpdateContact : MainState()
    data class Error(val error: String?) : MainState()
}

//data class MainState2(
//    val isLoding: Boolean = false,
//
//    )
