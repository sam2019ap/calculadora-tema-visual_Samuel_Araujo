# Calculadora com Pilhas de Operadores e Operandos — Tema Visual Personalizado

> Repositório da atividade de aplicação de um tema visual personalizado
> (Material Design 3) sobre a calculadora já desenvolvida anteriormente.

## Identificação

- **Aluno:** Samuel Araujo
- **RA:** 210025640
- **Curso:** Engenharia da Computação
- **Instituição:** UNISAL — Campus Campinas, Unidade São José

## Descrição do projeto

Aplicativo Android (Kotlin + Jetpack Compose) que implementa uma calculadora
utilizando a estrutura de **pilhas de operadores e operandos** apresentada em
aula para o tratamento das operações.

As operações binárias (soma, subtração, multiplicação, divisão, potenciação e
percentual) são resolvidas empilhando o operador e o operando pendentes e
processando-os quando o próximo operador ou o `=` é pressionado. As operações
unárias (seno, cosseno, tangente, raiz quadrada, inverso e fatorial) atuam
diretamente sobre o valor exibido no visor, sem uso da pilha.

## Operações implementadas

**Operações binárias (com pilha):**
- Soma (`+`)
- Subtração (`-`)
- Multiplicação (`*`)
- Divisão (`/`)
- Potenciação (`^`)
- Percentual (`%`, implementado como resto da divisão)

**Operações unárias (direto no visor):**
- Seno (`Sin`) — ângulo em graus
- Cosseno (`Cos`) — ângulo em graus
- Tangente (`Tan`) — ângulo em graus
- Raiz quadrada (`Sqrt`)
- Inverso (`Inv`) — `x⁻¹ = 1 / x`
- Fatorial (`!`)
- Constante Pi (`Pi`) — inserida como `3,14`

**Controles:**
- Igualdade (`=`)
- Limpar (`C`)
- Apagar último dígito (`<-`)
- Trocar sinal (`+/-`)

## Tratamento de situações inválidas

O aplicativo trata os seguintes casos sem encerrar inesperadamente,
exibindo `Erro` no visor:

- Divisão por zero
- Percentual por zero
- Raiz quadrada de número negativo
- Fatorial de número negativo, não inteiro ou grande demais
- Inverso de zero
- Qualquer resultado inválido (`NaN`/infinito)

Ao digitar um novo número após um erro, o visor é reiniciado normalmente.

## Estrutura do código

- Pacote: `br.com.unisal.samuelaraujo.calculadora`
- `MainActivity.kt`: contém a interface (Jetpack Compose) e toda a lógica de
  negócio da calculadora, incluindo:
  - `pilhaOperador` / `pilhaOperando`: pilhas usadas nas operações binárias
  - `numPress`: tratamento da entrada de dígitos
  - `opPress`: despacho das operações (binárias via pilha, unárias direto no visor)
  - `igualdade`: resolve o topo da pilha
  - `visorParaDouble` / `doubleParaVisor`: conversão entre o texto do visor
    (que usa vírgula como separador decimal) e `Double`

## Tema visual — "Retrô Handheld"

Tema personalizado (Material Design 3) inspirado nas telas de LCD
monocromáticas dos consoles portáteis clássicos (4 tons de verde) e nos
botões redondos coloridos desses aparelhos. Implementado em `ui/theme/`
(`Color.kt`, `Type.kt`, `Shape.kt`, `Theme.kt`), com duas variantes que usam
a mesma paleta de forma invertida:

- **Claro:** fundo verde-claro, texto verde-escuro (tela "não retroiluminada")
- **Escuro:** fundo verde-escuro, texto verde-claro (tela "retroiluminada")

**Cores** — só papéis semânticos (`MaterialTheme.colorScheme.*`), nada fixo
nos componentes:

- `primary` / `onPrimary` — botões de operador (+, −, ×, ÷, ^, Sin, Cos...)
- `secondaryContainer` / `onSecondaryContainer` — botões numéricos (neutros)
- `tertiary` / `onTertiary` — botão de igualdade (acento âmbar, destaque especial)
- `error` / `onError` — botão Limpar e mensagens de "Erro" (atenção)
- `surfaceVariant` / `outline` — painel do visor e botões utilitários (`<-`, `+/-`)

**Tipografia:** fonte monoespaçada em toda a interface, simulando um display
digital. A hierarquia vem da escala: `displayLarge` (visor, grande e em
negrito) > `titleLarge` (cabeçalho) > `labelLarge` (texto dos botões).

**Formas:** números e o botão de igualdade são circulares (`shapes.extraLarge`,
como os botões de ação dos consoles); operadores usam retângulo bem
arredondado (`shapes.medium`); Limpar e os botões utilitários usam cantos
mais retos (`shapes.small`), reforçando que são ações diferentes das
demais.

## Como executar

1. Abra a pasta do projeto no Android Studio
2. Aguarde a sincronização do Gradle
3. Execute em um emulador ou dispositivo físico
