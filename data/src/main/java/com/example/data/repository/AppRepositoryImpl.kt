package com.example.data.repository

import android.util.Log
import com.example.data.local.dao.ContactDao
import com.example.domain.models.Contact
import com.example.domain.repository.AppRepository
import com.example.mapper.toContact
import com.example.mapper.toContactEntity
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val getContactDao: ContactDao
) : AppRepository {

    override suspend fun getContacts(): List<Contact> {
        return getContactDao.getContacts().map { it.toContact() }
    }

    override suspend fun addContact(contact: Contact) {
        return getContactDao.insertContact(contact.toContactEntity())
    }

    override suspend fun deleteContact(contact: Contact) {
        return getContactDao.deleteContact(contact.toContactEntity())
    }

    override suspend fun deleteContacts() {
        return getContactDao.deleteContacts()
    }

    override suspend fun updateContact(contact: Contact) {
        Log.e("TAG", "updateContact: ${contact.toString()}", )
        return getContactDao.updateContact(contact.toContactEntity())
    }
}