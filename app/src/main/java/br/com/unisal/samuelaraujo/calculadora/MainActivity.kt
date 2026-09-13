/*
 * Calculadora com pilhas de operadores e operandos
 *
 * Aluno: Samuel Araujo
 * RA: 210025640
 * Curso: Engenharia da Computação
 * Instituição: UNISAL - Campus Campinas, Unidade São José
 */
package br.com.unisal.samuelaraujo.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import br.com.unisal.samuelaraujo.calculadora.ui.theme.CalculadoraTheme
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.cos
import kotlin.math.tan
import kotlin.math.sqrt


class MainActivity : ComponentActivity() {
    // Texto atualmente exibido no visor (o "estado" que a UI observa e redesenha
    // automaticamente sempre que muda, por causa do "by mutableStateOf").
    var visor by mutableStateOf("0")

    // Pilhas usadas nas operacoes BINARIAS (soma, subtracao, multiplicacao,
    // divisao, potenciacao, percentual): guardam, respectivamente, o operador
    // escolhido e o operando que estava no visor no momento em que o operador
    // foi pressionado. Sao resolvidas em igualdade().
    val pilhaOperador = mutableListOf<String>()
    val pilhaOperando = mutableListOf<String>()

    // true logo apos um operador, uma funcao unaria, Pi ou "=" - indica que o
    // proximo digito deve comecar um numero novo em vez de continuar o atual.
    var aguardandoOperando = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    criaCalculadora(visor)
                }
            }
        }
    }

    @Composable
    fun criaCalculadora(visor:String) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pequeno cabecalho: reforca a hierarquia visual (titulo > visor > botoes)
            // e usa a cor "onBackground" do tema, que ja vem no tom certo de contraste
            // tanto na variante clara quanto na escura do tema retro.
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                text = "CALCULADORA RETRÔ"
            )
            Spacer(modifier = Modifier.height(12.dp))

            // "Painel" do visor: um bloco com cor de superficie propria (surfaceVariant),
            // forma arredondada (shapes.medium) e uma borda fina na cor "outline",
            // simulando o vidro/moldura de uma telinha de LCD retro. O componente aqui
            // so indica os PAPEIS semanticos (surfaceVariant/onSurfaceVariant/outline);
            // quem decide a cor de verdade e o ColorScheme escolhido no tema.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.medium
                    )
                    .border(
                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline),
                        shape = MaterialTheme.shapes.medium
                    )
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = visor
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            // Linha 1: percentual e as operacoes binarias basicas (divisao, multiplicacao, subtracao).
            Row(
               horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                criaBotaoPequeno("%", BotaoOperacao.PERCENTUAL)
                criaBotaoPequeno("/", BotaoOperacao.DIVISAO)
                criaBotaoPequeno("*", BotaoOperacao.MULTIPLICACAO)
                criaBotaoPequeno("-", BotaoOperacao.SUBTRACAO)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Linha 2: funcoes trigonometricas (unarias) e a constante Pi.
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                criaBotaoPequeno("Sin", BotaoOperacao.SENO)
                criaBotaoPequeno("Cos", BotaoOperacao.COSSENO)
                criaBotaoPequeno("Tan", BotaoOperacao.TANGENTE)
                criaBotaoPequeno("Pi", BotaoOperacao.PI)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Linha 3: mais funcoes unarias (raiz, fatorial, inverso) e a potenciacao (binaria).
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                criaBotaoPequeno("Sqrt", BotaoOperacao.RAIZQUADRADA)
                criaBotaoPequeno("^", BotaoOperacao.POTENCIACAO)
                criaBotaoPequeno("!", BotaoOperacao.FATORIAL)
                criaBotaoPequeno("Inv", BotaoOperacao.INVERSO)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Linhas 4 a 6: teclado numerico (7-9, 4-6, 1-3), com soma, virgula e igualdade
            // ocupando a ultima coluna de cada linha.
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                criaBotaoPequeno("7", BotaoOperacao.SETE)
                criaBotaoPequeno("8", BotaoOperacao.OITO)
                criaBotaoPequeno("9", BotaoOperacao.NOVE)
                criaBotaoPequeno("+", BotaoOperacao.SOMA)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                criaBotaoPequeno("4", BotaoOperacao.QUATRO)
                criaBotaoPequeno("5",BotaoOperacao.CINCO)
                criaBotaoPequeno("6",BotaoOperacao.SEIS)
                criaBotaoPequeno(".",BotaoOperacao.VIRGULA)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                criaBotaoPequeno("1",BotaoOperacao.UM)
                criaBotaoPequeno("2",BotaoOperacao.DOIS)
                criaBotaoPequeno("3",BotaoOperacao.TRES)
                criaBotaoPequeno("=", BotaoOperacao.IGUALDADE)
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Linha 7: controles finais - trocar sinal, zero, limpar e apagar ultimo digito.
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                criaBotaoPequeno("+/-",BotaoOperacao.TROCARSINAL)
                criaBotaoPequeno("0", BotaoOperacao.ZERO)
                criaBotaoPequeno("C",BotaoOperacao.LIMPAR)
                criaBotaoPequeno("<-",BotaoOperacao.APAGAR)
            }
        }
    }

    /**
     * Categorias visuais de botao. Cada categoria mapeia para um papel
     * semantico do MaterialTheme.colorScheme (nunca uma cor fixa) e para uma
     * forma da escala MaterialTheme.shapes, preservando a hierarquia pedida:
     *   - NUMERO: aparencia neutra (secondaryContainer), formato circular.
     *   - OPERADOR: mais destaque que os numeros (primary), retangulo arredondado.
     *   - IGUAL: destaque especial (tertiary, acento ambar), formato circular.
     *   - LIMPAR: cor de atencao/erro (error), formato mais reto/anguloso.
     *   - UTILITARIO (apagar, trocar sinal): neutro mas distinto dos numeros
     *     (surfaceVariant), formato mais reto, para nao competir com os numeros.
     */
    private enum class TipoBotao { NUMERO, OPERADOR, IGUAL, LIMPAR, UTILITARIO }

    private fun tipoDoBotao(identificador: BotaoOperacao): TipoBotao {
        return when (identificador) {
            BotaoOperacao.IGUALDADE -> TipoBotao.IGUAL
            BotaoOperacao.LIMPAR -> TipoBotao.LIMPAR
            BotaoOperacao.APAGAR, BotaoOperacao.TROCARSINAL -> TipoBotao.UTILITARIO
            else -> {
                if (identificador.ordinal <= BotaoOperacao.VIRGULA.ordinal) {
                    TipoBotao.NUMERO
                } else {
                    TipoBotao.OPERADOR
                }
            }
        }
    }

    @Composable
    fun criaBotaoPequeno(texto:String, identificador: BotaoOperacao) {
        val tipo = tipoDoBotao(identificador)

        // Cores: sempre a partir de papeis semanticos do MaterialTheme.colorScheme.
        // O componente nao decide "qual cor fica bonita" - ele so diz qual e o seu
        // papel (numero, operador, igual...) e o tema (Theme.kt) e quem determina
        // a aparencia final de cada papel.
        val cores = when (tipo) {
            TipoBotao.NUMERO -> ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
            TipoBotao.OPERADOR -> ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
            TipoBotao.IGUAL -> ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
            TipoBotao.LIMPAR -> ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
            TipoBotao.UTILITARIO -> ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Formas: numeros e igual sao circulares (extraLarge), operadores usam
        // um retangulo bem arredondado (medium), e limpar/utilitario usam um
        // formato mais reto (small) - reforcando a hierarquia tambem na geometria,
        // nao so na cor.
        val forma = when (tipo) {
            TipoBotao.NUMERO, TipoBotao.IGUAL -> MaterialTheme.shapes.extraLarge
            TipoBotao.OPERADOR -> MaterialTheme.shapes.medium
            TipoBotao.LIMPAR, TipoBotao.UTILITARIO -> MaterialTheme.shapes.small
        }

        Button(
            modifier = Modifier.width(80.dp).height(50.dp),
            onClick = {
                if (identificador.ordinal <= BotaoOperacao.VIRGULA.ordinal){
                    numPress(identificador)
                } else {
                    opPress(identificador)
                }
            },
            shape = forma,
            colors = cores
        ) {
            Text(texto)
        }
    }

    fun numPress(identificador: BotaoOperacao){
        // Se o visor estiver mostrando um erro, qualquer novo dígito começa um número novo.
        if (visor == "Erro") {
            visor = "0"
            aguardandoOperando = false
        }

        if(aguardandoOperando){
            visor = "0"
            aguardandoOperando = false
        }

        if((visor == "0") && (identificador.name == BotaoOperacao.ZERO.name)){
            return
        }

        if((visor.contains(",")) && (identificador.name == BotaoOperacao.VIRGULA.name)){
            return
        }

        var tmp = identificador.name
        if(tmp == BotaoOperacao.VIRGULA.name){
            tmp = ","
        } else{
            tmp = identificador.ordinal.toString()
        }

        if(visor.length == 1 && visor == "0"){
            visor = tmp
        } else {
            visor += tmp
        }

    }

    fun opPress(identificador: BotaoOperacao) {
        when (identificador) {
            BotaoOperacao.LIMPAR -> {
                pilhaOperador.clear()
                pilhaOperando.clear()
                aguardandoOperando = false
                visor = "0"
            }

            BotaoOperacao.APAGAR -> apagarUltimoDigito()

            BotaoOperacao.TROCARSINAL -> trocarSinalDoVisor()

            BotaoOperacao.PI -> {
                // A constante Pi pode ser considerada como 3.14, conforme especificado.
                visor = "3,14"
                aguardandoOperando = true
            }

            BotaoOperacao.SENO -> aplicarOperacaoUnaria { valor -> sin(Math.toRadians(valor)) }
            BotaoOperacao.COSSENO -> aplicarOperacaoUnaria { valor -> cos(Math.toRadians(valor)) }
            BotaoOperacao.TANGENTE -> aplicarOperacaoUnaria { valor -> tan(Math.toRadians(valor)) }

            BotaoOperacao.RAIZQUADRADA -> aplicarOperacaoUnaria { valor ->
                if (valor < 0) Double.NaN else sqrt(valor)
            }

            BotaoOperacao.INVERSO -> aplicarOperacaoUnaria { valor ->
                if (valor == 0.0) Double.NaN else 1.0 / valor
            }

            BotaoOperacao.FATORIAL -> aplicarOperacaoUnaria { valor -> fatorial(valor) }

            BotaoOperacao.IGUALDADE -> igualdade()

            // Operacoes binarias: soma, subtracao, multiplicacao, divisao, potenciacao e percentual.
            // Continuam utilizando a estrutura de pilhas de operadores e operandos.
            else -> {
                if (visor == "Erro") {
                    // Nao eh possivel empilhar uma operacao em cima de um erro.
                    return
                }

                // So substitui o operador do topo da pilha se realmente ha um operador
                // pendente ali. Se a pilha estiver vazia (por exemplo, logo apos usar
                // Pi, uma funcao unaria ou o "=", quando aguardandoOperando tambem fica
                // true), o operador precisa ser empilhado normalmente — caso contrario
                // ele seria descartado silenciosamente.
                if (aguardandoOperando && pilhaOperador.isNotEmpty()) {
                    pilhaOperador[pilhaOperador.lastIndex] = identificador.name
                    return
                }

                if (pilhaOperador.isEmpty()) {
                    pilhaOperador.add(identificador.name)
                    pilhaOperando.add(visor)
                } else {
                    //executar a operacao pendente antes de empilhar a nova
                    igualdade()
                    if (visor == "Erro") {
                        return
                    }
                    pilhaOperador.add(identificador.name)
                    pilhaOperando.add(visor)
                }

                aguardandoOperando = true
            }
        }
    }

    fun igualdade(){
        if (pilhaOperador.isEmpty() || pilhaOperando.isEmpty()){
            return
        }

        val operador = pilhaOperador.removeAt(pilhaOperador.lastIndex)
        val operandoStr = pilhaOperando.removeAt(pilhaOperando.lastIndex)
        val operando = visorParaDouble(operandoStr)
        val aux = visorParaDouble(visor)

        if (operando == null || aux == null) {
            visor = "Erro"
            aguardandoOperando = true
            return
        }

        visor = when (operador) {
            BotaoOperacao.SOMA.name -> doubleParaVisor(operando + aux)
            BotaoOperacao.SUBTRACAO.name -> doubleParaVisor(operando - aux)
            BotaoOperacao.MULTIPLICACAO.name -> doubleParaVisor(operando * aux)
            BotaoOperacao.DIVISAO.name -> {
                // Divisao por zero deve ser tratada, evitando o encerramento do app.
                if (aux == 0.0) "Erro" else doubleParaVisor(operando / aux)
            }
            BotaoOperacao.POTENCIACAO.name -> doubleParaVisor(operando.pow(aux))
            BotaoOperacao.PERCENTUAL.name -> {
                if (aux == 0.0) "Erro" else doubleParaVisor(operando % aux)
            }
            else -> visor
        }

        aguardandoOperando = true
    }

    /**
     * Aplica uma operacao unaria (seno, cosseno, tangente, raiz quadrada, inverso, fatorial)
     * diretamente sobre o valor atualmente exibido no visor.
     */
    private fun aplicarOperacaoUnaria(operacao: (Double) -> Double) {
        val valorAtual = visorParaDouble(visor)
        visor = if (valorAtual == null) {
            "Erro"
        } else {
            doubleParaVisor(operacao(valorAtual))
        }
        aguardandoOperando = true
    }

    /**
     * Calcula o fatorial de um numero. So eh valido para inteiros nao negativos;
     * qualquer outro valor (negativo, com casas decimais ou grande demais) retorna NaN,
     * que sera convertido em "Erro" na exibicao.
     */
    private fun fatorial(valor: Double): Double {
        if (valor < 0 || valor != floor(valor) || valor > 170) {
            return Double.NaN
        }
        var resultado = 1.0
        var i = 2
        val n = valor.toInt()
        while (i <= n) {
            resultado *= i
            i++
        }
        return resultado
    }

    private fun apagarUltimoDigito() {
        if (aguardandoOperando || visor == "Erro") {
            visor = "0"
            aguardandoOperando = false
            return
        }

        visor = if (visor.length <= 1) {
            "0"
        } else {
            visor.substring(0, visor.length - 1)
        }

        if (visor == "-" || visor.isEmpty()) {
            visor = "0"
        }
    }

    private fun trocarSinalDoVisor() {
        if (visor == "Erro" || visor == "0") {
            return
        }

        visor = if (visor.startsWith("-")) {
            visor.substring(1)
        } else {
            "-$visor"
        }
    }

    /**
     * Converte o texto do visor (que usa virgula como separador decimal) para Double.
     * Retorna null caso o texto nao seja um numero valido.
     */
    private fun visorParaDouble(texto: String): Double? {
        return texto.replace(",", ".").toDoubleOrNull()
    }

    /**
     * Converte um Double de volta para o formato de texto do visor, usando virgula
     * como separador decimal, removendo zeros desnecessarios e tratando resultados
     * invalidos (NaN/Infinito) como "Erro".
     */
    private fun doubleParaVisor(valor: Double): String {
        if (valor.isNaN() || valor.isInfinite()) {
            return "Erro"
        }

        // Arredonda para evitar ruido de ponto flutuante (ex: 0.30000000000000004)
        val fator = 1.0E8
        val arredondado = round(valor * fator) / fator

        val texto = if (arredondado == arredondado.toLong().toDouble() && abs(arredondado) < 1.0E15) {
            arredondado.toLong().toString()
        } else {
            arredondado.toString()
        }

        return texto.replace(".", ",")
    }

    enum class BotaoOperacao {
        // Digitos e virgula: usados diretamente para compor o numero exibido no visor.
        ZERO,
        UM,
        DOIS,
        TRES,
        QUATRO,
        CINCO,
        SEIS,
        SETE,
        OITO,
        NOVE,
        VIRGULA,

        // Operacoes binarias: utilizam a pilha de operadores/operandos.
        SOMA,
        SUBTRACAO,
        MULTIPLICACAO,
        DIVISAO,
        POTENCIACAO,
        PERCENTUAL,

        // Operacoes unarias: atuam diretamente sobre o valor exibido no visor.
        SENO,
        COSSENO,
        TANGENTE,
        FATORIAL,
        PI,
        INVERSO,
        RAIZQUADRADA,

        // Controle da calculadora.
        IGUALDADE,
        LIMPAR,
        APAGAR,
        TROCARSINAL
    }
}
