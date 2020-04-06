package com.example.composeblogseries

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.Model
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Icon
import androidx.ui.foundation.selection.Toggleable
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.drawVector
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.IconButton
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.Ripple
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
        setContent {
            MaterialTheme {
                TweetView(
                        tweet
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
    var commentCount: Int,
    var retweeted: Boolean,
    var retweetCount: Int,
    var liked: Boolean,
    var likeCount: Int
)

@Composable
fun TweetView(tweet: Tweet) {
    val commentClick: (() -> Unit) = {
        tweet.commentCount += 1
    }
    val retweetToggle: ((Boolean) -> Unit) = { retweet ->
        tweet.retweeted = retweet
        if (retweet) {
            tweet.retweetCount = tweet.retweetCount + 1
        } else {
            tweet.retweetCount = tweet.retweetCount - 1
        }
    }
    val likedToggle: ((Boolean) -> Unit) = { liked ->
        tweet.liked = liked
        if (liked) {
            tweet.likeCount = tweet.likeCount + 1
        } else {
            tweet.likeCount = tweet.likeCount - 1
        }
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
        modifier = LayoutPadding(8.dp)
    )
}

// region user info row content
@Composable
fun UserInfoRow(name: String, handle: String, time: String) {
    Row(
        modifier = LayoutPadding(8.dp)
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
        modifier = LayoutPadding(0.dp, 0.dp, 8.dp, 0.dp),
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
        modifier = LayoutPadding(0.dp, 0.dp, 8.dp, 0.dp),
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
        modifier = LayoutWidth.Fill + LayoutPadding(8.dp),
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
    Ripple(bounded = false) {
        Clickable(onClick = onClick) {
            val icon = vectorResource(R.drawable.ic_comment)
            Row {
                Icon(
                    icon = icon,
                    modifier = LayoutSize(24.dp, 24.dp),
                    tint = Color.LightGray
                )
                if (count > 0) {
                    Text(
                        text = "$count",
                        modifier = LayoutPadding(8.dp, 0.dp, 0.dp, 0.dp),
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = Color.LightGray
                        )
                    )
                }
            }
        }
    }
}


@Composable
fun Retweet(count: Int, retweeted: Boolean, onValueChange: (Boolean) -> Unit) {
    Ripple(bounded = false) {
        ToggleImage(
            iconId = R.drawable.ic_retweet,
            count = count,
            checked = retweeted,
            selectedColor = Color.Green,
            onValueChange = onValueChange
        )
    }
}

@Composable
fun Like(count: Int, liked: Boolean, onValueChange: (Boolean) -> Unit) {
    Ripple(bounded = false) {
        ToggleImage(
            iconId = R.drawable.ic_retweet,
            count = count,
            checked = liked,
            selectedColor = Color.Red,
            onValueChange = onValueChange
        )
    }
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
    Toggleable(value = checked, onValueChange = onValueChange) {
        Row {
            Icon(
                icon = icon,
                modifier = LayoutSize(24.dp, 24.dp),
                tint = color
            )
            if (count > 0) {
                Text(
                    text = "$count",
                    modifier = LayoutPadding(8.dp, 0.dp, 0.dp, 0.dp),
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
        modifier = LayoutSize(24.dp, 24.dp)
    ) {
        Icon(
            icon = icon,
            modifier = LayoutSize(24.dp, 24.dp),
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
    MaterialTheme {
        TweetView(
            tweet
        )
    }
}
// endregion