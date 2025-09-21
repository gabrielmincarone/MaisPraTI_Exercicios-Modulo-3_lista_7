import java.math.BigDecimal;
import java.util.function.Function;

interface CalculadoraFrete {                                                                                            // Interface que define a estratégia de cálculo de frete
    BigDecimal calcular(Pedido pedido);
}

class Pedido {                                                                                                          // Classe que representa um pedido
    private String cep;                                                                                                 // CEP do destinatário
    private BigDecimal valor;                                                                                           // Valor total do pedido
    private CalculadoraFrete estrategiaFrete;                                                                           // Estratégia de cálculo de frete atual

    public Pedido(String cep, BigDecimal valor, CalculadoraFrete estrategiaFrete) {                                     // Construtor do pedido
        this.cep = cep;
        this.valor = valor;
        this.estrategiaFrete = estrategiaFrete;
    }

    public void setEstrategiaFrete(CalculadoraFrete estrategiaFrete) {                                                  // Método para definir/alterar a estratégia de frete em tempo de execução
        this.estrategiaFrete = estrategiaFrete;
    }

    public BigDecimal calcularFrete() {                                                                                 // Método para calcular o frete usando a estratégia atual
        return estrategiaFrete.calcular(this);
    }

    // Getters para acessar os atributos privados
    public String getCep() { return cep; }
    public BigDecimal getValor() { return valor; }
}

// Estratégia concreta para frete Sedex
class Sedex implements CalculadoraFrete {
    @Override
    public BigDecimal calcular(Pedido pedido) {
        if (!pedido.getCep().matches("\\d{8}")) {                                                                 // Valida se o CEP é válido (8 dígitos numéricos)
            throw new IllegalArgumentException("CEP inválido para Sedex: " + pedido.getCep());
        }

        // Lógica de cálculo do Sedex: R$ 15,00 fixos + R$ 2,00 por cada R$ 100,00
        BigDecimal valorBase = new BigDecimal("15.00");
        BigDecimal adicional = pedido.getValor()
                .divide(new BigDecimal("100.00"), 0, java.math.RoundingMode.UP)
                .multiply(new BigDecimal("2.00"));

        return valorBase.add(adicional);
    }
}

// Estratégia concreta para frete Pac
class Pac implements CalculadoraFrete {
    @Override
    public BigDecimal calcular(Pedido pedido) {
        if (!pedido.getCep().matches("\\d{8}")) {                                                                 // Valida se o CEP é válido
            throw new IllegalArgumentException("CEP inválido para Pac: " + pedido.getCep());
        }

        // Lógica de cálculo do Pac: R$ 10,00 fixos + R$ 1,50 por cada R$ 100,00
        BigDecimal valorBase = new BigDecimal("10.00");
        BigDecimal adicional = pedido.getValor()
                .divide(new BigDecimal("100.00"), 0, java.math.RoundingMode.UP)
                .multiply(new BigDecimal("1.50"));

        return valorBase.add(adicional);
    }
}

// Estratégia concreta para retirada na loja
class RetiradaNaLoja implements CalculadoraFrete {
    @Override
    public BigDecimal calcular(Pedido pedido) {
        return BigDecimal.ZERO;                                                                                         // Para retirada na loja, o frete é sempre zero
    }
}

public class Main {                                                                                                     // Classe principal para demonstrar o uso do padrão Strategy
    public static void main(String[] args) {
        try {
            Pedido pedido = new Pedido("12345678", new BigDecimal("350.00"), new Sedex());                     // Cria um pedido com estratégia inicial Sedex

            System.out.println("Frete Sedex: R$ " + pedido.calcularFrete());                                            // Calcula frete com Sedex

            // Troca para estratégia Pac em tempo de execução
            pedido.setEstrategiaFrete(new Pac());
            System.out.println("Frete Pac: R$ " + pedido.calcularFrete());

            // Troca para retirada na loja
            pedido.setEstrategiaFrete(new RetiradaNaLoja());
            System.out.println("Frete Retirada: R$ " + pedido.calcularFrete());

            // Cria uma estratégia promocional usando lambda (frete grátis acima de R$ 300,00)
            CalculadoraFrete fretePromocional = (p) -> {
                if (p.getValor().compareTo(new BigDecimal("300.00")) > 0) {
                    return BigDecimal.ZERO;                                                                             // Frete grátis
                } else {
                    return new Pac().calcular(p);                                                                       // Usa Pac como fallback
                }
            };

            // Aplica a estratégia promocional
            pedido.setEstrategiaFrete(fretePromocional);
            System.out.println("Frete Promocional: R$ " + pedido.calcularFrete());

            // Teste com CEP inválido (deve lançar exceção)
            Pedido pedidoInvalido = new Pedido("12345", new BigDecimal("100.00"), new Sedex());
            System.out.println("Frete CEP inválido: R$ " + pedidoInvalido.calcularFrete());

        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static CalculadoraFrete criarEstrategiaRegional(String regiao) {                                             // Método adicional para criar estratégias dinâmicas com lambda
        switch (regiao.toUpperCase()) {                                                                                 // Retorna diferentes estratégias baseadas na região usando lambda
            case "SUDESTE":
                return (pedido) -> new BigDecimal("8.00");                                                   // Frete fixo para Sudeste
            case "NORDESTE":
                return (pedido) -> new BigDecimal("12.00");                                                  // Frete fixo para Nordeste
            case "SUL":
                return (pedido) -> new BigDecimal("10.00");                                                  // Frete fixo para Sul
            default:
                return new Pac();                                                                                       // Estratégia padrão
        }
    }
}