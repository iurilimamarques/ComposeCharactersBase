package com.example.composecharactersbase.presentation.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composecharactersbase.data.model.Character
import com.example.composecharactersbase.presentation.viewmodel.CharacterViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

@Composable
fun CharacterApp(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = "/list"
    ) {
        composable("/list") {
            CharacterListScreen(navController)
        }

        composable("/detail/{characterId}") { backStackEntry ->
            var characterId = backStackEntry.arguments?.getString("characterId")?.toIntOrNull() ?: -1
            CharacterDetailScreen(characterId, navController)
        }
    }
}

@Composable
fun CharacterListScreen(navController: NavController, characterViewModel: CharacterViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        characterViewModel.fetchAllCharacters()
    }
    val characters by characterViewModel.characters.observeAsState(emptyList())

    // LazyColumn é uma lista otimizada para exibir grandes quantidades de dados.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize() // Preenche todo o espaço disponível.
            .padding(16.dp), // Adiciona um espaçamento interno de 16dp.
        verticalArrangement = Arrangement.spacedBy(12.dp) // Espaçamento entre os itens da lista.
    ) {
        // Para cada personagem na lista, cria um item na LazyColumn.
        items(characters) { character ->
            CharacterCard(character, characterViewModel, navController) // Exibe o cartão do personagem.
        }
    }
}

@Composable
fun CharacterCard(character: Character, characterViewModel: CharacterViewModel, navController: NavController) {
    // Estado que controla se o personagem é favorito ou não.
    var isFavorite by remember { mutableStateOf(character.favorite) }

    // Card é um componente que cria um contêiner com elevação e bordas arredondadas.
    Card(
        onClick = {navController.navigate("/detail/${character.id}")},
        modifier = Modifier
            .fillMaxWidth() // Preenche toda a largura disponível.
            .height(140.dp), // Define a altura do cartão.
        shape = RoundedCornerShape(16.dp), // Define bordas arredondadas.
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Define a elevação do cartão.
    ) {
        // Row organiza os elementos horizontalmente.
        Row(
            modifier = Modifier
                .fillMaxSize() // Preenche todo o espaço disponível no cartão.
                .padding(12.dp), // Adiciona um espaçamento interno de 12dp.
            verticalAlignment = Alignment.CenterVertically // Alinha os elementos verticalmente ao centro.
        ) {
            // Exibe a imagem do personagem.

            AsyncImage(
                model = character.image,
                contentDescription = character.name,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            // Espaçamento horizontal entre a imagem e o texto.
            Spacer(modifier = Modifier.width(16.dp))

            // Coluna para organizar os textos verticalmente.
            Column(
                modifier = Modifier
                    .weight(1f) // Faz com que a coluna ocupe o espaço restante.
                    .fillMaxHeight(), // Preenche toda a altura disponível.
                verticalArrangement = Arrangement.Center // Alinha os textos verticalmente ao centro.
            ) {
                // Exibe o nome do personagem.
                Text(text = character.name, style = MaterialTheme.typography.titleMedium)
                // Exibe o status do personagem.
                Text(text = "Status: ${character.status}")
                // Exibe a espécie do personagem.
                Text(text = "Species: ${character.species}")
            }

            // Botão para marcar/desmarcar o personagem como favorito.
            IconButton(
                onClick = {
                    isFavorite = !isFavorite
                    character.favorite = isFavorite
                    characterViewModel.setCharacterFavorite(character)
                    // TODO: Salvar ou remover dos favoritos usando SharedPreferences.
                }
            ) {
                // Ícone que muda dependendo do estado de favorito.
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = "Favorito", // Descrição do ícone para acessibilidade.
                    tint = if (isFavorite) Color.Yellow else Color.Gray // Cor do ícone.
                )
            }
        }
    }
}

@Composable
fun CharacterDetailScreen(characterId: Int, navController: NavController, characterViewModel: CharacterViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        characterViewModel.getCharacterById(characterId)
    }
    val characterDetail by characterViewModel.characterDetail.observeAsState()

    IconButton(
        onClick = {
            navController.popBackStack("/list", false, false)
        }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Voltar", // Descrição do ícone para acessibilidade.
            tint = Color.Black // Cor do ícone.
        )
    }

    Row(
        modifier = Modifier
            .fillMaxSize() // Preenche todo o espaço disponível no cartão.
            .padding(12.dp), // Adiciona um espaçamento interno de 12dp.
        verticalAlignment = Alignment.CenterVertically // Alinha os elementos verticalmente ao centro.
    ) {
        // Exibe a imagem do personagem.

        AsyncImage(
            model = characterDetail?.image,
            contentDescription = characterDetail?.name,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        // Espaçamento horizontal entre a imagem e o texto.
        Spacer(modifier = Modifier.width(16.dp))

        // Coluna para organizar os textos verticalmente.
        Column(
            modifier = Modifier
                .weight(1f) // Faz com que a coluna ocupe o espaço restante.
                .fillMaxHeight(), // Preenche toda a altura disponível.
            verticalArrangement = Arrangement.Center // Alinha os textos verticalmente ao centro.
        ) {
            // Exibe o nome do personagem.
            Text(
                text = characterDetail?.name.orEmpty(),
                style = MaterialTheme.typography.titleMedium
            )
            // Exibe o status do personagem.
            Text(text = "Status: ${characterDetail?.status}")
            // Exibe a espécie do personagem.
            Text(text = "Species: ${characterDetail?.species}")
        }
    }
}