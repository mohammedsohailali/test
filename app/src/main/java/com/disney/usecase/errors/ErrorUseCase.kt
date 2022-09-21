package com.disney.usecase.errors

import com.disney.data.error.Error

interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
