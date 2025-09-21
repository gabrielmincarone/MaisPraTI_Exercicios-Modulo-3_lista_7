public class Main {                                                                         // Classe principal que contém o método main para execução
    public static void main(String[] args) {
        try {
            Produto produto = new Produto("Notebook", 2500.00);                 // Criação de um novo produto com nome "Notebook" e preço R$ 2500.00

            System.out.println("Preço original: R$ " + produto.getPreco());                 // Exibe o preço original do produto

            produto.aplicarDesconto(20.0);                                       // Aplica um desconto válido de 20%

            System.out.println("Preço com 20% de desconto: R$ " + produto.getPreco());      // Exibe o preço após o desconto aplicado

            produto.aplicarDesconto(60.0);                                       // Tenta aplicar um desconto inválido (60%) - deve lançar exceção

        } catch (DescontoInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());                                  // Captura e exibe a mensagem da exceção personalizada
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());                                  // Captura e exibe a mensagem da exceção padrão do Java
        }

        try {                                                                               // Teste adicional com outro produto
            Produto produto2 = new Produto("Mouse", 100.00);                    // Criação de outro produto para teste
            System.out.println("\nPreço do mouse: R$ " + produto2.getPreco());

            produto2.aplicarDesconto(50.0);                                      // Aplica desconto máximo permitido (50%)
            System.out.println("Preço com 50% de desconto: R$ " + produto2.getPreco());

            produto2.aplicarDesconto(-10.0);                                     // Tenta aplicar desconto negativo - deve lançar exceção

        } catch (DescontoInvalidoException | IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}

class Produto {                                                                             // Classe que representa um produto com capacidade de aplicar descontos
    private String nome;                                                                    // Atributo privado para armazenar o nome do produto (encapsulado)

    private double preco;                                                                   // Atributo privado para armazenar o preço do produto (encapsulado)

    public Produto(String nome, double preco) {                                             // Construtor da classe Produto que inicializa nome e preço
        this.nome = nome;                                                                   // Atribui o nome recebido ao atributo da classe
        this.preco = preco;                                                                 // Atribui o preço recebido ao atributo da classe
    }

    public String getNome() {                                                               // Metodo getter para obter o nome do produto
        return nome;                                                                        // Retorna o valor do atributo nome
    }

    public void setNome(String nome) {                                                      // Metodo setter para definir o nome do produto
        this.nome = nome;                                                                   // Atribui novo valor ao atributo nome
    }

    public double getPreco() {                                                              // Método getter para obter o preço do produto
        return preco;                                                                       // Retorna o valor do atributo preco
    }

    // Método setter para definir o preço do produto
    public void setPreco(double preco) {
        this.preco = preco; // Atribui novo valor ao atributo preco
    }

    // Método para aplicar desconto com validação da porcentagem
    public void aplicarDesconto(double porcentagem) throws DescontoInvalidoException {
        // Verifica se a porcentagem é negativa
        if (porcentagem < 0) {
            // Lança exceção padrão do Java para valores negativos
            throw new IllegalArgumentException("Porcentagem de desconto não pode ser negativa");
        }

        // Verifica se a porcentagem excede o limite máximo de 50%
        if (porcentagem > 50) {
            // Lança exceção personalizada para valores acima do permitido
            throw new DescontoInvalidoException("Desconto máximo permitido é 50%");
        }

        // Calcula o valor do desconto em reais
        double desconto = preco * (porcentagem / 100);

        // Aplica o desconto subtraindo do preço original
        preco -= desconto;

        // Exibe mensagem informativa sobre o desconto aplicado
        System.out.println("Desconto de " + porcentagem + "% aplicado com sucesso!");
    }
}

// Classe de exceção personalizada para descontos inválidos
class DescontoInvalidoException extends Exception {
    // Construtor que recebe uma mensagem de erro
    public DescontoInvalidoException(String mensagem) {
        // Chama o construtor da classe pai (Exception) passando a mensagem
        super(mensagem);
    }
}