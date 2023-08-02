package com.example.domain.repository

import com.example.domain.models.Contact


interface AppRepository {
    suspend fun getContacts() : List<Contact>
    fun addContact(contact: Contact)
    fun deleteContact(contact: Contact)
    fun deleteContacts()
    fun updateContact(contact: Contact)
}