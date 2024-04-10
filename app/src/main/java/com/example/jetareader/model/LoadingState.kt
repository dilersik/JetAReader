package com.example.jetareader.model

data class LoadingState(val status: StatusEnum, val message: String? = null) {

    companion object {
        val SUCCESS = LoadingState(StatusEnum.SUCCESS)
        val FAILED = LoadingState(StatusEnum.FAILED)
        val LOADING = LoadingState(StatusEnum.LOADING)
        val IDLE = LoadingState(StatusEnum.IDLE)
    }

    enum class StatusEnum {
        SUCCESS,
        FAILED,
        LOADING,
        IDLE
    }
}
