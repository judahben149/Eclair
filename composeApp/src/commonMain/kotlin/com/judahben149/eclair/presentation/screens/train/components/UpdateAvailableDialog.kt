package com.judahben149.eclair.presentation.screens.train.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun UpdateAvailableDialog(
    currentVersion: Int,
    newVersion: Int,
    onAccept: () -> Unit,
    onDecline: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDecline,
        title = { Text("New Content Available") },
        text = {
            Text(
                "A new version of this content is available (v$newVersion). " +
                        "Would you like to update?"
            )
        },
        confirmButton = {
            TextButton(onClick = onAccept) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDecline) {
                Text("Not Now")
            }
        }
    )
}
