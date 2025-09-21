import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

class Funcionario {                                                                                         // Classe base Funcionario
    // Atributos protegidos (acessíveis pelas subclasses)
    protected String nome;                                                                                  // Nome do funcionário
    protected BigDecimal salario;                                                                           // Salário do funcionário (usando BigDecimal para precisão)

    public Funcionario(String nome, BigDecimal salario) {                                                   // Construtor da classe Funcionario
        this.nome = nome;                                                                                   // Atribui o nome recebido ao atributo da classe

        if (salario.compareTo(BigDecimal.ZERO) <= 0) {                                                      // Valida se o salário é positivo
            throw new IllegalArgumentException("Salário deve ser positivo");                                // Lança exceção se salário for negativo ou zero
        }
        this.salario = salario;                                                                             // Atribui o salário validado
    }

    public String getNome() {                                                                               // Getter para o nome
        return nome;                                                                                        // Retorna o nome do funcionário
    }

    public BigDecimal getSalario() {                                                                        // Getter para o salário
        return salario;                                                                                     // Retorna o salário do funcionário
    }

    public BigDecimal calcularBonus() {                                                                     // Método para calcular bônus (será sobrescrito pelas subclasses)
        return BigDecimal.ZERO;                                                                             // Retorna zero como padrão (será sobrescrito)
    }
}

class Gerente extends Funcionario {                                                                         // Classe Gerente que herda de Funcionario
    public Gerente(String nome, BigDecimal salario) {                                                       // Construtor da classe Gerente
        super(nome, salario);                                                                               // Chama o construtor da classe pai (Funcionario)
    }

    @Override                                                                                               // Sobrescreve o método calcularBonus
    public BigDecimal calcularBonus() {
        // Calcula 20% do salário (bônus do gerente)
        return salario.multiply(new BigDecimal("0.20"))                                                 // Calcula o bônus do gerente, multiplica salário por 0.20 (20%) e arredonda para 2 casas decimais
                .setScale(2, RoundingMode.HALF_UP);
    }
}

class Desenvolvedor extends Funcionario {                                                                   // Classe Desenvolvedor que herda de Funcionario
    public Desenvolvedor(String nome, BigDecimal salario) {                                                 // Construtor da classe Desenvolvedor
        super(nome, salario);                                                                               // Chama o construtor da classe pai (Funcionario)
    }

    @Override                                                                                               // Sobrescreve o método calcularBonus
    public BigDecimal calcularBonus() {
        return salario.multiply(new BigDecimal("0.10"))                                                 // Calcula bônus do desenvolvedor, multiplica salário por 0.10 (10%) e arredonda para 2 casas decimais
                .setScale(2, RoundingMode.HALF_UP);
    }
}

public class HierarquiaFuncionarios {                                                                       // Classe principal do programa
    public static void main(String[] args) {
        try {
            List<Funcionario> funcionarios = new ArrayList<>();                                             // Cria uma lista de Funcionario (polimorfismo)

            funcionarios.add(new Gerente("João Silva", new BigDecimal("10000.00")));              // Adiciona diferentes tipos de funcionários à lista
            funcionarios.add(new Desenvolvedor("Maria Santos", new BigDecimal("5000.00")));
            funcionarios.add(new Gerente("Pedro Costa", new BigDecimal("12000.00")));
            funcionarios.add(new Desenvolvedor("Ana Oliveira", new BigDecimal("4500.00")));

            for (Funcionario func : funcionarios) {                                                         // Percorre a lista de funcionários usando enhanced for loop
                // Exibe informações de cada funcionário
                System.out.println("Nome: " + func.getNome());                                              // Mostra o nome
                System.out.println("Salário: R$ " + func.getSalario());                                     // Mostra o salário
                System.out.println("Bônus: R$ " + func.calcularBonus());                                    // Calcula e mostra o bônus
                System.out.println("---");                                                                  // Linha separadora
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());                                                  // Captura exceção caso algum salário seja inválido e exibe mensagem de erro
        }
    }
}