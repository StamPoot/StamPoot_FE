package project.android.footstamp.ui.view.profile.screen

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
import androidx.compose.material3.CardColors
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
import project.android.footstamp.R
import project.android.footstamp.data.model.Notification
import project.android.footstamp.data.model.Profile
import project.android.footstamp.data.util.BitmapManager
import project.android.footstamp.data.util.Formatter
import project.android.footstamp.ui.base.BaseScreen
import project.android.footstamp.ui.components.BodyLargeText
import project.android.footstamp.ui.components.BodyText
import project.android.footstamp.ui.components.CommonButton
import project.android.footstamp.ui.components.HalfDialog
import project.android.footstamp.ui.components.LabelText
import project.android.footstamp.ui.components.ProfilePhotoSelector
import project.android.footstamp.ui.components.SpaceMaker
import project.android.footstamp.ui.components.TextInput
import project.android.footstamp.ui.components.TitleLargeText
import project.android.footstamp.ui.components.TitleText
import project.android.footstamp.ui.components.TopBar
import project.android.footstamp.ui.theme.BackColor
import project.android.footstamp.ui.theme.BlackColor
import project.android.footstamp.ui.theme.MainColor
import project.android.footstamp.ui.theme.SubColor
import project.android.footstamp.ui.theme.TransparentColor
import project.android.footstamp.ui.theme.WhiteColor
import project.android.footstamp.ui.view.profile.ProfileViewModel
import project.android.footstamp.ui.view.util.AlertScreen
import project.android.footstamp.ui.view.util.LoadingScreen

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
                onClickLogOut = { profileViewModel.logOutAlert(context) },
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
    Card(
        modifier = Modifier.fillMaxWidth(0.9f), colors = CardColors(
            BackColor, TransparentColor, TransparentColor, TransparentColor
        )
    ) {
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
    CommonButton(stringResource(id = R.string.profile_edit_title)) { onClickEditButton() }
}

@Composable
fun ProfileMyHistory(
    context: Context, notificationList: List<Notification>, screenHeight: Dp
) {
    SpaceMaker(height = screenHeight / 40f)
    TopBar(text = stringResource(R.string.profile_my_activity))
    Column(
        modifier = Modifier
            .fillMaxWidth(0.9f)
    ) {
        notificationList.forEach { notification ->
            NotificationItem(
                text = notification.profile.nickname + getString(
                    context,
                    R.string.profile_reply_message
                ),
                time = Formatter.dateTimeToFormedString(notification.dateTime),
                message = notification.content
            )
        }
    }
}

@Composable
fun NotificationItem(text: String, time: String, message: String) {
    SpaceMaker(height = 5.dp)
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardColors(BackColor, TransparentColor, TransparentColor, TransparentColor)
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
    onClickLogOut: () -> Unit,
    onClickDeleteProfile: () -> Unit,
) {
    SpaceMaker(height = screenHeight / 20)
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
        if (profileDeleteText == null) {
            CommonButton(
                text = stringResource(R.string.profile_log_out),
                buttonColor = SubColor
            ) { onClickLogOut() }
            CommonButton(
                text = stringResource(R.string.profile_delete_profile),
                buttonColor = SubColor
            ) { onClickDeleteProfile() }
        } else {
            TextInput(
                hint = stringResource(R.string.profile_delete_profile_hint),
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
            TitleLargeText(text = stringResource(R.string.profile_edit), color = BlackColor)
            SpaceMaker(height = screenHeight / 40)

            HorizontalDivider(modifier = Modifier.fillMaxWidth(0.9f))
            SpaceMaker(height = screenHeight / 80)
            TitleText(text = stringResource(R.string.profile_nickname), color = BlackColor)
            SpaceMaker(height = screenHeight / 80)
            TextInput(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(WhiteColor),
                baseText = editProfile.nickname,
                hint = stringResource(R.string.profile_nickname_hint),
                onValueChange = onChangeNickname
            )

            SpaceMaker(height = screenHeight / 80)
            TitleText(text = stringResource(R.string.profile_photo), color = BlackColor)
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
            TitleText(text = stringResource(R.string.profile_about_me), color = BlackColor)
            SpaceMaker(height = screenHeight / 80)
            TextInput(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(WhiteColor),
                baseText = editProfile.aboutMe,
                hint = stringResource(R.string.profile_about_me_hint),
                onValueChange = onChangeAboutMe
            )

            SpaceMaker(height = screenHeight / 80)
            CommonButton(stringResource(R.string.profile_edit_done)) { onEdit() }
            SpaceMaker(height = screenHeight / 80)
        }
    }
}