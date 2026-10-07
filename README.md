# Avaliador de Senhas em Java (ValidaSenhaForte)

Atividade de **Algoritmos e Programação** (Prof. Alexandre Montanha).
Aplicação de console que lê uma senha fictícia, avalia por regras em ordem fixa e devolve **apenas a mensagem da primeira falha**. Repete a solicitação até a senha ser aprovada.

> Use somente senhas fictícias. A aprovação significa apenas que a entrada passou nas regras deste exercício; não garante segurança em um sistema real.

---

## Como executar

Requisito: JDK instalado (testado com JDK 25), sem bibliotecas externas.

```
javac ValidaSenhaForte.java
java ValidaSenhaForte
```

Digite uma senha quando o programa pedir. Se for reprovada, a dica é exibida e uma nova senha é solicitada. Ao ser aprovada, o programa exibe a mensagem de sucesso e encerra.

---

## Regras (nesta ordem)

| Ordem | Regra | Reprova quando... | Mensagem |
|---|---|---|---|
| 1 | Comprimento | tem menos de 8 caracteres (8 passa) | `DICA: A senha deve ter no mínimo 8 caracteres.` |
| 2 | Número | não tem nenhum dígito | `DICA: Adicione pelo menos um número à sua senha.` |
| 3 | Senha óbvia | é exatamente igual a `12345678`, `senha123` ou `admin123` | `ALERTA: Esta senha é muito comum ou óbvia.` |
| 4 | Maiúscula | não tem nenhuma letra maiúscula | `DICA: Adicione pelo menos uma letra maiúscula à sua senha.` |
| - | Aprovação | passou em todas | `SUCESSO: Sua senha passou nos critérios básicos!` |

---

## Decisões do código

- **`avaliarSenha(String senha)`** recebe a senha (parâmetro) e devolve uma `String` com a mensagem (retorno). Só avalia: não lê teclado nem imprime.
- **`main`** cuida da leitura (`nextLine()`, linha inteira, sem alterar a entrada), da exibição e da repetição.
- **Ordem das regras:** cada regra tem um `return` dentro de um `if`; a primeira que falha encerra o método, por isso só uma mensagem é devolvida.
- **`equals()`** compara o conteúdo das Strings (e diferencia maiúsculas de minúsculas). O `==` compararia a referência do objeto. Por isso `Senha123` não é igual a `senha123`.
- **Laços:**
  - `for` com `charAt(i)` e `Character.isDigit`: procura pelo menos um dígito.
  - `for` no vetor `senhasObvias` com `equals()`: compara a senha com cada item da lista.
  - `for` com `charAt(i)` e `Character.isUpperCase`: procura pelo menos uma maiúscula.
  - `do-while` no `main`: repete as tentativas (pede a senha ao menos uma vez) até a aprovação.
- A aprovação é detectada com `mensagem.startsWith("SUCESSO")`, já que o contrato do método devolve apenas uma `String`.
- Sem regex, sem streams, sem salvar ou repetir a senha nas mensagens.

---

## Registro de testes (versão final, 4 regras)

| ID | Entrada | Resultado esperado | Resultado obtido | Passou? |
|---|---|---|---|---|
| T01 | (vazia) | Dica de tamanho mínimo | Dica de tamanho mínimo | ✅ |
| T02 | `batata` | Dica de tamanho mínimo | Dica de tamanho mínimo | ✅ |
| T03 | `Abcdef1` | Dica de tamanho mínimo (7 caracteres) | Dica de tamanho mínimo | ✅ |
| T04 | `Abcdefgh` | Dica de número | Dica de número | ✅ |
| T05 | `abcdefgh` | Dica de número (antes da maiúscula) | Dica de número | ✅ |
| T06 | `12345678` | Alerta de senha óbvia | Alerta de senha óbvia | ✅ |
| T07 | `senha123` | Alerta de senha óbvia | Alerta de senha óbvia | ✅ |
| T08 | `admin123` | Alerta de senha óbvia | Alerta de senha óbvia | ✅ |
| T09 | `abcdefgh1` | Dica de maiúscula | Dica de maiúscula | ✅ |
| T10 | `Abcdefg1` | Sucesso (exatamente 8 caracteres) | Sucesso | ✅ |
| T11 | `Senha123` | Sucesso (diferente de `senha123`) | Sucesso | ✅ |
| T12 | `Cachorr0_Verd3!` | Sucesso | Sucesso | ✅ |

### Fluxo completo

Sequência: `batata` → `senha123` → `Abcdefg1`.
Esperado: orientar as duas primeiras tentativas, pedir nova entrada após cada uma e encerrar só após a aprovação.
Obtido: orientou as duas primeiras tentativas, pediu nova entrada após cada uma e encerrou somente após a aprovação. ✅

### Pendências

Nenhuma. Todos os testes (T01 a T12) e o fluxo completo passaram.

---

## Reflexão

Passar nas regras deste exercício não garante que uma senha seja segura, porque elas verificam apenas características simples: tamanho, presença de número, presença de maiúscula e uma lista pequena de senhas óbvias. Uma senha como `Abcdefg1` cumpre todas as regras (teste T10), mas é previsível e facilmente adivinhada por programas que testam padrões comuns. A lista de senhas óbvias tem só três itens, enquanto na prática existem milhões de senhas vazadas e variações como `Senha@123`. Além disso, a segurança real depende de fatores que o programa não avalia, como reutilizar a mesma senha em vários sites, o armazenamento correto no servidor e a autenticação em dois fatores. O desenvolvedor tem a responsabilidade de não passar uma falsa sensação de segurança: a mensagem de sucesso deve deixar claro que a senha passou apenas em critérios básicos. Também deve orientar com dicas claras e honestas, sem repetir ou armazenar a senha, e manter as regras atualizadas conforme surgem novas ameaças. Orientar bem o usuário faz parte da proteção, pois uma regra mal explicada leva a senhas piores.
