package com.example.eden.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eden.auth.AuthenticationService
import com.example.eden.data.Repository
import com.example.eden.data.domain.IncidentReportInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * An interface that consists of all the fields and methods required
 * for a ReportScreenViewModel.
 */
interface ReportScreenViewModel {
    /**
     * Used for sending a report based on the provided
     * [incidentReportInfo].
     */
    fun sendReport(incidentReportInfo: IncidentReportInfo)
}

class EdenReportScreenViewModel(
    private val repository: Repository,
    private val authenticationService: AuthenticationService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), ReportScreenViewModel {
    override fun sendReport(incidentReportInfo: IncidentReportInfo) {
        authenticationService.currentUser?.let { currentUser ->
            viewModelScope.launch(defaultDispatcher) {
                repository.sendIncidentReport(currentUser, incidentReportInfo)
            }
        }
    }
}