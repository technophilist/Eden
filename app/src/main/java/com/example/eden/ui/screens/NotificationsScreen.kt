package com.example.eden.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CorporateFare
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.eden.data.domain.NotificationInfo


@ExperimentalMaterialApi
@Suppress("FunctionName")
@Composable
fun NotificationsScreen(notifications: List<NotificationInfo>) {
    Column(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxSize()
    ) {
        Text(text = "Notifications", style = MaterialTheme.typography.h3)
        Spacer(modifier = Modifier.padding(16.dp))
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(notifications) { notification ->
                NotificationCard(
                    modifier = Modifier
                        .height(80.dp)
                        .fillMaxWidth(),
                    notificationInfo = notification,
                    onClick = { /*TODO*/ }
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun NotificationCard(
    notificationInfo: NotificationInfo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Icon with rounded background
            Surface(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .size(48.dp),
                color = when (notificationInfo.type) {
                    NotificationInfo.NotificationType.ORDERS -> MaterialTheme.colors.secondary
                    NotificationInfo.NotificationType.APPOINTMENTS -> MaterialTheme.colors.error
                    NotificationInfo.NotificationType.NGO -> MaterialTheme.colors.secondaryVariant
                },
            ) {
                Icon(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize(),
                    imageVector = when (notificationInfo.type) {
                        NotificationInfo.NotificationType.ORDERS -> Icons.Filled.ShoppingCart
                        NotificationInfo.NotificationType.APPOINTMENTS -> Icons.Filled.AccountBox
                        NotificationInfo.NotificationType.NGO -> Icons.Filled.CorporateFare
                    },
                    contentDescription = ""
                )
            }
            Column(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = notificationInfo.header,
                    style = MaterialTheme.typography.h1,
                    maxLines = 1
                )
                Text(
                    text = notificationInfo.content,
                    style = MaterialTheme.typography.body1,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
