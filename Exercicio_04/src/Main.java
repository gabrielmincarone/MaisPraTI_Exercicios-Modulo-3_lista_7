import java.util.ArrayList;
import java.util.List;

interface IMeioTransporte {                                                                                 // Interface que define o contrato para todos os meios de transporte
    void acelerar(int incremento) throws VelocidadeException;                                               // Método para acelerar o veículo

    void frear(int decremento) throws VelocidadeException;                                                  // Método para frear o veículo

    int getVelocidadeAtual();                                                                               // Método para obter a velocidade atual
}

class VelocidadeException extends Exception {                                                               // Exceção personalizada para tratar erros relacionados à velocidade
    public VelocidadeException(String mensagem) {
        super(mensagem);                                                                                    // Chama o construtor da classe pai Exception
    }
}

class Carro implements IMeioTransporte {                                                                    // Classe que representa um Carro, implementando a interface IMeioTransporte
    private int velocidadeAtual;                                                                            // Velocidade atual do carro
    private static final int VELOCIDADE_MAXIMA = 200;                                                       // Limite máximo de velocidade

    public Carro() {                                                                                        // Construtor que inicializa o carro parado
        this.velocidadeAtual = 0;
    }

    @Override                                                                                               // Implementação do método acelerar para carro
    public void acelerar(int incremento) throws VelocidadeException {
        if (incremento <= 0) {                                                                              // Verifica se o incremento é válido
            throw new VelocidadeException("Incremento de velocidade deve ser positivo");
        }

        int novaVelocidade = velocidadeAtual + incremento;                                                  // Calcula a nova velocidade

        if (novaVelocidade > VELOCIDADE_MAXIMA) {                                                           // Verifica se excede o limite máximo
            throw new VelocidadeException("Carro não pode exceder " + VELOCIDADE_MAXIMA + " km/h");
        }

        velocidadeAtual = novaVelocidade;                                                                   // Atualiza a velocidade
        System.out.println("Carro acelerou para: " + velocidadeAtual + " km/h");
    }

    @Override                                                                                               // Implementação do método frear para carro
    public void frear(int decremento) throws VelocidadeException {
        if (decremento <= 0) {                                                                              // Verifica se o decremento é válido
            throw new VelocidadeException("Decremento de velocidade deve ser positivo");
        }

        int novaVelocidade = velocidadeAtual - decremento;                                                  // Calcula a nova velocidade

        if (novaVelocidade < 0) {                                                                           // Verifica se a velocidade ficaria negativa
            throw new VelocidadeException("Velocidade não pode ser negativa");
        }

        velocidadeAtual = novaVelocidade;                                                                   // Atualiza a velocidade
        System.out.println("Carro freou para: " + velocidadeAtual + " km/h");
    }

    @Override                                                                                               // Método para obter a velocidade atual
    public int getVelocidadeAtual() {
        return velocidadeAtual;
    }
}

class Bicicleta implements IMeioTransporte {                                                                // Classe que representa uma Bicicleta, implementando a interface IMeioTransporte
    private int velocidadeAtual;                                                                            // Velocidade atual da bicicleta
    private static final int VELOCIDADE_MAXIMA = 40;                                                        // Limite máximo de velocidade

    public Bicicleta() {                                                                                    // Construtor que inicializa a bicicleta parada
        this.velocidadeAtual = 0;
    }

    @Override                                                                                               // Implementação do método acelerar para bicicleta
    public void acelerar(int incremento) throws VelocidadeException {
        if (incremento <= 0) {                                                                              // Verifica se o incremento é válido
            throw new VelocidadeException("Incremento de velocidade deve ser positivo");
        }

        int novaVelocidade = velocidadeAtual + incremento;                                                  // Calcula a nova velocidade

        if (novaVelocidade > VELOCIDADE_MAXIMA) {                                                           // Verifica se excede o limite máximo (mais baixo que carro)
            throw new VelocidadeException("Bicicleta não pode exceder " + VELOCIDADE_MAXIMA + " km/h");
        }

        velocidadeAtual = novaVelocidade;                                                                   // Atualiza a velocidade
        System.out.println("Bicicleta acelerou para: " + velocidadeAtual + " km/h");
    }

    @Override                                                                                               // Implementação do método frear para bicicleta
    public void frear(int decremento) throws VelocidadeException {
        if (decremento <= 0) {                                                                              // Verifica se o decremento é válido
            throw new VelocidadeException("Decremento de velocidade deve ser positivo");
        }

        int novaVelocidade = velocidadeAtual - decremento;                                                  // Calcula a nova velocidade

        if (novaVelocidade < 0) {                                                                           // Verifica se a velocidade ficaria negativa
            throw new VelocidadeException("Velocidade não pode ser negativa");
        }

        velocidadeAtual = novaVelocidade;                                                                   // Atualiza a velocidade
        System.out.println("Bicicleta freou para: " + velocidadeAtual + " km/h");
    }

    @Override                                                                                               // Método para obter a velocidade atual
    public int getVelocidadeAtual() {
        return velocidadeAtual;
    }
}

class Trem implements IMeioTransporte {                                                                     // Classe que representa um Trem, implementando a interface IMeioTransporte
    private int velocidadeAtual;                                                                            // Velocidade atual do trem
    private static final int VELOCIDADE_MAXIMA = 120;                                                       // Limite máximo de velocidade
    private static final int VELOCIDADE_MINIMA = 20;                                                        // Limite mínimo de velocidade (trem não pode andar muito devagar)

    public Trem() {                                                                                         // Construtor que inicializa o trem parado
        this.velocidadeAtual = 0;
    }

    @Override                                                                                               // Implementação do método acelerar para trem
    public void acelerar(int incremento) throws VelocidadeException {
        if (incremento <= 0) {                                                                              // Verifica se o incremento é válido
            throw new VelocidadeException("Incremento de velocidade deve ser positivo");
        }

        int novaVelocidade = velocidadeAtual + incremento;                                                  // Calcula a nova velocidade

        if (novaVelocidade > VELOCIDADE_MAXIMA) {                                                           // Verifica se excede o limite máximo
            throw new VelocidadeException("Trem não pode exceder " + VELOCIDADE_MAXIMA + " km/h");
        }

        velocidadeAtual = novaVelocidade;                                                                   // Atualiza a velocidade
        System.out.println("Trem acelerou para: " + velocidadeAtual + " km/h");
    }

    @Override                                                                                               // Implementação do método frear para trem (com regras específicas)
    public void frear(int decremento) throws VelocidadeException {
        if (decremento <= 0) {                                                                              // Verifica se o decremento é válido
            throw new VelocidadeException("Decremento de velocidade deve ser positivo");
        }

        int novaVelocidade = velocidadeAtual - decremento;                                                  // Calcula a nova velocidade

        if (novaVelocidade < 0) {                                                                           // Verifica se a velocidade ficaria negativa
            throw new VelocidadeException("Velocidade não pode ser negativa");
        }

        if (novaVelocidade > 0 && novaVelocidade < VELOCIDADE_MINIMA) {                                     // Verifica se a velocidade ficaria abaixo do mínimo permitido para trem
            throw new VelocidadeException("Trem não pode andar abaixo de " + VELOCIDADE_MINIMA + " km/h");
        }

        velocidadeAtual = novaVelocidade;                                                                   // Atualiza a velocidade

        if (velocidadeAtual == 0) {                                                                         // Mensagem diferente se parou completamente
            System.out.println("Trem parou completamente");
        } else {
            System.out.println("Trem freou para: " + velocidadeAtual + " km/h");
        }
    }

    @Override                                                                                               // Método para obter a velocidade atual
    public int getVelocidadeAtual() {
        return velocidadeAtual;
    }
}

public class Main {                                                                                         // Classe principal que demonstra o polimorfismo
    public static void main(String[] args) {
        List<IMeioTransporte> transportes = new ArrayList<>();                                              // Cria uma lista de IMeioTransporte (polimorfismo)

        // Adiciona diferentes tipos de veículos à lista
        transportes.add(new Carro());
        transportes.add(new Bicicleta());
        transportes.add(new Trem());

        for (IMeioTransporte transporte : transportes) {                                                    // Percorre a lista e demonstra polimorfismo
            System.out.println("\n--- Operando " + transporte.getClass().getSimpleName() + " ---");

            try {
                // Tenta acelerar cada veículo
                transporte.acelerar(50);
                transporte.acelerar(30);

                // Tenta frear cada veículo
                transporte.frear(20);
                transporte.frear(10);

            } catch (VelocidadeException e) {
                System.out.println("Erro: " + e.getMessage());                                              // Captura e trata exceções específicas de velocidade
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());                                   // Captura outras exceções genéricas
            }
        }

        System.out.println("\n--- Testando operações inválidas ---");                                       // Teste adicional com operações que geram exceções

        try {
            Carro carro = new Carro();
            carro.acelerar(-10);                                                                  // Incremento negativo - deve gerar exceção
        } catch (VelocidadeException e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }

        try {
            Bicicleta bicicleta = new Bicicleta();
            bicicleta.acelerar(100);                                                              // Excede limite máximo - deve gerar exceção
        } catch (VelocidadeException e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }

        try {
            Trem trem = new Trem();
            trem.acelerar(30);
            trem.frear(25);                                                                      // Fica abaixo do mínimo permitido - deve gerar exceção
        } catch (VelocidadeException e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }
    }
}