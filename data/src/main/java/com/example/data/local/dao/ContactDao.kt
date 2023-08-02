package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.enteties.ContactEntity


@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: ContactEntity)

    @Query("SELECT * FROM contacts")
    suspend fun getContacts(): List<ContactEntity>

    @Delete
    fun deleteContact(contact: ContactEntity)

    @Query("DELETE FROM contacts")
    fun deleteContacts()

    @Update
    fun updateContact(contact: ContactEntity)
}