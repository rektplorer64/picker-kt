package io.rektplorer64.pickerkt.sample

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import io.rektplorer64.pickerkt.builder.PickerKt
import io.rektplorer64.pickerkt.contentresolver.MimeType
import io.rektplorer64.pickerkt.sample.ui.theme.PickerKtTheme
import io.rektplorer64.pickerkt.ui.common.PickerKtActivityResult

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PickerKtTheme {

                val readExternalStoragePermissionState = rememberPermissionState(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )

                val imagesReturnedFromPickerKt = remember { mutableStateListOf<Uri>() }
                val pickerLauncher =
                    rememberLauncherForActivityResult(contract = PickerKtActivityResult()) {
                        imagesReturnedFromPickerKt.addAll(it)
                    }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        if (imagesReturnedFromPickerKt.isNotEmpty()) {
                            FloatingActionButton(onClick = { imagesReturnedFromPickerKt.clear() }) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = "Clear selected images"
                                )
                            }
                        }
                    }
                ) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Surface(color = MaterialTheme.colors.background) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "PickerKT Sample",
                                    style = MaterialTheme.typography.h3,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(16.dp),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = "There are ${imagesReturnedFromPickerKt.size} selected images.",
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.padding(bottom = 8.dp),
                                    textAlign = TextAlign.Center
                                )

                                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Button(
                                        onClick = { readExternalStoragePermissionState.launchPermissionRequest() },
                                        enabled = !readExternalStoragePermissionState.hasPermission
                                    ) {
                                        Text(text = if (readExternalStoragePermissionState.hasPermission) "Permission Granted" else "Grant Permission")
                                    }

                                    Button(
                                        enabled = readExternalStoragePermissionState.hasPermission,
                                        onClick = {
                                            pickerLauncher.launch(
                                                PickerKt.picker {
                                                    allowMimes {
                                                        add { MimeType.Jpeg }
                                                        add { MimeType.Png }
                                                        add { MimeType.Gif }
                                                        add { MimeType.Svg }
                                                        add { MimeType.Mpeg4 }
                                                        add { MimeType.MsWordDoc2007 }
                                                        add { MimeType.Mp3 }
                                                        add { MimeType.OggAudio }
                                                    }

                                                    selection {
                                                        maxSelection(25)
                                                    }
                                                }
                                            )
                                        }
                                    ) {
                                        Text(text = "Open Picker")
                                    }
                                }

                                if (readExternalStoragePermissionState.shouldShowRationale) {
                                    Text(text = "Allow 'Read External Storage permission' to open the picker")
                                }
                            }
                        }

                        LazyVerticalGrid(
                            cells = GridCells.Adaptive(120.dp),
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        ) {
                            items(imagesReturnedFromPickerKt) {
                                Column(
                                    modifier = Modifier
                                        .animateItemPlacement()
                                        .fillParentMaxWidth()
                                ) {
                                    Image(
                                        painter = rememberAsyncImagePainter(it),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .aspectRatio(1f)
                                            .fillParentMaxWidth(),
                                        alignment = Alignment.Center,
                                        contentScale = ContentScale.Crop
                                    )
                                    Text(text = it.toString(), maxLines = 3)
                                }
                            }
                        }
                    }
                }

            }
        }
    }
}