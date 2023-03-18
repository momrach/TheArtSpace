package com.example.theartspace

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theartspace.ui.theme.TheArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheArtSpaceTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContent()
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier) {
    var deltas = 0f
    var TAG ="MainContent"

    //Estado para los botones
    var imageNum by remember {
        mutableStateOf(0)
    }

    //Estado para el Draggable
    val draggableState = rememberDraggableState(
        onDelta = {delta ->
                //Log.d(TAG, "Dragged $delta")
                deltas+=delta
        },
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier
        ,
    ){
        ArtImage(
            imageNum = imageNum,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .draggable(
                    state = draggableState,
                    orientation = Orientation.Horizontal,
                    onDragStarted = { Log.d(TAG, "Drag started") },
                    onDragStopped = {
                        Log.d(TAG, "Drag stopped with deltas = $deltas")
                        Log.d(TAG,"Current ImageNum $imageNum")
                        if (deltas >= 100) {
                            imageNum = (imageNum - 1)
                            if (imageNum < 0)
                                imageNum = 4
                        }
                        else if(deltas<-100) {
                            imageNum = (imageNum + 1) % 5
                        }
                        Log.d(TAG,"Final ImageNum $imageNum")
                        deltas=0f
                        Log.d(TAG,"Drag initialized deltas = $deltas ")
                        deltas=0f
                    },
                )
        )
        Botonera(
            modifier = Modifier.fillMaxWidth(),//.border(1.dp, Color.Red),
            onClickPreviousAction = {
                imageNum=(imageNum-1)
                if (imageNum<0)
                    imageNum=4
            },
            onClickNextAction = { imageNum=(imageNum+1)%5},
        )
    }
}

@Composable
fun ArtImage(imageNum: Int ,
             modifier: Modifier=Modifier
){
    var imageId : Int //= R.drawable.bourgeois
    var title : String
    var description: String

    when(imageNum){
        0 -> {
            imageId = R.drawable.bourgeois
            title = stringResource(id = R.string.TBourgeois)
            description = stringResource(id = R.string.LouiseBourgeois)
        }
        1 -> {
            imageId = R.drawable.hockney
            title = stringResource(id = R.string.THockney)
            description = stringResource(id = R.string.DavidHockney)
        }
        2 -> {
            imageId = R.drawable.koons
            title = stringResource(id = R.string.TKoons)
            description = stringResource(id = R.string.JeffKoons)
        }
        3 -> {
            imageId = R.drawable.imagechristina
            title = stringResource(id = R.string.TMagritte)
            description = stringResource(id = R.string.RenéMagritte)
        }
        4 -> {
            imageId = R.drawable.lovers
            title = stringResource(id = R.string.TDalí)
            description = stringResource(id = R.string.SalvadorDalí)
        }
        else -> {
            imageId = R.drawable.img_placeholder
            title = ""
            description = ""
        }
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.padding(top=10.dp)//.border(1.dp, Color.Blue)
    ) {
        Surface(
            shadowElevation = 30.dp,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .weight(3f)
                //.fillMaxHeight(0.65f)
                //.padding(top = 10.dp)
        ) {
            Image(
                painter = painterResource(id = imageId), contentDescription = "image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .padding(10.dp)
            )
        }

        Surface(
            tonalElevation = 10.dp,
            shadowElevation = 30.dp,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .padding(top=10.dp)
                .weight(1f)
        ) {
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = description,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.size(5.dp))
            }
        }
    }
}

@Composable
fun Botonera(
    onClickPreviousAction:  ()-> Unit,
    onClickNextAction:  ()-> Unit,
    modifier: Modifier=Modifier
){
    val buttonWidth = 120.dp
    Row(horizontalArrangement = Arrangement.spacedBy(0.dp,alignment = Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Bottom,
        modifier = modifier//.padding(bottom = 10.dp)
    ) {

        Button(onClick = onClickPreviousAction ,
            modifier = Modifier
                .padding(start = 50.dp, end = 50.dp)
                .width(buttonWidth)
        ) {
            Text(text = "Previous")
        }

        Button(onClick = onClickNextAction,
            modifier = Modifier
                .padding(20.dp, 0.dp, 20.dp, 0.dp)
                .width(buttonWidth)
        ) {
            Text(text = "Next")
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TheArtSpaceTheme {
        Surface() {
            MainContent()
        }
    }
}