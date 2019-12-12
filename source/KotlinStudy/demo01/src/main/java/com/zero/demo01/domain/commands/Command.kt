package com.zero.demo01.domain.commands

interface Command<out T> {
    suspend fun execute(): T
}