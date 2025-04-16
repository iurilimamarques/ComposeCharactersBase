package com.example.composecharactersbase.presentation.ui

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.rememberAsyncImagePainter
import com.example.composecharactersbase.data.model.Character
import com.example.composecharactersbase.presentation.viewmodel.CharacterViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun CharacterApp(characterViewModel: CharacterViewModel = hiltViewModel()) {
    // Função principal que inicia a tela do aplicativo.
    // Aqui chamamos a tela que lista os personagens.
//    CharacterListScreen(characterViewModel)
}

@Composable
fun CharacterListScreen(characterViewModel: CharacterViewModel = hiltViewModel()) {
    characterViewModel.fetchUser()
    val characters by characterViewModel.characters.observeAsState(emptyList())


    // Lista de personagens mockados (dados fictícios para teste).
//    val characters = listOf(
//        CharacterMock(
//            name = "Rick Sanchez",
//            status = "Alive",
//            species = "Human",
//            imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
//        ),
//        CharacterMock(
//            name = "Morty Smith",
//            status = "Alive",
//            species = "Human",
//            imageUrl = "https://rickandmortyapi.com/api/character/avatar/2.jpeg"
//        ),
//        CharacterMock(
//            name = "Summer Smith",
//            status = "Alive",
//            species = "Human",
//            imageUrl = "https://rickandmortyapi.com/api/character/avatar/3.jpeg"
//        )
//    )

    // LazyColumn é uma lista otimizada para exibir grandes quantidades de dados.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize() // Preenche todo o espaço disponível.
            .padding(16.dp), // Adiciona um espaçamento interno de 16dp.
        verticalArrangement = Arrangement.spacedBy(12.dp) // Espaçamento entre os itens da lista.
    ) {
        // Para cada personagem na lista, cria um item na LazyColumn.
        items(characters) { character ->
            CharacterCard(character) // Exibe o cartão do personagem.
        }
    }
}

@Composable
fun CharacterCard(character: Character) {
    // Estado que controla se o personagem é favorito ou não.
    var isFavorite by remember { mutableStateOf(false) }

    // Card é um componente que cria um contêiner com elevação e bordas arredondadas.
    Card(
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
            Image(
                painter = rememberAsyncImagePainter(model = character.imageUrl), // Carrega a imagem da URL.
                contentDescription = "Character image", // Descrição da imagem para acessibilidade.
                modifier = Modifier
                    .size(100.dp) // Define o tamanho da imagem.
                    .clip(RoundedCornerShape(12.dp)) // Adiciona bordas arredondadas à imagem.
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
                    isFavorite = !isFavorite // Alterna o estado de favorito.
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

// Classe de dados que representa um personagem.
data class CharacterMock(
    val name: String, // Nome do personagem.
    val status: String, // Status do personagem (ex.: Vivo, Morto).
    val species: String, // Espécie do personagem (ex.: Humano, Alien).
    val imageUrl: String // URL da imagem do personagem.
)
