
<div align="center">

<img width="128" height="128" src="./sample/src/main/res/mipmap-xxxhdpi/ic_launcher.webp" alt="logo">

<span>

`ANDROID LIBRARY`

</span>

# PickerKT

<p>A media picker library for Android apps powered by Jetpack Compose.</p>
</div>

![Project Hero Image](/previews/pj_hero_img.png)

## 🚩 Motivations
---
The app I worked on in my full-time job used [Matisse](https://github.com/zhihu/Matisse), and it was okay until a new version of Android (11 ~ 12) came along. The app faced instability issues and, to be honest, the UI of Matisse is not consistent with the rest of the app that uses Jetpack Compose by that time. Therefore, I spent my free time building this library to (hopefully) replace Matisse.


## 🎀 Project Structure
---
PickerKT has 3 sub-modules as follows:

### `pickerkt-base`
The `Base` module contains core data classes specified by the library and data loaders (`ContentResolver` + `Paging` stuffs). **It includes the building blocks that you need to build your own Picker UI (Jetpack Compose or not).** Even the Query Builder is in here.

#### Features
1. Reactive `Collection` listing via `CollectionListingSource`.
2. Reactive `Content` listing via `ContentPagingSource`.
3. Comes with a DSL to config the Picker-related classes.

#### Caveats
1. Non-media file MIME Type won't work with `ContentResolver` even if we have them defined in the code (PDF, MS Word, etc. are not visible 🥲).
2. I decided to use `threetenabp` and not quite sure if I got it right (in terms of setup and appropiateness).

### `pickerkt-ui`
The `Ui` module is built upon the `Base` module. This modules come with a ready-to-use Media Picker UI implemented using Jetpack Compose with Material 3. The screenshots in the top banner comes from this module. This is where most of efforts went into.

#### Features
1. Material Design 3 based Media Picker
2. Comes with Media Viewer (limited to images for now)
3. Customizable via Picker Config DSL.
4. Responsive UI (designed for both tablet and phone)

#### Caveats
1. ⚠️ Low performance when scrolling (Needs hands to investigate this)
2. No drag-to-select items
3. Multi-language translation needs a lot of work.
4. Some Configurable Arguments (using DSL) are not wired up properly.

### `sample`
The sample is basically a demo of the library. You can build and install to test it on your phone.


## 🔐 Required Runtime Permissions
---
The library requires `android.Manifest.permission.READ_EXTERNAL_STORAGE` permission to operate. `pickerkt-ui` will not perform any permission request for you, so you have to do it yourself.


## ⭐ Quick Start with PickerKT UI
---

### Download
// TODO:

### With Jetpack Compose
0. Request `android.Manifest.permission.READ_EXTERNAL_STORAGE` permission from the user. If you got no idea, see the `sample` source code or `Google` it.
1. In one of your Compose UI, declare a `MutableStateList` of any other State holder that can hold a `List<Uri>`
    ```kotlin
    val myPickerResultList = remember { mutableStateListOf<Uri>() }
    ```
2. Declare an Activity Launcher and a trailing lambda to specify what to do with the result (In this case, we are storing the result in `myPickerResultList`).
    ```kotlin
    val pickerLauncher = rememberLauncherForActivityResult(contract = PickerKtActivityResult()) { resultUriList ->
        myPickerResultList.addAll(resultUriList)
    }
    ```
3. On one of your `Button`, add a code to LAUNCH the Picker.
    ```kotlin
        Button(
            onClick = {
                pickerLauncher.launch(
                    PickerKt.picker {
                        allowMimes {
                            add { MimeType.Jpeg }
                            add { MimeType.Png }
                            add { MimeType.Gif }
                            add { MimeType.Svg }
                            add { MimeType.Mpeg4 }
                            add { MimeType.Mp3 }
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
    ```

    In this code, we set the Picker to show only files with MIME equals to JPEG, PNG, GIF, SVG, MPEG4, and MP3. We allow up to 25 files to be picked.

That's all you have to do for Jetpack Compose. To use the result list, use `myPickerResultList` as declared in step 1.

## ✅ Requirements
---
- AndroidX
- Min SDK: 24

## 📃 License
---
    Copyright 2022 PickerKT Contributors.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.