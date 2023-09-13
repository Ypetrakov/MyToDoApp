package com.example.mytodoapp.feture_todo.presentation.todolist.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp


@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onAddClicked: () -> Unit,
    showCompleted: Boolean,
    onChangeShowCompleted: () -> Unit
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .height(64.dp)
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 15.dp)
        ) {
            IconButton(
                onClick = { onChangeShowCompleted() },
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = if (showCompleted) Icons.Default.CheckBox
                    else Icons.Default.CheckBoxOutlineBlank, contentDescription = null
                )

            }
            FloatingActionButton(
                onClick = { onAddClicked() },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                shape = CircleShape,
                modifier = Modifier
                    .size(48.dp)
                    .align(Alignment.Center)
                    .padding(0.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    }
}