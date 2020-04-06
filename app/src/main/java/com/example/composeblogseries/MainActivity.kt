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
        setContent {
            MaterialTheme {
                TweetView()
            }
        }
    }
}

@Composable
fun TweetView() {
    Column {
        UserInfoRow(
            name = "Brian Gardner",
            handle = "@BrianGardnerDev",
            time = "7m"
        )
        TweetContent(content = "Sample tweet content")
        ActionRow()
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
fun ActionRow() {
    val context = ContextAmbient.current
    Row(
        modifier = LayoutWidth.Fill + LayoutPadding(8.dp),
        arrangement = Arrangement.SpaceAround
    ){
        Comment {
            Toast.makeText(context, "Clicked on comment", Toast.LENGTH_SHORT).show()
        }
        Retweet {
            Toast.makeText(context, "Clicked on retweet", Toast.LENGTH_SHORT).show()
        }
        Like {
            Toast.makeText(context, "Clicked on like", Toast.LENGTH_SHORT).show()
        }
        Share {
            Toast.makeText(context, "Clicked on share", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun Comment(onClick : () -> Unit) {
    Clickable(onClick = onClick) {
        val icon = vectorResource(R.drawable.ic_comment)
        Container(
                height = 24.dp,
                width = 24.dp,
                modifier = drawVector(vectorImage = icon, tintColor = Color.LightGray)
        ) {

        }
    }
}

@Composable
fun Retweet(onClick : () -> Unit) {
    Clickable(onClick = onClick) {
        val icon = vectorResource(R.drawable.ic_retweet)
        Container(
                height = 24.dp,
                width = 24.dp,
                modifier = drawVector(vectorImage = icon, tintColor = Color.LightGray)
        ) {

        }
    }
}

@Composable
fun Like(onClick : () -> Unit) {
    Clickable(onClick = onClick) {
        val icon = vectorResource(R.drawable.ic_like)
        Container(
                height = 24.dp,
                width = 24.dp,
                modifier = drawVector(vectorImage = icon, tintColor = Color.LightGray)
        ) {

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
    MaterialTheme {
        TweetView()

    }
}
// endregion