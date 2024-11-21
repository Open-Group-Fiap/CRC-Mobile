# CRC - Condomínios Reduzindo Consumo

## Descrição

O CRC é um projeto que visa a redução do consumo de energia elétrica em condomínios residenciais.
Através de um sistema de monitoramento e controle, o CRC com que o condomínio com base no consumo de
cada morador, dê bonus para aqueles que economizam energia elétrica. Bonus esses que podem ser
resgatados em nossa loja virtual.

## Requisitos

Projeto foi desenvolvido utilizando jetpack compose.

Utilizamos o banco de dados Firebase Firestore para armazenar logs de erros durante a execução do
app.

Utilizamos SQLite para armazenar dados locais do usuário.

Utilizamos Retrofit para fazer requisições HTTP para a API do projeto.

Temos o CRUD de usuário, com cadastro, login, atualização dos dados (Tela essa que também lista
dados não alteraveis) e exclusão da conta

## Importante

**A API do projeto tem um cold boot demorado, apesar de algumas tecnicas do aplicativo para mitigar
esse problema, é recomendado que após iniciar o aplicativo o professor espere um momento antes de
tentar realizar interações com a API.**

**Na dashboard principal existe um botão de DEBUG para ganhar mais pontos, esse botão é apenas para
poder demonstrar a loja virtual, na versão final do aplicativo esses pontos seriam dados com base no
consumo com o passar dos meses, esse botão gera uma nova fatura aleátoria na conta do usuário e com
isso o bonifica com pontos.**

## Integrantes

Cauã Alencar Rojas Romero - RM98638

Jaci Teixeira Santos - RM99627

Leonardo dos Santos Guerra - RM99738

Maria Eduarda Ferreira da Mata - RM99004

