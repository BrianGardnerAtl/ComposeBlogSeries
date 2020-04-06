package com.example.composeblogseries

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.*
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.selection.Toggleable
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.ripple
import androidx.ui.res.vectorResource
import androidx.ui.text.TextStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp
import androidx.ui.unit.sp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tweet = Tweet(
            displayName = "Brian Gardner",
            handle = "@BrianGardnerDev",
            time = "7m",
            content = "This is a test tweet",
            commentCount = 100,
            retweeted = false,
            retweetCount = 10,
            liked = false,
            likeCount = 1000
        )
        val state = mutableStateOf(tweet, ReferentiallyEqual)
        setContent {
            MaterialTheme {
                TweetView(
                    state
                )
            }
        }
    }
}

@Model
data class Tweet(
    val displayName: String,
    val handle: String,
    val time: String,
    val content: String,
    val commentCount: Int,
    val retweeted: Boolean,
    val retweetCount: Int,
    val liked: Boolean,
    val likeCount: Int
)

@Composable
fun TweetView(state: MutableState<Tweet>) {
    val tweet = state.value
    val commentClick: (() -> Unit) = {
        val newCount = tweet.commentCount + 1
        val newTweet = tweet.copy(
            commentCount = newCount
        )
        state.value = newTweet
    }
    val retweetToggle: ((Boolean) -> Unit) = { retweet ->
        val retweetCount = if (retweet) {
            tweet.retweetCount + 1
        } else {
            tweet.retweetCount - 1
        }
        val newTweet = tweet.copy(
            retweeted = retweet,
            retweetCount = retweetCount
        )
        state.value = newTweet
    }
    val likedToggle: ((Boolean) -> Unit) = { liked ->
        val likeCount = if (liked) {
            tweet.likeCount + 1
        } else {
            tweet.likeCount - 1
        }
        val newTweet = tweet.copy(
            liked = liked,
            likeCount = likeCount
        )
        state.value = newTweet
    }
    Column {
        UserInfoRow(
            name = tweet.displayName,
            handle = tweet.handle,
            time = tweet.time
        )
        TweetContent(content = tweet.content)
        ActionRow(
            commentCount = tweet.commentCount,
            commentClick = commentClick,
            retweeted = tweet.retweeted,
            retweetCount = tweet.retweetCount,
            onRetweetChanged = retweetToggle,
            liked = tweet.liked,
            likeCount = tweet.likeCount,
            onLikeChanged = likedToggle
        )
    }
}

@Composable
fun TweetContent(content: String) {
    return Text(
        text = content,
        style = TextStyle(
            color = Color.Black,
            fontSize = 12.sp
        ),
        modifier = Modifier.padding(8.dp)
    )
}

// region user info row content
@Composable
fun UserInfoRow(name: String, handle: String, time: String) {
    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        DisplayName(name)
        Handle(handle)
        PostTime(time)
    }
}

@Composable
fun DisplayName(name: String) {
    Text(
        text = name,
        modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp),
        style = TextStyle(
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun Handle(handle: String) {
    Text(
        text = handle,
        modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp),
        style = TextStyle(
            color = Color.DarkGray,
            fontSize = 12.sp
        )
    )
}

@Composable
fun PostTime(time: String) {
    Text(
        text = "time",
        style = TextStyle(
            color = Color.DarkGray,
            fontSize = 12.sp
        )
    )
}
// endregion

// region action row contents
@Composable
fun ActionRow(
    commentCount: Int,
    commentClick: (() -> Unit),
    retweeted: Boolean,
    retweetCount: Int,
    onRetweetChanged: (Boolean) -> Unit,
    liked: Boolean,
    likeCount: Int,
    onLikeChanged: (Boolean) -> Unit
) {
    val context = ContextAmbient.current
    Row(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        arrangement = Arrangement.SpaceAround
    ){
        Comment(commentCount, commentClick)
        Retweet(retweetCount, retweeted, onRetweetChanged)
        Like(likeCount, liked, onLikeChanged)
        Share {
            Toast.makeText(context, "Clicked on share", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Comment(count: Int, onClick : () -> Unit) {
    Clickable(
        onClick = onClick,
        modifier = Modifier.ripple(bounded = false)
    ) {
        val icon = vectorResource(R.drawable.ic_comment)
        Row {
            Icon(
                asset = icon,
                modifier = Modifier.preferredSize(24.dp),
                tint = Color.LightGray
            )
            if (count > 0) {
                Text(
                    text = "$count",
                    modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp),
                    style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.LightGray
                    )
                )
            }
        }
    }
}


@Composable
fun Retweet(count: Int, retweeted: Boolean, onValueChange: (Boolean) -> Unit) {
    ToggleImage(
        iconId = R.drawable.ic_retweet,
        count = count,
        checked = retweeted,
        selectedColor = Color.Green,
        onValueChange = onValueChange
    )
}

@Composable
fun Like(count: Int, liked: Boolean, onValueChange: (Boolean) -> Unit) {
    ToggleImage(
        iconId = R.drawable.ic_retweet,
        count = count,
        checked = liked,
        selectedColor = Color.Red,
        onValueChange = onValueChange
    )
}

@Composable
fun ToggleImage(
    @DrawableRes iconId: Int,
    count: Int,
    checked: Boolean,
    selectedColor: Color,
    onValueChange: ((Boolean) -> Unit)
) {
    val icon = vectorResource(iconId)
    val color = if (checked) {
        selectedColor
    } else {
        Color.LightGray
    }
    Toggleable(
        value = checked,
        modifier = Modifier.ripple(bounded = false),
        onValueChange = onValueChange
    ) {
        Row {
            Icon(
                asset = icon,
                modifier = Modifier.preferredSize(24.dp),
                tint = color
            )
            if (count > 0) {
                Text(
                    text = "$count",
                    modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp),
                    style = TextStyle(
                        color = color,
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}

@Composable
fun Share(onClick : () -> Unit) {
    val icon = vectorResource(R.drawable.ic_share)
    IconButton(
        onClick = onClick,
        modifier = Modifier.preferredSize(24.dp)
    ) {
        Icon(
            asset = icon,
            modifier = Modifier.preferredSize(24.dp),
            tint = Color.LightGray
        )
    }
}
// endregion

// region preview functions
@Preview
@Composable
fun TwitterPreview() {
    val tweet = Tweet(
        displayName = "Brian Gardner",
        handle = "@BrianGardnerDev",
        time = "7m",
        content = "This is a test tweet",
        commentCount = 100,
        retweeted = false,
        retweetCount = 10,
        liked = false,
        likeCount = 1000
    )
    val state = mutableStateOf(tweet, ReferentiallyEqual)
    MaterialTheme {
        TweetView(
            state
        )
    }
}
// endregion