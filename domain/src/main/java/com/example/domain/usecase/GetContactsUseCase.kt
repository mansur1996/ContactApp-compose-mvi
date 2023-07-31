package com.example.domain.usecase

import com.example.domain.models.Contact
import com.example.domain.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetContactsUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {
    fun getContacts(): Flow<Result<List<Contact>>> = flow {
        val response = appRepository.getContacts()
        emit(Result.success(response))
    }.catch {
        emit(Result.failure(Exception(it)))
    }.flowOn(Dispatchers.IO)
}