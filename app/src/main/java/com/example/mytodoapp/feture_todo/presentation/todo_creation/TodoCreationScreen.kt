package com.example.mytodoapp.feture_todo.presentation.todo_creation

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mytodoapp.feture_todo.domain.model.Work
import com.example.mytodoapp.feture_todo.presentation.todo_creation.util.TodoValidator
import com.example.mytodoapp.feture_todo.presentation.todo_creation.util.ValidateResult
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TodoCreationScreen(
    navigator: DestinationsNavigator,
    todoCreationViewModel: TodoCreationViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val dateStartPickerState = rememberMaterialDialogState()
    val timeStartPickerState = rememberMaterialDialogState()
    val dateEndPickerState = rememberMaterialDialogState()
    val timeEndPickerState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateStartPickerState,
        buttons = {
            positiveButton("Ok") {
                timeStartPickerState.show()
            }
            negativeButton("Cancel")
        }
    ) {
        datepicker(
            initialDate = todoCreationViewModel.startDate.toLocalDate(),
            onDateChange = todoCreationViewModel::changeSelectedDate
        )
    }

    MaterialDialog(
        dialogState = timeStartPickerState,
        buttons = {
            positiveButton("Ok") {
            }
            negativeButton("Cancel")
        }
    ) {
        timepicker(
            initialTime = todoCreationViewModel.startDate.toLocalTime(),
            onTimeChange = todoCreationViewModel::changeSelectedTime,
            is24HourClock = true
        )
    }


    MaterialDialog(
        dialogState = dateEndPickerState,
        buttons = {
            positiveButton("Ok") {
                timeEndPickerState.show()
            }
            negativeButton("Cancel")
        }
    ) {
        datepicker(
            initialDate = todoCreationViewModel.endDate.toLocalDate(),
            onDateChange = todoCreationViewModel::changeEndDate
        )
    }

    MaterialDialog(
        dialogState = timeEndPickerState,
        buttons = {
            positiveButton("Ok") {
            }
            negativeButton("Cancel")
        }
    ) {
        timepicker(
            initialTime = todoCreationViewModel.endDate.toLocalTime(),
            onTimeChange = todoCreationViewModel::changeEndTime,
            is24HourClock = true
        )
    }

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val result = TodoValidator.validate(
                        todoCreationViewModel.title,
                        todoCreationViewModel.startDate,
                        todoCreationViewModel.endDate
                    )
                    when (result) {
                        is ValidateResult.Success -> {
                            navigator.popBackStack()
                            todoCreationViewModel.saveTodo()
                        }

                        is ValidateResult.Error -> Toast.makeText(
                            context, result.message, Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                shape = CircleShape,
                modifier = Modifier
                    .size(48.dp)
                    .padding(0.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Create new TODO", style = MaterialTheme.typography.titleLarge)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navigator.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }

                })

        }

    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .padding(10.dp)
        ) {
            TextField(
                value = todoCreationViewModel.title,
                onValueChange = todoCreationViewModel::changeTitle,
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Title") },
                placeholder = { Text(text = "Enter title here") }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                Button(
                    onClick = { dateStartPickerState.show() }
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Start")
                        Text(todoCreationViewModel.formattedStartDate.value)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { dateEndPickerState.show() },
                    colors =
                    if (!todoCreationViewModel.isEndTimeChanged)
                        ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    else ButtonDefaults.buttonColors()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "End")
                        Text(todoCreationViewModel.formattedEndDate.value)
                    }
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "List of works(optional)")
            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .weight(1f)
            ) {
                /*item {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.AddTask,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                todoCreationViewModel.addWork()
                            })
                    }
                }*/

                items(
                    items = todoCreationViewModel.workList,
                    key = { work ->
                        work.id
                    }
                ) { work: Work ->
                    TextField(
                        value = work.name,
                        onValueChange = { field ->
                            todoCreationViewModel.changeWorkByIndex(work.id, field)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(text = "Work ${work.id + 1}") },
                        placeholder = { Text(text = "Enter your new work here") },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (work.name.isNotEmpty()) todoCreationViewModel.addWork()
                                focusManager.clearFocus()
                            }
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                if (work.id + 1 == todoCreationViewModel.workList.size && work.name.isNotEmpty()) {
                                    todoCreationViewModel.addWork()
                                    focusManager.clearFocus()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.AddTask,
                                    contentDescription = "End Task",
                                    tint = if (work.id + 1 < todoCreationViewModel.workList.size) Color.Green else Color.Unspecified
                                )

                            }
                        }
                    )
                }
            }

        }
    }

}