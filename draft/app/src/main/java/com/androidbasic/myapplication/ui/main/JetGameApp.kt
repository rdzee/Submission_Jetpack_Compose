package com.androidbasic.myapplication.ui.main

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.androidbasic.myapplication.R
import com.androidbasic.myapplication.di.Injection
import com.androidbasic.myapplication.factory.ViewModelFactory
import com.androidbasic.myapplication.ui.theme.JetGameAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun JetGameApp(
    modifier: Modifier = Modifier,
    viewModel: JetGameViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository()))
) {
    val groupedGames by viewModel.groupedGames.collectAsState()
    val query by viewModel.query

    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            item {
                SearchBarCom(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = modifier.background(MaterialTheme.colorScheme.primary)
                )
            }
            groupedGames.forEach { (initial, games) ->
                stickyHeader {
                    CharacterHeader(initial)
                }
                items(games, key = { it.rank }) { game ->
                    ConstraintLayout(
                        modifier = modifier.clickable {

                        }
                    ) {
                        val (posterImage, titleText, descText) = createRefs()

                        AsyncImage(
                            model = game.photoUrl,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = modifier
                                .padding(8.dp)
                                .width(80.dp)
                                .height(100.dp)
                                .constrainAs(posterImage) {
                                    top.linkTo(parent.top)
                                }
                        )
                        Text(
                            text = game.title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = modifier
                                .fillMaxWidth()
                                .constrainAs(titleText) {
                                    start.linkTo(posterImage.end, margin = 8.dp)
                                    top.linkTo(parent.top, margin = 16.dp)
                                    width = Dimension.fillToConstraints
                                }
                        )
                        Text(
                            text = game.description,
                            fontSize = 14.sp,
                            maxLines = 2,
                            modifier = modifier
                                .constrainAs(descText) {
                                    top.linkTo(titleText.bottom, margin = 10.dp)
                                    start.linkTo(posterImage.end, margin = 8.dp)
                                }
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 30.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.scrollToItem(index = 0)
                    }
                }
            )
        }
    }
}

@Composable
fun ScrollToTopButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilledIconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = stringResource(R.string.scroll_to_top),
        )
    }
}

@Composable
fun CharacterHeader(
    char: Char,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Text(
            text = char.toString(),
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarCom(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_game))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun JetGameAppPreview() {
    JetGameAppTheme {
        JetGameApp()
    }
}