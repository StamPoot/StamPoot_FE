package project.android.footstamp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import project.android.footstamp.R
import project.android.footstamp.ui.theme.BlackColor
import project.android.footstamp.ui.theme.MainColor

data class PolicyModel(
    val title: String,
    val content: String
)

@Composable
fun Policy() {
    val headLine = PolicyModel(
        title = stringResource(id = R.string.policy_headline_title),
        content = stringResource(id = R.string.policy_headline_content),
    )
    val information = PolicyModel(
        title = stringResource(id = R.string.policy_information_title),
        content = stringResource(id = R.string.policy_information_content),
    )
    val thirdParty = PolicyModel(
        title = stringResource(id = R.string.policy_thirdParty_title),
        content = stringResource(id = R.string.policy_thirdParty_content),
    )
    val optOutRight = PolicyModel(
        title = stringResource(id = R.string.policy_optOutRight_title),
        content = stringResource(id = R.string.policy_optOutRight_content),
    )
    val dataRetention = PolicyModel(
        title = stringResource(id = R.string.policy_dataRetention_title),
        content = stringResource(id = R.string.policy_dataRetention_content),
    )
    val children = PolicyModel(
        title = stringResource(id = R.string.policy_children_title),
        content = stringResource(id = R.string.policy_children_content),
    )
    val security = PolicyModel(
        title = stringResource(id = R.string.policy_security_title),
        content = stringResource(id = R.string.policy_security_content),
    )
    val change = PolicyModel(
        title = stringResource(id = R.string.policy_change_title),
        content = stringResource(id = R.string.policy_change_content),
    )
    val consent = PolicyModel(
        title = stringResource(id = R.string.policy_consent_title),
        content = stringResource(id = R.string.policy_consent_content),
    )
    val contact = PolicyModel(
        title = stringResource(id = R.string.policy_contact_title),
        content = stringResource(id = R.string.policy_contact_content),
    )
    val policyList = listOf(
        headLine,
        information,
        thirdParty,
        optOutRight,
        dataRetention,
        children,
        security,
        change,
        consent,
        contact
    )

    LazyColumn {
        items(policyList.size) {
            PolicyItem(policy = policyList[it])
        }
    }
}

@Composable
fun PolicyItem(policy: PolicyModel) {
    Column(modifier = Modifier.padding(horizontal = 5.dp)) {
        SpaceMaker(height = 10.dp)
        TitleText(text = policy.title, color = MainColor)
        SpaceMaker(height = 10.dp)
        BodyText(text = policy.content, color = BlackColor, maxLines = 50)
        SpaceMaker(height = 10.dp)
    }
}