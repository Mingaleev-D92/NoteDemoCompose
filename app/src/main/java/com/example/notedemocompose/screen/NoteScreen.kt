package com.example.notedemocompose.screen

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notedemocompose.R
import com.example.notedemocompose.components.NoteButton
import com.example.notedemocompose.components.NoteInputText
import com.example.notedemocompose.data.NoteDataSource
import com.example.notedemocompose.model.NoteModel
import java.time.format.DateTimeFormatter

/**
 * @author : Mingaleev D
 * @data : 10.05.2023
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(notes: List<NoteModel>,
               onAddNotes: (NoteModel) -> Unit,
               onRemoveNote: (NoteModel) -> Unit
) {

   var title by remember { mutableStateOf("") }
   var description by remember { mutableStateOf("") }

   Column(modifier = Modifier.padding(6.dp)) {
      TopAppBar(title = {
         Text(text = stringResource(id = R.string.app_name))
      }, actions = {
         Icon(imageVector = Icons.Rounded.Notifications, contentDescription = null)
      }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xffdadfe3))
      )
      //input content
      Column(modifier = Modifier.fillMaxWidth(),
             horizontalAlignment = Alignment.CenterHorizontally
      ) {
         NoteInputText(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                       text = title,
                       label = stringResource(id = R.string.title),
                       onTextChange = { titleText ->
                          if (titleText.all { char ->
                                 char.isLetter() || char.isWhitespace()
                              })
                             title = titleText
                       }
         )
         NoteInputText(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                       text = description,
                       label = stringResource(id = R.string.description),
                       onTextChange = { descriptionText ->
                          if (descriptionText.all { char ->
                                 char.isLetter() || char.isWhitespace()
                              })
                             description = descriptionText

                       }
         )
         NoteButton(text = stringResource(id = R.string.save),
                    onClick = {
                       if (title.isNotEmpty() && description.isNotEmpty()) {
                          title = ""
                          description = ""
                       }
                    })
      }
      Divider(modifier = Modifier.padding(10.dp))
      LazyColumn {
         items(notes) { note ->
            NoteRow(note = note, onNoteClicked = { })
         }
      }
   }
}

@SuppressLint("InvalidColorHexValue")
@Composable
fun NoteRow(modifier: Modifier = Modifier,
            note: NoteModel,
            onNoteClicked: (NoteModel) -> Unit
) {
   Surface(modifier = Modifier
      .padding(4.dp)
      .clip(RoundedCornerShape(topEnd = 33.dp))
      .fillMaxWidth(),
           contentColor = Color(0xffdfe6b),
           tonalElevation = 8.dp
   ) {
      Column(modifier
                .clickable { }
                .padding(horizontal = 14.dp, vertical = 6.dp),
             horizontalAlignment = Alignment.Start
      ) {
         Text(text = note.title,
              style = MaterialTheme.typography.titleLarge,
              color = Color.Black
         )
         Text(text = note.description,
              style = MaterialTheme.typography.titleSmall,
              color = Color.Black
         )
         Text(text = note.entryDate.format(DateTimeFormatter.ofPattern("EEE, d MMM")),
              style = MaterialTheme.typography.titleSmall,
              color = Color.Black
         )
      }
   }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
   NoteScreen(notes = NoteDataSource().loadNotes(),
              onAddNotes = { },
              onRemoveNote = { }
   )
}