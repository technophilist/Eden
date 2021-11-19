package com.example.eden.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.eden.auth.AuthenticationService
import com.example.eden.data.Repository
import com.example.eden.data.domain.NotificationInfo

interface NotificationsScreenViewModel {
    val notificationList: LiveData<List<NotificationInfo>>
}

class EdenNotificationsScreenViewmodel(
    repository: Repository,
    authenticationService: AuthenticationService
) : ViewModel(), NotificationsScreenViewModel {

    //TODO(Remove non-null assertion)
    override val notificationList: LiveData<List<NotificationInfo>> =
        repository.listenForNotifications(authenticationService.currentUser!!)


}
