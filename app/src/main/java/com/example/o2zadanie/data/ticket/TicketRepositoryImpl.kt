package com.example.o2zadanie.data.ticket

import com.example.o2zadanie.core.notification.O2NotificationManager
import com.example.o2zadanie.data.ticket.remote.O2ActivationApi
import com.example.o2zadanie.domain.model.TicketState
import com.example.o2zadanie.domain.repository.TicketRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import org.json.JSONObject

class TicketRepositoryImpl(
    private val applicationScope: CoroutineScope,
    private val o2ActivationApi: O2ActivationApi,
    private val o2NotificationManager: O2NotificationManager
): TicketRepository {

    private val ticket = MutableStateFlow<TicketState>(TicketState.Unwiped)

    override fun getTicket(): Flow<TicketState> = ticket

    override fun wipeTicket(code: String) {
        if(ticket.value == TicketState.Unwiped) {
            ticket.value = TicketState.Wiped(code)
        }
    }

    override suspend fun activateTicket(code: String) {
        // Ak by som si chcel byt isty, ze sa request odosle za kazdych okolnosti, tak by som pouzil WorkManager.
        // Pre zjednodusenie pouzijem riesenie, kedy sa aktivacia vykona dokial je applicationScope platny.
        withContext(applicationScope.coroutineContext + NonCancellable) {
            ticket.value = TicketState.Activating(code)
            ticket.value = try {
                val response = o2ActivationApi.activateCode(code)
                val body = response.body()?.string()
                if (!response.isSuccessful)
                    TicketState.ActivationError(code, "HTTP ${response.code()}")
                else if (body == null)
                    TicketState.ActivationError(code, "Empty response")
                else {
                    // tu by som normalne pouzil Moshi
                    val activationResult = JSONObject(body).optString("android").toInt()
                    if (activationResult > 80_000)
                        TicketState.Activated(code)
                    else {
                        o2NotificationManager.showActivationErrorNotification(activationResult)
                        TicketState.ActivationError(code, "Response: $activationResult")
                    }
                }
            } catch (e: Exception) {
                TicketState.ActivationError(code, e.message)
            }
        }
    }
}