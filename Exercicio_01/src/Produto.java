public class Produto {                                                                              // Declaração da classe pública Produto
    private String nome;                                                                            // Declaração de atributo privado para armazenar o nome do produto
    private double preco;                                                                           // Declaração de atributo privado para armazenar o preço do produto
    private int quantidadeEmEstoque;                                                                // Declaração de atributo privado para armazenar a quantidade em estoque

    // Construtor
    public Produto(String nome, double preco, int quantidadeEmEstoque) {                            // Construtor da classe - Recebe três parâmetros: nome, preço e quantidade em estoque
                setNome(nome);                                                                      // Usa o metodo setter para definir o nome (que inclui validação)
        setPreco(preco);                                                                            // Usa o metodo setter para definir o preço (que inclui validação)
        setQuantidadeEmEstoque(quantidadeEmEstoque);                                                // Usa o metodo setter para definir a quantidade (que inclui validação)
    }

    // Getters e Setters com validações
    public String getNome() {                                                                       // Getter para o atributo nome, para retorna o valor do atributo
        return nome;                                                                                // Retorna o valor atual do atributo nome
    }

    public void setNome(String nome) {                                                              // Setter para o atributo nome, metodo que define/modifica o valor do atributo
        if (nome == null || nome.trim().isEmpty()) {                                                // Validação: verifica se o nome é nulo OU se após remover espaços está vazio
            throw new IllegalArgumentException("Nome não pode ser nulo ou vazio");                  // Se a validação falhar, lança uma exceção com mensagem explicativa
        }
        this.nome = nome.trim();                                                                    // Se a validação passar, atribui o valor ao atributo, usando trim() para remover espaços em branco no início e fim
    }

    public double getPreco() {                                                                      // Getter para o atributo preço - retorna o valor atual
        return preco;
    }

    public void setPreco(double preco) {                                                            // Setter para o atributo preço - define/modifica o valor
        if (preco < 0) {                                                                            // Validação: verifica se o preço é negativo
            throw new IllegalArgumentException("Preço não pode ser negativo");                      // Se for negativo, lança exceção
        }
        this.preco = preco;                                                                         // Se a validação passar, atribui o valor ao atributo
    }

    public int getQuantidadeEmEstoque() {                                                           // Getter para o atributo quantidadeEmEstoque - retorna o valor atual
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(int quantidadeEmEstoque) {                                   // Setter para o atributo quantidadeEmEstoque - define/modifica o valor
        if (quantidadeEmEstoque < 0) {                                                              // Validação: verifica se a quantidade é negativa
            throw new IllegalArgumentException("Quantidade em estoque não pode ser negativa");      // Se for negativa, lança exceção
        }
        this.quantidadeEmEstoque = quantidadeEmEstoque;                                             // Se a validação passar, atribui o valor ao atributo
    }

    // Sobrescrita do metodo toString() da classe Object
    @Override                                                                                       // Chama automaticamente quando tenta imprimir um objeto Produto
    public String toString() {
        return String.format("Produto: %s | Preço: R$ %.2f | Estoque: %d unidades",                 // Formata a string com os dados do produto
                nome, preco, quantidadeEmEstoque);
    }
}