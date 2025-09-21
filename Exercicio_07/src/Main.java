interface Identificavel<ID> {                                                                                           // Interface que define que uma entidade possui um identificador
    ID getId();                                                                                                         // Método para obter o ID da entidade
}

class EntidadeNaoEncontradaException extends RuntimeException {                                                         // Exceção personalizada para quando uma entidade não é encontrada
    public EntidadeNaoEncontradaException(String mensagem) {
        super(mensagem);                                                                                                // Chama o construtor da classe pai RuntimeException
    }
}


interface IRepository<T extends Identificavel<ID>, ID> {                                                                // Interface genérica para o repositório | T extends Identificavel<ID> garante que T implementa Identificavel | ID é o tipo do identificador (String, Integer, etc)

    T salvar(T entidade);                                                                                               // Salva uma entidade no repositório

    java.util.Optional<T> buscarPorId(ID id);                                                                           // Busca uma entidade por ID, retornando Optional (pode estar vazio)

    java.util.List<T> listarTodos();                                                                                    // Retorna todas as entidades como uma lista imutável

    void remover(ID id);                                                                                                // Remove uma entidade pelo ID
}

class InMemoryRepository<T extends Identificavel<ID>, ID> implements IRepository<T, ID> {                               // Implementação em memória do repositório usando Map

    private final java.util.Map<ID, T> repositorio = new java.util.HashMap<>();                                         // Map que armazena as entidades, usando ID como chave

    @Override                                                                                                           // Salva uma entidade no repositório
    public T salvar(T entidade) {
        ID id = entidade.getId();                                                                                       // Obtém o ID da entidade
        repositorio.put(id, entidade);                                                                                  // Armazena a entidade no Map usando o ID como chave
        return entidade;                                                                                                // Retorna a entidade salva
    }

    @Override                                                                                                           // Busca uma entidade por ID
    public java.util.Optional<T> buscarPorId(ID id) {
        return java.util.Optional.ofNullable(repositorio.get(id));                                                      // Busca a entidade no Map e encapsula em Optional, se não encontrar, retorna Optional.empty()
    }

    @Override                                                                                                           // Retorna todas as entidades como lista imutável
    public java.util.List<T> listarTodos() {
        java.util.List<T> lista = new java.util.ArrayList<>(repositorio.values());                                      // Cria uma nova lista com todos os valores do Map
        return java.util.Collections.unmodifiableList(lista);                                                           // Retorna uma cópia imutável da lista usando Collections.unmodifiableList
    }

    @Override                                                                                                           // Remove uma entidade por ID
    public void remover(ID id) {
        if (!repositorio.containsKey(id)) {                                                                             // Verifica se a entidade existe antes de tentar remover
            throw new EntidadeNaoEncontradaException("Entidade com ID " + id + " não encontrada");                      // Lança exceção se a entidade não for encontrada
        }
        repositorio.remove(id);                                                                                         // Remove a entidade do Map
    }
}

class Produto implements Identificavel<String> {                                                                        // Exemplo de entidade Produto
    private String id;                                                                                                  // ID do produto
    private String nome;                                                                                                // Nome do produto
    private double preco;                                                                                               // Preço do produto

    public Produto(String id, String nome, double preco) {                                                              // Construtor
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    @Override                                                                                                           // Implementação do método da interface Identificavel
    public String getId() {
        return id;
    }

    // Getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    @Override
    public String toString() {
        return "Produto{id='" + id + "', nome='" + nome + "', preco=" + preco + "}";
    }
}

class Funcionario implements Identificavel<Integer> {                                                                   // Exemplo de entidade Funcionario
    private Integer id;         // ID do funcionário
    private String nome;        // Nome do funcionário
    private String departamento; // Departamento do funcionário

    public Funcionario(Integer id, String nome, String departamento) {                                                  // Construtor
        this.id = id;
        this.nome = nome;
        this.departamento = departamento;
    }

    @Override                                                                                                           // Implementação do método da interface Identificavel
    public Integer getId() {
        return id;
    }

    // Getters e setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    @Override
    public String toString() {
        return "Funcionario{id=" + id + ", nome='" + nome + "', departamento='" + departamento + "'}";
    }
}

public class Main {                                                                                                     // Classe principal para demonstrar o uso do repositório
    public static void main(String[] args) {
        System.out.println("=== Testando Repositório de Produtos ===");

        IRepository<Produto, String> repoProdutos = new InMemoryRepository<>();                                         // Cria repositório para Produtos com ID String

        // Cria alguns produtos
        Produto p1 = new Produto("P001", "Notebook", 2500.00);
        Produto p2 = new Produto("P002", "Mouse", 50.00);
        Produto p3 = new Produto("P003", "Teclado", 120.00);

        // Salva os produtos no repositório
        repoProdutos.salvar(p1);
        repoProdutos.salvar(p2);
        repoProdutos.salvar(p3);

        // Lista todos os produtos
        System.out.println("Todos os produtos:");
        repoProdutos.listarTodos().forEach(System.out::println);

        // Busca um produto específico
        System.out.println("\nBuscando produto P002:");
        repoProdutos.buscarPorId("P002").ifPresentOrElse(
                produto -> System.out.println("Encontrado: " + produto),
                () -> System.out.println("Produto não encontrado")
        );

        // Remove um produto
        System.out.println("\nRemovendo produto P002...");
        repoProdutos.remover("P002");

        // Tenta buscar o produto removido
        System.out.println("Buscando produto P002 após remoção:");
        repoProdutos.buscarPorId("P002").ifPresentOrElse(
                produto -> System.out.println("Encontrado: " + produto),
                () -> System.out.println("Produto não encontrado")
        );

        System.out.println("\n=== Testando Repositório de Funcionários ===");

        IRepository<Funcionario, Integer> repoFuncionarios = new InMemoryRepository<>();                                // Cria repositório para Funcionarios com ID Integer

        // Cria alguns funcionários
        Funcionario f1 = new Funcionario(1, "João", "TI");
        Funcionario f2 = new Funcionario(2, "Maria", "RH");
        Funcionario f3 = new Funcionario(3, "Pedro", "Vendas");

        // Salva os funcionários no repositório
        repoFuncionarios.salvar(f1);
        repoFuncionarios.salvar(f2);
        repoFuncionarios.salvar(f3);

        // Lista todos os funcionários
        System.out.println("Todos os funcionários:");
        repoFuncionarios.listarTodos().forEach(System.out::println);

        System.out.println("\nTentando remover funcionário com ID 99...");                                              // Testa a exceção ao tentar remover ID inexistente
        try {
            repoFuncionarios.remover(99);
        } catch (EntidadeNaoEncontradaException e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }

        System.out.println("\nTestando imutabilidade da lista...");                                                     // Testa que a lista retornada é imutável
        java.util.List<Funcionario> lista = repoFuncionarios.listarTodos();
        try {
            lista.add(new Funcionario(4, "Teste", "Teste")); // Deve lançar exceção
        } catch (UnsupportedOperationException e) {
            System.out.println("Exceção capturada: Não é possível modificar a lista (imutável)");
        }
    }
}