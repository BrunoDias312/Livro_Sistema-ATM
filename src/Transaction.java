// Transaction.java
// A superclasse abstrata Transaction representa uma transação no ATM

public abstract class Transaction {
    private int accountNumber; // indica conta envolvida
    private Screen screen; // Tela do ATM
    private BankDatabase bankDatabase; // banco de dados de informações sobre a conta

    // Construtor de Transaction invocado pelas subclasses utilizando super()
    public Transaction(int userAccountNumber, Screen atmScreen,
                       BankDatabase atmBankDatabase) {
        accountNumber = userAccountNumber;
        screen = atmScreen;
        bankDatabase = atmBankDatabase;
    } // fim do construtor de Transaction

    // retorna o número de conta
    public int getAccountNumber() {
        return accountNumber;
    } // fim do método getAccountNumber

    // retorna a referência à tela
    public Screen getScreen() {
        return screen;
    } // fim do método getScreen

    // retorna a referência ao banco de dados da instituição financeira
    public BankDatabase getBankDatabase() {
        return bankDatabase;
    } // fim do método getBankDatabase

    // realiza a transação (sobrescrita por cada subclasse)
    abstract public void execute();
} // fim da classe Transaction