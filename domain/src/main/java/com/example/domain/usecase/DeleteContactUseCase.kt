package com.example.domain.usecase

import com.example.domain.models.Contact
import com.example.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteContactUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {
    fun deleteContact(contact: Contact): Flow<Result<Unit>> = flow {
        val response = appRepository.deleteContact(contact)
        emit(Result.success(response))
    }.flowOn(Dispatchers.IO)
}