package com.example.eden.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eden.auth.AuthenticationService
import com.example.eden.data.Repository
import com.example.eden.data.domain.IncidentReportInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface ReportScreenViewModel {
    fun sendReport(incidentReport: IncidentReportInfo)
}

class EdenReportScreenViewModel(
    private val repository: Repository,
    private val authenticationService: AuthenticationService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), ReportScreenViewModel {
    override fun sendReport(incidentReport: IncidentReportInfo) {
        authenticationService.currentUser?.let { currentUser ->
            viewModelScope.launch(defaultDispatcher) {
                repository.sendIncidentReport(currentUser, incidentReport)
            }
        }
    }
}