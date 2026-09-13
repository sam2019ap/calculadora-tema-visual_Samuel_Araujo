package br.com.unisal.samuelaraujo.calculadora.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

/*
 * Escala de formas "Retrô Handheld".
 *
 * O objetivo é que a hierarquia entre os tipos de botão apareça também no
 * formato, não só na cor:
 *   - extraSmall/small: cantos quase retos, usados nos botões de ação
 *     "utilitária" (Limpar, Apagar, Trocar sinal) - transmite uma sensação
 *     mais "de alerta/controle", menos convidativa que um botão circular.
 *   - medium: retângulo bem arredondado, usado nos operadores - continua
 *     "convidativo" mas visualmente diferente dos números.
 *   - large/extraLarge: totalmente circular - usado nos números e no botão
 *     de igualdade, remetendo aos botões redondos de ação (A/B) dos
 *     consoles portáteis clássicos.
 */
val Shapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(18.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(50) // valor alto o suficiente para virar um círculo/pílula
)
