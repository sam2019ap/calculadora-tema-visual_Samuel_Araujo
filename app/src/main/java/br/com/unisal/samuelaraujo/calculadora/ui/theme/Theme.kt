package br.com.unisal.samuelaraujo.calculadora.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/*
 * ColorScheme "Retrô Handheld" - variante clara.
 *
 * Simula uma tela de LCD monocromática NÃO retroiluminada: fundo verde-claro
 * ("pixel apagado") com texto/contornos em verde bem escuro ("pixel aceso").
 *
 * Mapeamento de papéis semânticos usados pelos componentes da calculadora:
 *   - background/surface -> fundo geral da tela e o "vidro" do visor
 *   - surfaceVariant       -> painel do visor (levemente diferente do fundo,
 *                             como se fosse o baixo-relevo da telinha)
 *   - primary/onPrimary    -> botões de OPERADOR (mais destaque que os números)
 *   - secondaryContainer   -> botões NUMÉRICOS (aparência neutra)
 *   - tertiary/onTertiary  -> botão de IGUALDADE (destaque especial, cor âmbar)
 *   - error/onError        -> botão de LIMPAR e mensagens de "Erro" (atenção)
 */
private val RetroLightColorScheme = lightColorScheme(
    primary = RetroVerdeMedio,
    onPrimary = RetroVerdeMaisClaro,
    primaryContainer = RetroVerdeMedio,
    onPrimaryContainer = RetroVerdeMaisClaro,

    secondary = RetroVerdeEscuro,
    onSecondary = RetroVerdeMaisClaro,
    secondaryContainer = RetroVerdeClaro,
    onSecondaryContainer = RetroVerdeEscuro,

    tertiary = RetroAmbar,
    onTertiary = RetroAmbarEscuro,
    tertiaryContainer = RetroAmbar,
    onTertiaryContainer = RetroAmbarEscuro,

    background = RetroVerdeMaisClaro,
    onBackground = RetroVerdeEscuro,

    surface = RetroVerdeMaisClaro,
    onSurface = RetroVerdeEscuro,
    surfaceVariant = RetroVerdeClaro,
    onSurfaceVariant = RetroVerdeEscuro,

    error = RetroVermelho,
    onError = Color.White,

    outline = RetroVerdeEscuro
)

/*
 * ColorScheme "Retrô Handheld" - variante escura.
 *
 * Simula a mesma telinha, agora "retroiluminada": fundo verde bem escuro
 * com o texto em verde claro brilhante - a mesma paleta de 4 tons, só que
 * invertida em relação a qual tom vira fundo e qual vira destaque.
 */
private val RetroDarkColorScheme = darkColorScheme(
    primary = RetroVerdeClaro,
    onPrimary = RetroVerdeEscuro,
    primaryContainer = RetroVerdeClaro,
    onPrimaryContainer = RetroVerdeEscuro,

    secondary = RetroVerdeMaisClaro,
    onSecondary = RetroVerdeEscuro,
    secondaryContainer = RetroVerdeMedio,
    onSecondaryContainer = RetroVerdeMaisClaro,

    tertiary = RetroAmbar,
    onTertiary = RetroAmbarEscuro,
    tertiaryContainer = RetroAmbar,
    onTertiaryContainer = RetroAmbarEscuro,

    background = RetroVerdeEscuro,
    onBackground = RetroVerdeMaisClaro,

    surface = RetroVerdeEscuro,
    onSurface = RetroVerdeMaisClaro,
    surfaceVariant = RetroVerdeMedio,
    onSurfaceVariant = RetroVerdeMaisClaro,

    error = RetroVermelhoClaro,
    onError = RetroVerdeEscuro,

    outline = RetroVerdeMaisClaro
)

/**
 * Tema personalizado da calculadora: identidade visual "Retrô Handheld".
 *
 * Diferente do template padrão do Android Studio, este tema NÃO usa cores
 * dinâmicas (extraídas do papel de parede do usuário) — a proposta é que a
 * calculadora sempre mantenha a MESMA identidade visual retrô em qualquer
 * aparelho, então [dynamicColor] fica desligado por padrão.
 *
 * O parâmetro [darkTheme] continua funcionando normalmente: ele apenas
 * escolhe entre as duas variantes (clara "não retroiluminada" / escura
 * "retroiluminada") da mesma paleta retrô, em vez de trocar de identidade.
 */
@Composable
fun CalculadoraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) RetroDarkColorScheme else RetroLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
