package br.com.unisal.samuelaraujo.calculadora.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/*
 * Tipografia "Retrô Handheld".
 *
 * Fonte monoespaçada em todos os estilos, para reforçar a ideia de um
 * display digital segmentado. A hierarquia entre o visor e os botões é
 * feita pela ESCALA (tamanho/peso), e não por trocar de família de fonte:
 *
 *   - displayLarge: usado no visor da calculadora - bem grande e em negrito,
 *     para ser claramente o elemento de maior destaque da tela.
 *   - labelLarge: estilo padrão que os componentes Button do Material 3
 *     aplicam automaticamente ao texto interno - por isso todos os botões
 *     (números, operadores, igual, limpar) já herdam esse estilo sem
 *     precisar declarar fonte/tamanho manualmente em cada um.
 *   - titleLarge: usado no pequeno cabeçalho acima do visor.
 */
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 48.sp,
        lineHeight = 52.sp,
        letterSpacing = 1.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 2.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    )
)
