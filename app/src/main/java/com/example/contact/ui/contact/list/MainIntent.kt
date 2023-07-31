package com.example.contact.ui.contact.list

import com.example.domain.models.Contact

sealed class MainIntent {
    data class AddContact(val contact: Contact) : MainIntent()
    object GetAllContacts : MainIntent()
    object DeleteAllContacts : MainIntent()
    data class DeleteContact(val contact: Contact) : MainIntent()
    data class UpdateContact(val contact: Contact) : MainIntent()
}