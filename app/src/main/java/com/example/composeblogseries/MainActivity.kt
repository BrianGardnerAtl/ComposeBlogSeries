package com.example.composeblogseries

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.foundation.Clickable
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.drawVector
import androidx.ui.layout.*
import androidx.ui.material.Button
import androidx.ui.material.MaterialTheme
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
            retweetCount = 10,
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

data class Tweet(
    val displayName: String,
    val handle: String,
    val time: String,
    val content: String,
    val commentCount: Int,
    val retweetCount: Int,
    val likeCount: Int
)

@Composable
fun TweetView(tweet: Tweet) {
    Column {
        UserInfoRow(
            name = tweet.displayName,
            handle = tweet.handle,
            time = tweet.time
        )
        TweetContent(content = tweet.content)
        ActionRow(
            commentCount = tweet.commentCount,
            retweetCount = tweet.retweetCount,
            likeCount = tweet.likeCount
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
    retweetCount: Int,
    likeCount: Int
) {
    val context = ContextAmbient.current
    Row(
        modifier = LayoutWidth.Fill + LayoutPadding(8.dp),
        arrangement = Arrangement.SpaceAround
    ){
        Comment(commentCount) {
            Toast.makeText(context, "Clicked on comment", Toast.LENGTH_SHORT).show()
        }
        Retweet(retweetCount) {
            Toast.makeText(context, "Clicked on retweet", Toast.LENGTH_SHORT).show()
        }
        Like(likeCount) {
            Toast.makeText(context, "Clicked on like", Toast.LENGTH_SHORT).show()
        }
        Share {
            Toast.makeText(context, "Clicked on share", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Comment(count: Int, onClick : () -> Unit) {
    Clickable(onClick = onClick) {
        val icon = vectorResource(R.drawable.ic_comment)
        Row {
            Container(
                height = 24.dp,
                width = 24.dp,
                modifier = drawVector(vectorImage = icon, tintColor = Color.LightGray)
            ) {

            }
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

@Composable
fun Retweet(count: Int, onClick : () -> Unit) {
    Clickable(onClick = onClick) {
        val icon = vectorResource(R.drawable.ic_retweet)
        Row {
            Container(
                height = 24.dp,
                width = 24.dp,
                modifier = drawVector(vectorImage = icon, tintColor = Color.LightGray)
            ) {

            }
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

@Composable
fun Like(count: Int, onClick : () -> Unit) {
    Clickable(onClick = onClick) {
        val icon = vectorResource(R.drawable.ic_like)
        Row {
            Container(
                height = 24.dp,
                width = 24.dp,
                modifier = drawVector(vectorImage = icon, tintColor = Color.LightGray)
            ) {

            }
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

@Composable
fun Share(onClick : () -> Unit) {
    Clickable(onClick = onClick) {
        val icon = vectorResource(R.drawable.ic_share)
        Container(
            height = 24.dp,
            width = 24.dp,
            modifier = drawVector(vectorImage = icon, tintColor = Color.LightGray)
        ) {

        }
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
        retweetCount = 10,
        likeCount = 1000
    )
    MaterialTheme {
        TweetView(
            tweet
        )
    }
}
// endregion