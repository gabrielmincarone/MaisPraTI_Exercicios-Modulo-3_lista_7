import java.math.BigDecimal;                                                                                                                    // Para trabalhar com valores monetários de forma precisa

class PagamentoInvalidoException extends Exception {                                                                                            // Classe de exceção personalizada para erros de pagamento
    public PagamentoInvalidoException(String mensagem) {                                                                                        // Construtor que recebe uma mensagem de erro
        super(mensagem);                                                                                                                        // Chama o construtor da classe pai (Exception)
    }
}

abstract class FormaPagamento {                                                                                                                 // Classe abstrata que define a estrutura comum para todas as formas de pagamento

    // Método abstrato que deve ser implementado pelas classes filhas
    public abstract void validarPagamento() throws PagamentoInvalidoException;                                                                  // Responsável por validar os dados específicos de cada forma de pagamento

    // Método abstrato que processa o pagamento após a validação
    public abstract void processarPagamento(BigDecimal valor) throws PagamentoInvalidoException;                                                // Recebe o valor a ser pago como parâmetro

    // Método template que orquestra o fluxo completo de pagamento
    public void realizarPagamento(BigDecimal valor) throws PagamentoInvalidoException {
        validarPagamento();                                                                                                                     // Primeiro valida os dados
        processarPagamento(valor);                                                                                                              // Depois processa o pagamento
    }
}

class CartaoCredito extends FormaPagamento {                                                                                                    // Classe concreta que implementa pagamento por Cartão de Crédito
    private String numeroCartao;                                                                                                                // Número do cartão de crédito
    private String nomeTitular;                                                                                                                 // Nome do titular do cartão
    private String dataValidade;                                                                                                                // Data de validade no formato MM/AA
    private String cvv;                                                                                                                         // Código de segurança

    public CartaoCredito(String numeroCartao, String nomeTitular, String dataValidade, String cvv) {                                            // Construtor que inicializa os atributos do cartão
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.dataValidade = dataValidade;
        this.cvv = cvv;
    }

    @Override                                                                                                                                   // Implementação do método de validação específico para cartão de crédito
    public void validarPagamento() throws PagamentoInvalidoException {
        if (numeroCartao == null || !numeroCartao.matches("\\d{16}")) {                                                                   // Valida se o número do cartão tem 16 dígitos
            throw new PagamentoInvalidoException("Número do cartão inválido. Deve conter 16 dígitos.");
        }

        if (nomeTitular == null || nomeTitular.trim().isEmpty()) {                                                                              // Valida se o nome do titular não está vazio
            throw new PagamentoInvalidoException("Nome do titular é obrigatório.");
        }

        if (dataValidade == null || !dataValidade.matches("\\d{2}/\\d{2}")) {                                                             // Valida o formato da data de validade (MM/AA)
            throw new PagamentoInvalidoException("Data de validade inválida. Use o formato MM/AA.");
        }

        if (cvv == null || !cvv.matches("\\d{3}")) {                                                                                      // Valida se o CVV tem 3 dígitos
            throw new PagamentoInvalidoException("CVV inválido. Deve conter 3 dígitos.");
        }

        System.out.println("Validação do cartão de crédito realizada com sucesso!");
    }

    @Override                                                                                                                                   // Implementação do método de processamento para cartão de crédito
    public void processarPagamento(BigDecimal valor) throws PagamentoInvalidoException {
        // Simula o processamento do pagamento
        System.out.println("Processando pagamento de R$ " + valor + " via Cartão de Crédito");
        System.out.println("Cartão: " + numeroCartao.substring(0, 4) + "**** **** " + numeroCartao.substring(12));
        System.out.println("Pagamento com cartão processado com sucesso!");
    }
}

class Boleto extends FormaPagamento {                                                                                                           // Classe concreta que implementa pagamento por Boleto Bancário
    private String codigoBarras;                                                                                                                // Código de barras do boleto

    public Boleto(String codigoBarras) {                                                                                                        // Construtor que recebe o código de barras
        this.codigoBarras = codigoBarras;
    }

    @Override                                                                                                                                   // Implementação do método de validação específico para boleto
    public void validarPagamento() throws PagamentoInvalidoException {
        if (codigoBarras == null || (!codigoBarras.matches("\\d{44}") && !codigoBarras.matches("\\d{48}"))) {                       // Valida se o código de barras tem 44 ou 48 dígitos (formatos comuns no Brasil)
            throw new PagamentoInvalidoException("Código de barras inválido. Deve conter 44 ou 48 dígitos.");
        }

        System.out.println("Validação do boleto realizada com sucesso!");
    }

    @Override                                                                                                                                   // Implementação do método de processamento para boleto
    public void processarPagamento(BigDecimal valor) throws PagamentoInvalidoException {
        // Simula a geração do boleto
        System.out.println("Processando pagamento de R$ " + valor + " via Boleto Bancário");
        System.out.println("Código de barras: " + codigoBarras);
        System.out.println("Boleto gerado com sucesso! Pagamento pendente até a data de vencimento.");
    }
}

class Pix extends FormaPagamento {                                                                                                              // Classe concreta que implementa pagamento por PIX
    private String chavePix;                                                                                                                    // Chave PIX (CPF, email, telefone ou chave aleatória)

    public Pix(String chavePix) {                                                                                                               // Construtor que recebe a chave PIX
        this.chavePix = chavePix;
    }

    @Override                                                                                                                                   // Implementação do método de validação específico para PIX
    public void validarPagamento() throws PagamentoInvalidoException {
        if (chavePix == null || chavePix.trim().isEmpty()) {                                                                                    // Valida se a chave PIX não está vazia
            throw new PagamentoInvalidoException("Chave PIX é obrigatória.");
        }

        if (chavePix.length() < 5) {                                                                                                            // Valida se a chave PIX tem um formato válido (exemplo simplificado)
            throw new PagamentoInvalidoException("Chave PIX inválida. Muito curta.");
        }

        System.out.println("Validação do PIX realizada com sucesso!");
    }

    @Override                                                                                                                                   // Implementação do método de processamento para PIX
    public void processarPagamento(BigDecimal valor) throws PagamentoInvalidoException {
        // Simula o processamento instantâneo do PIX
        System.out.println("Processando pagamento de R$ " + valor + " via PIX");
        System.out.println("Chave PIX: " + chavePix);
        System.out.println("Pagamento PIX processado instantaneamente com sucesso!");
    }
}

public class SistemaPagamentos {                                                                                                                // Classe principal para demonstrar o uso do sistema de pagamentos

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE PAGAMENTOS ===\n");

        // Cria diferentes formas de pagamento
        FormaPagamento cartao = new CartaoCredito("1234567812345678", "João Silva", "12/25", "123");
        FormaPagamento boleto = new Boleto("00193373700000010000500940144816060680935031");
        FormaPagamento pix = new Pix("joao.silva@email.com");

        BigDecimal valor = new BigDecimal("150.99");                                                                                        // Valor a ser pago

        FormaPagamento[] formasPagamento = {cartao, boleto, pix};                                                                               // Demonstra polimorfismo - tratando todas as formas de pagamento da mesma maneira

        for (FormaPagamento forma : formasPagamento) {                                                                                          // Processa cada forma de pagamento
            try {
                System.out.println("\n--- Tentando pagamento com " + forma.getClass().getSimpleName() + " ---");
                forma.realizarPagamento(valor);                                                                                                 // Chamada polimórfica
            } catch (PagamentoInvalidoException e) {
                System.err.println("Erro no pagamento: " + e.getMessage());                                                                     // Trata exceções específicas de pagamento
            } catch (Exception e) {
                System.err.println("Erro inesperado: " + e.getMessage());                                                                       // Trata outras exceções genéricas
            }
        }

        System.out.println("\n=== TESTANDO VALIDAÇÕES COM DADOS INVÁLIDOS ===\n");                                                              // Demonstra tratamento de erros com dados inválidos

        try {
            // Cartão com número inválido
            FormaPagamento cartaoInvalido = new CartaoCredito("1234", "João", "12/25", "123");
            cartaoInvalido.realizarPagamento(valor);
        } catch (PagamentoInvalidoException e) {
            System.err.println("Erro esperado com cartão inválido: " + e.getMessage());
        }

        try {
            // Boleto com código inválido
            FormaPagamento boletoInvalido = new Boleto("123");
            boletoInvalido.realizarPagamento(valor);
        } catch (PagamentoInvalidoException e) {
            System.err.println("Erro esperado com boleto inválido: " + e.getMessage());
        }

        try {
            // PIX com chave vazia
            FormaPagamento pixInvalido = new Pix("");
            pixInvalido.realizarPagamento(valor);
        } catch (PagamentoInvalidoException e) {
            System.err.println("Erro esperado com PIX inválido: " + e.getMessage());
        }
    }
}