package com.example.mytodoapp.feture_todo.presentation.todolist

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.FormatListNumbered
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mytodoapp.R
import com.example.mytodoapp.destinations.TodoCreationScreenDestination
import com.example.mytodoapp.feture_todo.presentation.todolist.components.DayTime
import com.example.mytodoapp.feture_todo.presentation.todolist.components.EmptyDayTime
import com.example.mytodoapp.feture_todo.presentation.todolist.components.TodoItem
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TodoListScreen(
    navigator: DestinationsNavigator,
    todoListViewModel: TodoListViewModel = hiltViewModel(),
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    val openDrawer = {
        scope.launch {
            drawerState.open()
        }
    }
    val scrollState = rememberLazyListState(todoListViewModel.selectedPosition)

    Scaffold(
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigator.navigate(TodoCreationScreenDestination) },
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
                title = { Text(text = todoListViewModel.formattedSelectedDate) },
                navigationIcon = {
                    IconButton(onClick = { openDrawer() }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = todoListViewModel::toggleShowCompleted) {
                        Icon(
                            imageVector =
                            if (todoListViewModel.showCompeted) {
                                Icons.Default.CheckBox
                            } else {
                                Icons.Default.CheckBoxOutlineBlank
                            },
                            contentDescription = "Toggle Show Completed"
                        )
                    }
                }
            )
        },

        ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(padding)
                .padding(10.dp)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        scope.launch {
                            scrollState.scrollBy(-0.75f * dragAmount)
                        }
                    }
                }
        ) {

            val days = remember { todoListViewModel.days }
            val centerPosition by remember {
                derivedStateOf {
                    scrollState.layoutInfo.visibleItemsInfo.run {
                        val firstVisibleIndex = scrollState.firstVisibleItemIndex
                        if (isEmpty()) 0 else (firstVisibleIndex + (last().index - firstVisibleIndex) / 2f).roundToInt() - 3
                    }
                }
            }
            LaunchedEffect(centerPosition) {
                todoListViewModel.updateSelectedDay(centerPosition)
            }

            Spacer(modifier = Modifier.height(5.dp))
            LazyRow(
                state = scrollState,
                horizontalArrangement = Arrangement.spacedBy(9.dp),
                userScrollEnabled = false
            ) {
                items(3) {
                    EmptyDayTime()
                }
                itemsIndexed(days) { index, day ->
                    DayTime(
                        day = day,
                        isActive = index == centerPosition
                    )
                }
                items(3) {
                    EmptyDayTime()
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            AnimatedContent(
                targetState = todoListViewModel.todos.todos,
                transitionSpec = {
                    slideInHorizontally { todoListViewModel.direction * it } togetherWith slideOutHorizontally { -todoListViewModel.direction * it }
                },
                label = ""
            ) { todos ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    items(todos) {
                        TodoItem(
                            todo = it,
                            onTodoBoxClicked = todoListViewModel::toggleCheckbox,
                            onWorkBoxClicked = todoListViewModel::toggleWorkCheckbox
                        )
                    }
                }
            }
            if (todoListViewModel.todos.todos.isEmpty() && !todoListViewModel.todos.isLoading) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Column(
                        Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Outlined.FormatListNumbered,
                            contentDescription = "No todo today",
                            Modifier.size(96.dp)
                        )
                        Text(text = stringResource(id = R.string.no_todos_for_today))
                    }
                    Column(
                        Modifier.weight(1f)
                    ) {
                        val color = MaterialTheme.colorScheme.onBackground

                        Canvas(modifier = Modifier.fillMaxSize()) {

                            val path = Path().apply {
                                moveTo(size.width / 2, 0f)
                                cubicTo(
                                    size.width / 2, size.height / 4,
                                    size.width, size.height / 4,
                                    size.width * 0.92f, size.height * 0.92f
                                )
                            }

                            drawPath(
                                path = path,
                                color = color,
                                style = Stroke(width = 5.dp.toPx())
                            )
                        }
                    }

                }

            }
        }
    }


}


