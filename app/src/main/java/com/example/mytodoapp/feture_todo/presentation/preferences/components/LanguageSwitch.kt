package com.example.mytodoapp.feture_todo.presentation.preferences.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mytodoapp.R
import com.example.mytodoapp.ui.theme.MyToDoAppTheme


@Composable
fun LanguageSwitch(
    checked: Boolean,
    onChange: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    Switch(
        modifier = modifier.width(100.dp),
        checked = checked,
        onCheckedChange = {
            onChange()
        },
        thumbContent = {
            Image(
                painter = painterResource(if (checked) R.drawable.english else R.drawable.ukraine),
                contentDescription = null
            )
        }
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES, widthDp = 320, heightDp = 320)
@Composable
fun LanguageSwitchPreview() {
    MyToDoAppTheme {
        Surface {
            LanguageSwitch(
                true,
                {}
            )
        }
    }
}