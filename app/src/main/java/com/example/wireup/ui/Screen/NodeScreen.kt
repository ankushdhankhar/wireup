package com.example.wireup.ui.Screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.wireup.Navigation.NavigationItem
import com.example.wireup.ui.Components.CircularImage
import com.example.wireup.ui.Components.NumberText
import com.example.wireup.ui.Components.TweetActionRow
import com.example.wireup.ui.Components.TweetItem
import com.example.wireup.util.DateUtil
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.UUID
import kotlin.random.Random

data class Tweet(
    val id: String = UUID.randomUUID().toString(),
    val description: String,
    val userId: String,
    val comments: List<String> = emptyList(),
    val likeCount: Int = 0,
    val retweetCount: Int = 0,
    val bookmarkCount: Int = 0,
    val imageUrl: List<String> = emptyList(),
    val timeStamp: Long = System.currentTimeMillis()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NodeScreen(navController: NavHostController) {
    Column {
        TopAppBar(title = {
            Text(
                "Nodes", fontSize = 18.sp
            )
        }, navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        )
        Divider()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 3.dp, end = 3.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                val div = 5
                repeat(div) { index ->
                    MainNode()

                    if (index < div-1) {
                        Divider(
                            modifier = Modifier
                                .fillMaxWidth(),
                            thickness = 2.dp,
                            color = Color.Gray
                        )
                    }
                }


            }






    }
}

@Composable
fun MainNode(){
    val username = FirebaseAuth.getInstance().currentUser?.email.toString()
    val userimage = "https://static.vecteezy.com/system/resources/thumbnails/005/545/335/small/user-sign-icon-person-symbol-human-avatar-isolated-on-white-backogrund-vector.jpg"
    var isVisible by remember { mutableStateOf(false) }
    var retweets = 34
    var likes = 56
    var bookmarks = 5
    var isRetweeted by rememberSaveable {
        mutableStateOf(false)
    }
    var isLiked by rememberSaveable {
        mutableStateOf(false)
    }
    var isBookmarked by rememberSaveable {
        mutableStateOf(false)
    }
    var isFollowed by rememberSaveable {
        mutableStateOf(false)
    }
    Row(modifier=Modifier
            .fillMaxWidth()
            .padding(start = 4.dp , top = 2.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CircularImage(imageUrl = userimage, imageSize = 38.dp)
        Spacer(Modifier.width(8.dp))
        Column(Modifier.weight(1f)) {
            Text(username, fontWeight = FontWeight.W600, fontSize = 15.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                isFollowed = !isFollowed
            },
            colors = ButtonColors(
                containerColor =
                if (isFollowed) {
                    MaterialTheme.colorScheme.background
                } else {
                    MaterialTheme.colorScheme.background
                },
                contentColor = if (isFollowed) {
                    MaterialTheme.colorScheme.onBackground
                } else {
                    MaterialTheme.colorScheme.onBackground
                },
                disabledContentColor = Color.Gray,
                disabledContainerColor = Color.LightGray
            )
        ) {
            Text(if (isFollowed) "Following" else "Follow")
        }}
//    Spacer(modifier = Modifier
//        .height(1.dp))
    Column(Modifier
        .fillMaxWidth()
        .padding(start =50.dp)) {
        Text("Jetpack compose for UI development\n" +
                "Kotlin for programming\n" +
                "MVVM architecture\n" +
                "Coil library for dynamic Image loading\n" +
                "Retrofit for REST API data consuming\n" +
                "Jetpack compose Navigation\n" +
                "Lazy list, Card, Other composable functions", fontSize = 15.sp , fontWeight = FontWeight.W400)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            DateUtil.getDateTime(System.currentTimeMillis()),
            color = Color.Gray,
            fontSize = 11.sp ,
            fontWeight = FontWeight.W400
        )
        Spacer(modifier = Modifier.height(8.dp))
//        androidx.compose.material3.Divider(thickness = 1.dp, color = Color.Gray)
        TweetActionRow(
            isLiked, isRetweeted, isBookmarked,
            likes,
            retweets,
            bookmarks,
            onLikeClicked = {
                if (it)
                    likes++
                else
                    likes--
                isLiked = it
            }, onRetweetClicked = {
                if (it)
                    retweets++
                else
                    retweets--
                isRetweeted = it
            }, onBookmarkClicked = {
                if (it)
                    bookmarks++
                else
                    bookmarks--
                isBookmarked = it
            }) {
            isVisible = !isVisible
        }
//        androidx.compose.material3.Divider(thickness = 1.dp, color = Color.Gray)
    }

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        LazyColumn(
            modifier = Modifier
                .height(420.dp) // Set the desired height here
                .drawBehind {
                    drawLine(
                        color = Color.LightGray,
                        start = Offset(150f, 0f),
                        end = Offset(150f, this.size.height)
                    )
                }
        ) {
            // ... rest of your code
            val urls = listOf(
                "https://3.bp.blogspot.com/-VVp3WvJvl84/X0Vu6EjYqDI/AAAAAAAAPjU/ZOMKiUlgfg8ok8DY8Hc-ocOvGdB0z86AgCLcBGAsYHQ/s1600/jetpack%2Bcompose%2Bicon_RGB.png",
                "https://vectorportal.com/storage/bD8Fzwwh5EDWD8YJkJfCCQCwQ8pxMUBIQaCbmKaZ.jpg",
                "https://vectorportal.com/storage/anime-avatar.jpg",
                "https://vectorportal.com/storage/aKJ32lYqZ7wSaC2f0NIMZEUh4hhjlVETzKZ3FjyR.jpg",
                "https://vectorportal.com/storage/UxFA72dcdu4df3y1hyEpAUYbpqSecbrhnjck3x77.jpg",
                "https://vectorportal.com/storage/Eg0cerMrth5t1FDREtUJQr8RmvmXXvA9XEsL7tcH.jpg",
                "https://cdn.pixabay.com/photo/2023/05/28/03/34/flowers-8022731_1280.jpg",
                "https://cdn.pixabay.com/photo/2023/07/14/10/50/flower-8126748_1280.jpg",
            )
            items(5) {
                TweetItem(tweet = Tweet(
                    description = "Incorporate and convert Java code into #Kotlin using #Android Studio, and learn Kotlin language conventions along the way. You’ll also learn how to write Kotlin code to make it callable from Java code.",
                    userId = FirebaseAuth.getInstance().currentUser?.email.toString(),
                    comments = listOf("very nice"),
                    likeCount = 203,
                    retweetCount = 50,
                    bookmarkCount = 135,
                    imageUrl = urls), onCommentClick = { }) {}
            }
        }
    }

}