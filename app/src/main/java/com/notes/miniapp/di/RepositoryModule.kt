package com.notes.miniapp.di

import com.notes.miniapp.repository.AuthenticationRepository
import com.notes.miniapp.repository.DatabaseRepository
import com.notes.miniapp.repository.FirebaseAuthRepositoryImpl
import com.notes.miniapp.repository.FirebaseDatabaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    abstract fun bindAuthentication(implementation: FirebaseAuthRepositoryImpl): AuthenticationRepository

    @Binds
    abstract fun bindDatabase(implementation: FirebaseDatabaseRepositoryImpl): DatabaseRepository

}
