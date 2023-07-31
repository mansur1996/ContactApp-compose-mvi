package com.example.domain.usecase

import com.example.domain.models.Contact
import com.example.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddContactUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {
    fun addContact(contact: Contact): Flow<Result<Unit>> = flow {
        val response = appRepository.addContact(contact)
        emit(Result.success(response))
    }.catch {
        emit(Result.failure(Exception(it)))
    }.flowOn(Dispatchers.IO)
}