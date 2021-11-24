package com.example.eden.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.systemBarsPadding

private data class ReportScreenTextFieldContent(
    val label: String,
    val imageVector: ImageVector,
    val stringValue: MutableState<String> = mutableStateOf("")
)

@Composable
fun ReportScreen() {
    val capturePhotoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicturePreview()
    ) {}
    var typeOfIncident by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val addressAndPhoneNumberTextFieldContentList = remember {
        mutableMapOf(
            "address" to ReportScreenTextFieldContent("Address", Icons.Filled.Place),
            "phoneNumber" to ReportScreenTextFieldContent("Phone Number", Icons.Filled.PhoneAndroid)
        )
    }
    val cityAndStateReportTextFieldContentList = remember {
        mutableMapOf(
            "city" to ReportScreenTextFieldContent("City", Icons.Filled.LocationCity),
            "state" to ReportScreenTextFieldContent("State", Icons.Filled.Apartment)
        )
    }
    val isReportButtonEnabled by remember(
        addressAndPhoneNumberTextFieldContentList.getValue("address").stringValue.value,
        addressAndPhoneNumberTextFieldContentList.getValue("phoneNumber").stringValue.value,
        cityAndStateReportTextFieldContentList.getValue("city").stringValue.value,
        cityAndStateReportTextFieldContentList.getValue("state").stringValue.value,
        description
    ) {
        val isAddressAndPhoneNumberTextNotBlank =
            addressAndPhoneNumberTextFieldContentList.values.all { it.stringValue.value.isNotBlank() }
        val isCityAndStateTextNotBlank =
            cityAndStateReportTextFieldContentList.values.all { it.stringValue.value.isNotBlank() }
        mutableStateOf(
            isAddressAndPhoneNumberTextNotBlank &&
                isCityAndStateTextNotBlank &&
                description.isNotBlank()
        )
    }
    Box(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "Report incident", style = MaterialTheme.typography.h3)// TODO string res
            addressAndPhoneNumberTextFieldContentList.values.forEach { reportScreenTextFieldContent ->
                ReportScreenTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = reportScreenTextFieldContent.label) },
                    leadingIcon = { Icon(reportScreenTextFieldContent.imageVector, null) },
                    value = reportScreenTextFieldContent.stringValue.value,
                    onValueChange = { reportScreenTextFieldContent.stringValue.value = it },
                    maxLines = 1,
                    keyboardOptions = if (reportScreenTextFieldContent.label == "Phone Number") KeyboardOptions(
                        keyboardType = KeyboardType.Phone
                    )
                    else KeyboardOptions.Default
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                cityAndStateReportTextFieldContentList.values.forEach { reportTextFieldContent ->
                    ReportScreenTextField(
                        modifier = Modifier.weight(1f),
                        label = { Text(text = reportTextFieldContent.label) },
                        leadingIcon = { Icon(reportTextFieldContent.imageVector, null) },
                        value = reportTextFieldContent.stringValue.value,
                        onValueChange = { reportTextFieldContent.stringValue.value = it },
                        maxLines = 1
                    )
                }
            }
            ReportScreenTextField(
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Type Of Incident") },
                value = typeOfIncident,
                onValueChange = { typeOfIncident = it },
                maxLines = 1
            )
            ReportScreenTextField(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                label = { Text(text = "Description") },
                value = description,
                onValueChange = { description = it },
            )
            Button(onClick = { capturePhotoLauncher.launch() }) {
                Icon(imageVector = Icons.Filled.PhotoCamera, contentDescription = null)
                Text(text = "Attach Image")
            }
        }
        Button(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.BottomStart)
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.error),
            onClick = {},
            enabled = isReportButtonEnabled
        ) {
            Icon(
                imageVector = Icons.Filled.Report,
                tint = MaterialTheme.colors.onError,
                contentDescription = null
            )
            Text(text = "Report", color = MaterialTheme.colors.onError)
        }
    }
}

@Composable
fun ReportScreenTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    label: (@Composable () -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    TextField(
        modifier = modifier,
        leadingIcon = leadingIcon,
        label = label,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            cursorColor = MaterialTheme.colors.secondary,
            focusedLabelColor = MaterialTheme.colors.secondary
        ),
        maxLines = maxLines,
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions
    )
}
