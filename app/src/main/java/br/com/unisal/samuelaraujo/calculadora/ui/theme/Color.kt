package br.com.unisal.samuelaraujo.calculadora.ui.theme

import androidx.compose.ui.graphics.Color

/*
 * Paleta "Retrô Handheld"
 *
 * Inspirada na tela de LCD monocromática (4 tons de verde) dos consoles
 * portáteis clássicos do fim dos anos 80/90. A ideia é que o app inteiro
 * pareça estar "dentro" de uma telinha retrô, e que os botões lembrem os
 * controles físicos coloridos desses aparelhos (cruzeta neutra, botões de
 * ação em destaque, luz de energia vermelha).
 *
 * As 4 tons de verde abaixo reproduzem a paleta clássica de 4 níveis de
 * cinza-esverdeado (do mais claro/pixel apagado ao mais escuro/pixel aceso).
 */
val RetroVerdeMaisClaro = Color(0xFF9BBC0F) // "pixel apagado" - fundo da tela no modo claro
val RetroVerdeClaro = Color(0xFF8BAC0F)     // tom intermediário claro - superfícies/painéis
val RetroVerdeMedio = Color(0xFF306230)     // tom intermediário escuro - botões de operação
val RetroVerdeEscuro = Color(0xFF0F380F)    // "pixel aceso" - texto/contornos

// Acento âmbar: referência aos botões de ação (A/B) dos consoles clássicos,
// usado para dar destaque especial ao botão de igualdade.
val RetroAmbar = Color(0xFFFFB300)
val RetroAmbarEscuro = Color(0xFF241C00) // texto sobre o âmbar, alto contraste

// Vermelho: referência ao clássico LED vermelho de "power on", usado aqui
// como cor de atenção/erro (botão Limpar e mensagens de "Erro").
val RetroVermelho = Color(0xFFB3261E)
val RetroVermelhoClaro = Color(0xFFEF5350) // variante mais clara, usada no modo escuro
