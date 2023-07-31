package com.example.domain.repository

import com.example.domain.models.Contact


interface AppRepository {
    suspend fun getContacts() : List<Contact>
    suspend fun addContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
    suspend fun deleteContacts()
    suspend fun updateContact(contact: Contact)
}