// CashDispenser.java
// Representa o dispensador de cédulas do ATM

public class CashDispenser {
    // o número inicial padrão de cédulas no dispensador de cédulas
    private final static int INITIAL_COUNT = 500;
    private int count; // número de cédulas de US$ 20 remanescente

    // construtor sem argumentos CashDispenser inicializa a count para o padrão
    public CashDispenser() {
        count = INITIAL_COUNT; // configura atributo count como o padrão
    } // fim do construtor CashDispenser

    // simula a entrega da quantia especificada de cédulas
    public void dispenseCash(int amount) {
        int billsRequired = amount / 20; // número de cédulas de US$ 20 requerido
        count -= billsRequired; // atualiza a contagem das cédulas
    } // fim do método dispenseCash

    // indica se o dispensador de cédulas pode entregar a quantia desejada
    public boolean isSufficientCashAvailable(int amount) {
        int billsRequired = amount / 20; // número de cédulas de US$ 20 requerido

        if (count >= billsRequired)
            return true; // há cédulas suficientes disponíveis
        else
            return false; // não há cédulas suficientes disponíveis
    } // fim do método isSufficientCashAvailable
} // fim da classe CashDispenser