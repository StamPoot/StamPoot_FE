package com.example.footstamp.ui.view.profile.screen

import android.content.ContentResolver
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.footstamp.R
import com.example.footstamp.data.model.Notification
import com.example.footstamp.data.model.Profile
import com.example.footstamp.data.util.BitmapManager
import com.example.footstamp.data.util.Formatter
import com.example.footstamp.ui.base.BaseScreen
import com.example.footstamp.ui.components.BodyLargeText
import com.example.footstamp.ui.components.BodyText
import com.example.footstamp.ui.components.CommonButton
import com.example.footstamp.ui.components.HalfDialog
import com.example.footstamp.ui.components.LabelText
import com.example.footstamp.ui.components.ProfilePhotoSelector
import com.example.footstamp.ui.components.SpaceMaker
import com.example.footstamp.ui.components.TextInput
import com.example.footstamp.ui.components.TitleLargeText
import com.example.footstamp.ui.components.TitleText
import com.example.footstamp.ui.components.TopBar
import com.example.footstamp.ui.theme.BackColor
import com.example.footstamp.ui.theme.BlackColor
import com.example.footstamp.ui.theme.MainColor
import com.example.footstamp.ui.theme.SubColor
import com.example.footstamp.ui.theme.WarnColor
import com.example.footstamp.ui.theme.WhiteColor
import com.example.footstamp.ui.view.profile.ProfileViewModel
import com.example.footstamp.ui.view.util.AlertScreen
import com.example.footstamp.ui.view.util.LoadingScreen

@Composable
fun ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val isLoading by profileViewModel.isLoading.collectAsState()
    val alert by profileViewModel.alertState.collectAsState()

    BaseScreen { paddingValue, screenWidth, screenHeight ->
        val context = LocalContext.current
        val profileState by profileViewModel.profileState.collectAsState()
        val editProfile by profileViewModel.editProfile.collectAsState()
        val notificationList by profileViewModel.notificationList.collectAsState()
        val profileDeleteText by profileViewModel.profileDeleteText.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(text = stringResource(R.string.screen_profile), backgroundColor = WhiteColor)
            ProfileCard(profileState = profileState,
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                paddingValues = paddingValue,
                onClickEditButton = { profileViewModel.showEditProfileDialog() })
            ProfileMyHistory(
                context = context,
                notificationList = notificationList,
                screenHeight = screenHeight,
            )
            ProfileBottomLayout(
                screenHeight = screenHeight,
                profileDeleteText = profileDeleteText,
                onTextChange = { profileViewModel.deleteProfileAlert(it, context) },
                onClickDeleteProfile = { profileViewModel.checkProfileDelete() }
            )
        }

        if (editProfile != null) ProfileEditDialog(screenWidth = screenWidth,
            screenHeight = screenHeight,
            contentResolver = context.contentResolver,
            editProfile = editProfile!!,
            onChangeNickname = { profileViewModel.updateEditProfile(nickname = it) },
            onChangeImage = { profileViewModel.updateEditProfile(image = it) },
            onChangeAboutMe = { profileViewModel.updateEditProfile(aboutMe = it) },
            onEdit = { profileViewModel.updateProfile(context) },
            onDismiss = { profileViewModel.hideEditProfileDialog() })
    }

    isLoading.let { if (it) LoadingScreen() }
    alert?.let { AlertScreen(alert = it) }
}

@Composable
fun ProfileCard(
    profileState: Profile,
    screenWidth: Dp,
    screenHeight: Dp,
    paddingValues: PaddingValues,
    onClickEditButton: () -> Unit
) {

    SpaceMaker(height = screenHeight / 20f)
    Card(modifier = Modifier.fillMaxWidth(0.9f)) {
        Column(
            modifier = Modifier.padding(
                vertical = screenHeight / 40, horizontal = screenWidth / 40
            )
        ) {
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                Card(
                    shape = CircleShape,
                ) {
                    AsyncImage(
                        model = if (profileState.image != null) {
                            Formatter.convertStringToBitmap(profileState.image!!)
                        } else {
                            R.drawable.icon_circle_small
                        },
                        modifier = Modifier.size(screenWidth / 8, screenWidth / 8),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                SpaceMaker(width = 20.dp)
                TitleLargeText(text = profileState.nickname, color = BlackColor)
            }
            SpaceMaker(height = 20.dp)
            BodyText(text = profileState.aboutMe, color = MainColor)
        }
    }
    SpaceMaker(height = screenHeight / 40f)
    CommonButton("수정하기") { onClickEditButton() }
}

@Composable
fun ProfileMyHistory(
    context: Context, notificationList: List<Notification>, screenHeight: Dp
) {
    SpaceMaker(height = screenHeight / 40f)
    TopBar(text = "나의 활동")
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
    ) {
        notificationList.forEach { notification ->
            NotificationItem(
                text = notification.profile.nickname + getString(context, R.string.reply_message),
                time = Formatter.dateStringToString(notification.dateTime),
                message = notification.content
            )
        }
    }
}

@Composable
fun NotificationItem(text: String, time: String, message: String) {
    SpaceMaker(height = 5.dp)
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        SpaceMaker(height = 5.dp)
        BodyText(text = text, color = MainColor, modifier = Modifier.padding(horizontal = 5.dp))
        SpaceMaker(height = 5.dp)
        BodyLargeText(
            text = message, color = BlackColor, modifier = Modifier.padding(horizontal = 5.dp)
        )
        SpaceMaker(height = 5.dp)
        LabelText(text = time, color = SubColor, modifier = Modifier.padding(horizontal = 5.dp))
        SpaceMaker(height = 5.dp)
    }
    SpaceMaker(height = 5.dp)
}

@Composable
fun ProfileBottomLayout(
    profileDeleteText: String?,
    screenHeight: Dp,
    onTextChange: (text: String) -> Unit,
    onClickDeleteProfile: () -> Unit,
) {
    SpaceMaker(height = screenHeight / 20)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        if (profileDeleteText == null) {
            CommonButton(text = "회원 탈퇴", buttonColor = WarnColor) { onClickDeleteProfile() }
        } else {
            TextInput(
                hint = "탈퇴하시려면 '회원 탈퇴'를 입력해주세요",
                onValueChange = onTextChange,
            )
        }
    }
}

@Composable
fun ProfileEditDialog(
    screenWidth: Dp,
    screenHeight: Dp,
    editProfile: Profile,
    contentResolver: ContentResolver,
    onChangeNickname: (String) -> Unit,
    onChangeImage: (String) -> Unit,
    onChangeAboutMe: (String) -> Unit,
    onEdit: () -> Unit,
    onDismiss: () -> Unit,
) {
    HalfDialog(onChangeState = onDismiss) {
        Column(
            modifier = Modifier
                .background(BackColor)
                .fillMaxWidth(0.92f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SpaceMaker(height = screenHeight / 40)
            TitleLargeText(text = "프로필 수정", color = BlackColor)
            SpaceMaker(height = screenHeight / 40)

            HorizontalDivider(modifier = Modifier.fillMaxWidth(0.9f))
            SpaceMaker(height = screenHeight / 80)
            TitleText(text = "닉네임", color = BlackColor)
            SpaceMaker(height = screenHeight / 80)
            TextInput(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(WhiteColor),
                baseText = editProfile.nickname,
                hint = "닉네임을 설정해주세요",
                onValueChange = onChangeNickname
            )

            SpaceMaker(height = screenHeight / 80)
            TitleText(text = "사진", color = BlackColor)
            SpaceMaker(height = screenHeight / 80)
            ProfilePhotoSelector(
                contentResolver = contentResolver,
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                image = if (editProfile.image != null) Formatter.convertStringToBitmap(editProfile.image!!) else null,
                photoResizer = BitmapManager::bitmapResize1MB,
                onSetPhoto = onChangeImage
            )

            SpaceMaker(height = screenHeight / 80)
            TitleText(text = "한 줄 소개", color = BlackColor)
            SpaceMaker(height = screenHeight / 80)
            TextInput(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(WhiteColor),
                baseText = editProfile.aboutMe,
                hint = "자기소개를 설정해주세요",
                onValueChange = onChangeAboutMe
            )

            SpaceMaker(height = screenHeight / 80)
            CommonButton("수정") { onEdit() }
            SpaceMaker(height = screenHeight / 80)
        }
    }
}