package com.example.mapper

import com.example.data.local.enteties.ContactEntity
import com.example.domain.models.Contact

fun ContactEntity.toContact () : Contact {
    return Contact(id = this.id, name = this.name, phoneNumber = this.phoneNumber)
}

fun Contact.toContactEntity () : ContactEntity {
    return ContactEntity(id = this.id, name = this.name, phoneNumber = this.phoneNumber)
}