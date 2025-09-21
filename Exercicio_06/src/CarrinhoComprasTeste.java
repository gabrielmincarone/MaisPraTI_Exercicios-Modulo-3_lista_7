import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

enum Moeda {                                                                                                            // Enumeração que representa as moedas disponíveis
    BRL("Real Brasileiro"),
    USD("Dólar Americano"),
    EUR("Euro");

    private final String descricao;

    Moeda(String descricao) {                                                                                           // Construtor do enum Moeda
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

final class Dinheiro {                                                                                                  // Classe imutável que representa Dinheiro (Value Object)
    private final BigDecimal valor;
    private final Moeda moeda;

    private Dinheiro(BigDecimal valor, Moeda moeda) {                                                                   // Construtor privado para garantir imutabilidade
        this.valor = Objects.requireNonNull(valor, "Valor não pode ser nulo");                                  // Validação para garantir que valor não seja nulo
        this.moeda = Objects.requireNonNull(moeda, "Moeda não pode ser nula");                                  // Validação para garantir que moeda não seja nula
        if (valor.compareTo(BigDecimal.ZERO) < 0) {                                                                     // Validação para garantir que valor não seja negativo
            throw new IllegalArgumentException("Valor não pode ser negativo");
        }
    }

    public static Dinheiro of(BigDecimal valor, Moeda moeda) {                                                          // Método factory para criar instâncias de Dinheiro
        return new Dinheiro(valor, moeda);
    }

    public static Dinheiro of(String valor, Moeda moeda) {                                                              // Método factory conveniente para criar Dinheiro com String
        return new Dinheiro(new BigDecimal(valor), moeda);
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Moeda getMoeda() {
        return moeda;
    }

    public Dinheiro somar(Dinheiro outro) {                                                                             // Método para somar dois valores de Dinheiro (mesma moeda)
        if (!this.moeda.equals(outro.moeda)) {                                                                          // Verifica se as moedas são compatíveis
            throw new IllegalArgumentException("Moedas diferentes não podem ser somadas");
        }
        return new Dinheiro(this.valor.add(outro.valor), this.moeda);
    }

    public Dinheiro multiplicar(int quantidade) {                                                                       // Método para multiplicar o valor por uma quantidade
        if (quantidade <= 0) {                                                                                          // Validação para quantidade positiva
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        return new Dinheiro(this.valor.multiply(new BigDecimal(quantidade)), this.moeda);
    }

    public Dinheiro aplicarDesconto(BigDecimal percentualDesconto) {                                                    // Método para aplicar desconto com limite de 30%
        Objects.requireNonNull(percentualDesconto, "Percentual de desconto não pode ser nulo");                 // Validação para percentual não nulo
        if (percentualDesconto.compareTo(BigDecimal.ZERO) < 0) {                                                        // Validação para percentual não negativo
            throw new IllegalArgumentException("Percentual de desconto não pode ser negativo");
        }
        if (percentualDesconto.compareTo(new BigDecimal("30")) > 0) {                                               // Limite de 30% para desconto
            throw new IllegalArgumentException("Desconto não pode exceder 30%");
        }

        BigDecimal valorDesconto = this.valor.multiply(percentualDesconto)                                              // Calcula o valor do desconto
                .divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);
        BigDecimal novoValor = this.valor.subtract(valorDesconto)                                                       // Aplica o desconto com arredondamento bancário
                .setScale(2, RoundingMode.HALF_EVEN);
        return new Dinheiro(novoValor, this.moeda);
    }

    @Override                                                                                                           // Implementação do equals para comparar dois objetos Dinheiro
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dinheiro dinheiro = (Dinheiro) o;
        return valor.compareTo(dinheiro.valor) == 0 && moeda == dinheiro.moeda;                                         // Compara valores e moedas usando compareTo para BigDecimal
    }

    @Override                                                                                                           // Implementação do hashCode consistente com equals
    public int hashCode() {
        return Objects.hash(valor, moeda);
    }

    @Override
    public String toString() {
        return String.format("%s %s", moeda, valor);
    }
}

class Produto {                                                                                                         // Classe que representa um Produto
    private final String id;
    private final String nome;
    private final Dinheiro preco;

    public Produto(String id, String nome, Dinheiro preco) {                                                            // Construtor do Produto
        // Validações dos parâmetros
        this.id = Objects.requireNonNull(id, "ID não pode ser nulo");
        this.nome = Objects.requireNonNull(nome, "Nome não pode ser nulo");
        this.preco = Objects.requireNonNull(preco, "Preço não pode ser nulo");
    }

    // Getters
    public String getId() { return id; }
    public String getNome() { return nome; }
    public Dinheiro getPreco() { return preco; }

    @Override
    public String toString() {
        return String.format("Produto{id='%s', nome='%s', preco=%s}", id, nome, preco);
    }
}

class ItemCarrinho {                                                                                                    // Classe que representa um Item do Carrinho
    private final Produto produto;
    private final int quantidade;

    public ItemCarrinho(Produto produto, int quantidade) {                                                              // Construtor do ItemCarrinho
        this.produto = Objects.requireNonNull(produto, "Produto não pode ser nulo");                            // Validações dos parâmetros
        if (quantidade <= 0) {                                                                                          // Validação para quantidade positiva
            throw new IllegalArgumentException("Quantidade deve ser maior que zero");
        }
        this.quantidade = quantidade;
    }

    // Getters
    public Produto getProduto() { return produto; }
    public int getQuantidade() { return quantidade; }

    public Dinheiro getSubtotal() {                                                                                     // Método para calcular o subtotal do item
        return produto.getPreco().multiplicar(quantidade);
    }

    @Override
    public String toString() {
        return String.format("ItemCarrinho{produto=%s, quantidade=%d}", produto, quantidade);
    }
}

final class Carrinho {                                                                                                  // Classe imutável que representa o Carrinho de Compras
    private final List<ItemCarrinho> itens;
    private final BigDecimal percentualDesconto;

    private Carrinho(List<ItemCarrinho> itens, BigDecimal percentualDesconto) {                                         // Construtor privado para garantir imutabilidade
        this.itens = Collections.unmodifiableList(new ArrayList<>(itens));                                              // Cria uma cópia defensiva da lista para garantir imutabilidade
        this.percentualDesconto = Objects.requireNonNull(percentualDesconto);

        if (percentualDesconto.compareTo(BigDecimal.ZERO) < 0) {                                                        // Validação para desconto não negativo
            throw new IllegalArgumentException("Percentual de desconto não pode ser negativo");
        }
        if (percentualDesconto.compareTo(new BigDecimal("30")) > 0) {                                               // Limite de 30% para desconto
            throw new IllegalArgumentException("Desconto não pode exceder 30%");
        }
    }

    public static Carrinho criar() {                                                                                    // Método factory para criar carrinho vazio
        return new Carrinho(new ArrayList<>(), BigDecimal.ZERO);
    }

    public Carrinho adicionarItem(ItemCarrinho novoItem) {                                                              // Método para adicionar item - retorna novo carrinho (imutabilidade)
        List<ItemCarrinho> novosItens = new ArrayList<>(this.itens);                                                    // Cria nova lista com os itens existentes
        novosItens.add(novoItem);                                                                                       // Adiciona o novo item
        return new Carrinho(novosItens, this.percentualDesconto);                                                       // Retorna novo carrinho com a lista atualizada
    }

    public Carrinho removerItem(String produtoId) {                                                                     // Método para remover item - retorna novo carrinho (imutabilidade)
        List<ItemCarrinho> novosItens = new ArrayList<>();                                                              // Cria nova lista sem o item a ser removido
        for (ItemCarrinho item : this.itens) {
            if (!item.getProduto().getId().equals(produtoId)) {
                novosItens.add(item);
            }
        }
        return new Carrinho(novosItens, this.percentualDesconto);                                                       // Retorna novo carrinho com a lista atualizada
    }

    public Carrinho aplicarCupom(BigDecimal percentualDesconto) {                                                       // Método para aplicar cupom de desconto - retorna novo carrinho
        return new Carrinho(this.itens, percentualDesconto);                                                            // Retorna novo carrinho com o desconto aplicado
    }

    public Dinheiro calcularTotal() {                                                                                   // Método para calcular o total do carrinho
        if (itens.isEmpty()) {
            return Dinheiro.of(BigDecimal.ZERO, Moeda.BRL); // Moeda padrão
        }

        Dinheiro total = itens.get(0).getSubtotal();                                                                    // Soma todos os subtotais dos itens
        for (int i = 1; i < itens.size(); i++) {
            total = total.somar(itens.get(i).getSubtotal());
        }

        if (percentualDesconto.compareTo(BigDecimal.ZERO) > 0) {                                                        // Aplica desconto se houver
            total = total.aplicarDesconto(percentualDesconto);
        }

        return total;
    }

    // Getters
    public List<ItemCarrinho> getItens() { return itens; }
    public BigDecimal getPercentualDesconto() { return percentualDesconto; }

    public boolean estaVazio() {
        return itens.isEmpty();
    }

    @Override
    public String toString() {
        return String.format("Carrinho{itens=%s, desconto=%s%%, total=%s}",
                itens, percentualDesconto, calcularTotal());
    }
}

public class CarrinhoComprasTeste {                                                                                     // Classe de teste para demonstrar o fluxo completo
    public static void main(String[] args) {
        System.out.println("=== TESTE DO CARRINHO DE COMPRAS ===\n");

        // 1. Criando produtos
        System.out.println("1. Criando produtos...");
        Produto notebook = new Produto("001", "Notebook Dell",
                Dinheiro.of("2500.00", Moeda.BRL));
        Produto mouse = new Produto("002", "Mouse Sem Fio",
                Dinheiro.of("89.90", Moeda.BRL));
        Produto teclado = new Produto("003", "Teclado Mecânico",
                Dinheiro.of("299.90", Moeda.BRL));

        System.out.println("Produtos criados: " + notebook + ", " + mouse + ", " + teclado);

        // 2. Criando carrinho vazio
        System.out.println("\n2. Criando carrinho vazio...");
        Carrinho carrinho = Carrinho.criar();
        System.out.println("Carrinho inicial: " + carrinho);
        System.out.println("Está vazio? " + carrinho.estaVazio());

        // 3. Adicionando itens ao carrinho (operações imutáveis)
        System.out.println("\n3. Adicionando itens ao carrinho...");
        Carrinho carrinhoComItens = carrinho
                .adicionarItem(new ItemCarrinho(notebook, 1))
                .adicionarItem(new ItemCarrinho(mouse, 2))
                .adicionarItem(new ItemCarrinho(teclado, 1));

        System.out.println("Carrinho com itens: " + carrinhoComItens);
        System.out.println("Carrinho original permanece vazio: " + carrinho.estaVazio());

        // 4. Aplicando cupom de desconto
        System.out.println("\n4. Aplicando cupom de 15% de desconto...");
        Carrinho carrinhoComDesconto = carrinhoComItens.aplicarCupom(new BigDecimal("15"));
        System.out.println("Carrinho com desconto: " + carrinhoComDesconto);

        // 5. Tentando aplicar desconto maior que 30% (deve falhar)
        System.out.println("\n5. Tentando aplicar desconto de 40% (deve falhar)...");
        try {
            carrinhoComItens.aplicarCupom(new BigDecimal("40"));
        } catch (IllegalArgumentException e) {
            System.out.println("Erro esperado: " + e.getMessage());
        }

        // 6. Removendo item do carrinho
        System.out.println("\n6. Removendo mouse do carrinho...");
        Carrinho carrinhoSemMouse = carrinhoComItens.removerItem("002");
        System.out.println("Carrinho sem mouse: " + carrinhoSemMouse);

        // 7. Testando igualdade de objetos Dinheiro
        System.out.println("\n7. Testando igualdade de valores monetários...");
        Dinheiro valor1 = Dinheiro.of("100.00", Moeda.BRL);
        Dinheiro valor2 = Dinheiro.of("100.00", Moeda.BRL);
        Dinheiro valor3 = Dinheiro.of("200.00", Moeda.BRL);

        System.out.println(valor1 + " equals " + valor2 + ": " + valor1.equals(valor2));
        System.out.println(valor1 + " equals " + valor3 + ": " + valor1.equals(valor3));
        System.out.println("HashCode " + valor1 + ": " + valor1.hashCode());
        System.out.println("HashCode " + valor2 + ": " + valor2.hashCode());

        // 8. Demonstrando operações matemáticas com Dinheiro
        System.out.println("\n8. Operações com Dinheiro...");
        Dinheiro soma = valor1.somar(valor3);
        System.out.println(valor1 + " + " + valor3 + " = " + soma);

        Dinheiro multiplicacao = valor1.multiplicar(3);
        System.out.println(valor1 + " * 3 = " + multiplicacao);

        Dinheiro comDesconto = valor3.aplicarDesconto(new BigDecimal("25"));
        System.out.println(valor3 + " com 25% de desconto = " + comDesconto);

        System.out.println("\n=== TESTE CONCLUÍDO ===");
    }
}