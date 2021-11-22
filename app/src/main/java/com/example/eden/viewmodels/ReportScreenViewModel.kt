package com.example.eden.viewmodels

import androidx.lifecycle.ViewModel
import com.example.eden.auth.AuthenticationService
import com.example.eden.data.Repository
import com.example.eden.data.domain.IncidentReportInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface ReportScreenViewModel {
    fun sendReport(incidentReport: IncidentReportInfo)
}

class EdenReportScreenViewModel(
    private val repository: Repository,
    private val authenticationService: AuthenticationService,
    private val defaultDispatcher:CoroutineDispatcher = Dispatchers.IO
):ViewModel(),ReportScreenViewModel{
    override fun sendReport(incidentReport: IncidentReportInfo) {

    }
}