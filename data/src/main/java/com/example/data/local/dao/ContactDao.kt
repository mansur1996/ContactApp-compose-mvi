package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.enteties.ContactEntity


@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contact: ContactEntity)

    @Query("SELECT * FROM contacts")
    suspend fun getContacts(): List<ContactEntity>

    @Delete
    suspend fun deleteContact(contact: ContactEntity)

    @Query("DELETE FROM contacts")
    suspend fun deleteContacts()

    @Update
    suspend fun updateContact(contact: ContactEntity)
}