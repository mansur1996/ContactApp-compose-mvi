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

    override fun addContact(contact: Contact) {
        return getContactDao.insertContact(contact.toContactEntity())
    }

    override fun deleteContact(contact: Contact) {
        return getContactDao.deleteContact(contact.toContactEntity())
    }

    override fun deleteContacts() {
        return getContactDao.deleteContacts()
    }

    override fun updateContact(contact: Contact) {
        return getContactDao.updateContact(contact.toContactEntity())
    }
}