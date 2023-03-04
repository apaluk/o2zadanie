package com.example.o2zadanie.di

import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.o2zadanie.core.notification.O2NotificationManager
import com.example.o2zadanie.data.ticket.TicketRepositoryImpl
import com.example.o2zadanie.data.ticket.remote.O2ActivationApi
import com.example.o2zadanie.domain.repository.TicketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @ApplicationScope
    @Provides
    fun provideApplicationScope(): CoroutineScope {
        return ProcessLifecycleOwner.get().lifecycleScope
    }

    @Provides
    fun provideO2ActivationApi(): O2ActivationApi {
        return Retrofit.Builder()
            .baseUrl("https://api.o2.sk/")
            .build()
            .create(O2ActivationApi::class.java)
    }

    // singleton aby si drzal stav zrebu, kedze nemame iny source of truth
    @Singleton
    @Provides
    fun provideTicketRepository(
        @ApplicationScope applicationScope: CoroutineScope,
        o2ActivationApi: O2ActivationApi,
        o2NotificationManager: O2NotificationManager
    ): TicketRepository {
        return TicketRepositoryImpl(applicationScope, o2ActivationApi, o2NotificationManager)
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApplicationScope