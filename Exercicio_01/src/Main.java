public class Main {                                                                                     // Declaração da classe principal Main que contém o metodo main
    public static void main(String[] args) {                                                            // Metodo principal - ponto de entrada do programa Java
        try {                                                                                           // Bloco try para capturar exceções que possam ocorrer durante a execução
            // Criando instâncias válidas
            System.out.println("=== CRIANDO PRODUTOS VÁLIDOS ===");                                     // Imprime um cabeçalho indicando a seção de criação de produtos válidos
            Produto produto1 = new Produto("Notebook", 2500.00, 10);      // Cria uma nova instância de Produto com valores válidos ("Notebook" como nome, 2500.00 como preço, 10 como quantidade)
            Produto produto2 = new Produto("Mouse", 50.00, 100);          // Cria uma nova instância de Produto com valores válidos ("Mouse" como nome, 50.00 como preço, 100 como quantidade)

            System.out.println(produto1);                                                               // Imprime o primeiro produto (chama automaticamente o metodo toString())
            System.out.println(produto2);                                                               // Imprime o segundo produto (chama automaticamente o metodo toString())

            // Alterando valores válidos
            System.out.println("\n=== ALTERANDO VALORES VÁLIDOS ===");                                  // Imprime um cabeçalho indicando a seção de alteração de valores
            produto1.setPreco(2300.00);                                                                 // Altera o preço do produto1 para um novo valor válido (2300.00)
            produto1.setQuantidadeEmEstoque(8);                                                         // Altera a quantidade em estoque do produto1 para um novo valor válido (8)
            produto2.setNome("Mouse Gamer");                                                            // Altera o nome do produto2 para um novo valor válido ("Mouse Gamer")

            System.out.println("Produto 1 atualizado: " + produto1);                                    // Imprime o produto1 atualizado com os novos valores
            System.out.println("Produto 2 atualizado: " + produto2);                                    // Imprime o produto2 atualizado com o novo nome

            // Tentando atribuições inválidas
            System.out.println("\n=== TESTANDO VALIDAÇÕES ===");                                        // Imprime um cabeçalho indicando a seção de testes de validação

            try {                                                                                       // Bloco try-catch para testar a validação de preço negativo
                produto1.setPreco(-100.00);                                                             // Tenta definir um preço negativo no produto1 (deve lançar exceção)
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao setar preço negativo: " + e.getMessage());                  // Captura a exceção e imprime uma mensagem de erro com detalhes
            }

            try {                                                                                       // Bloco try-catch para testar a validação de quantidade negativa
                produto2.setQuantidadeEmEstoque(-5);                                                    // Tenta definir uma quantidade negativa no produto2 (deve lançar exceção)
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao setar estoque negativo: " + e.getMessage());                // Captura a exceção e imprime uma mensagem de erro com detalhes
            }

            try {                                                                                       // Bloco try-catch para testar a validação de nome vazio
                produto1.setNome("");                                                                   // Tenta definir um nome vazio no produto1 (deve lançar exceção)
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao setar nome vazio: " + e.getMessage());                      // Captura a exceção e imprime uma mensagem de erro com detalhes
            }

            try {                                                                                       // Bloco try-catch para testar a validação de nome nulo
                produto2.setNome(null);                                                                 // Tenta definir um nome nulo no produto2 (deve lançar exceção)
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao setar nome nulo: " + e.getMessage());                       // Captura a exceção e imprime uma mensagem de erro com detalhes
            }

            // Tentando criar produto inválido no construtor
            try {                                                                                       // Bloco try-catch para testar a criação de um produto com valores inválidos
                Produto produtoInvalido = new Produto("", -10, -5);       // Tenta criar um produto com nome vazio, preço negativo e quantidade negativa
            } catch (IllegalArgumentException e) {
                System.out.println("Erro ao criar produto inválido: " + e.getMessage());                // Captura a exceção e imprime uma mensagem de erro com detalhes
            }

        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());                                   // Captura qualquer exceção não prevista que possa ocorrer no bloco try principal
        }
    }
}