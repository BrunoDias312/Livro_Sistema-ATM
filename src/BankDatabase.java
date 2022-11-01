// BankDatabase.java
// Representa o banco de dados com as informações sobre as contas bancárias

public class BankDatabase {
    private Account[] accounts; // array de Accounts

    // construtor BankDatabase sem argumentos inicializa as contas
    public BankDatabase() {
        accounts = new Account[2]; // apenas 2 contar para teste
        accounts[0] = new Account(12345, 54321, 1000.0, 1200.0);
        accounts[1] = new Account(98765, 56789, 200.0, 200.0);
    } // fim do construtor BankDatabase sem argumentos

    // recupera o objeto Account que contém o número de conta especificado
    private Account getAccount(int accountNumber) {
        // faz um loop pelas contas procurando uma correspondência com o número de conta
        for (Account currentAccount : accounts) {
            // retorna a conta atual se uma correspondência for localizada
            if (currentAccount.getAccountNumber() == accountNumber)
                return currentAccount;
        } // for final

        return null; // se nenhuma correspondência com uma conta foi localizada, retorna null
    } // fim do método getAccount

    // determina se número da conta e PIN especificados pelo usuário correspondem
    // àqueles de uma conta no banco de dados
    public boolean authenticateUser(int userAccountNumber, int userPIN) {
        // tenta recuperar a conta com o número da conta
        Account userAccount = getAccount(userAccountNumber);

        // se a conta existir, retorna o resultado do método validatePIN de Account
        if (userAccount != null)
            return userAccount.validatePIN(userPIN);
        else
            return false; // número de conta não foi localizado, portanto retorna false
    } // fim do método authenticateUser

    // retorna o saldo disponível de Account com o número da conta especificado
    public double getAvailableBalance(int userAccountNumber) {
        return getAccount(userAccountNumber).getAvailableBalance();
    } // fim do método getAvailableBalance

    // retorna o saldo total de Account com o número da conta especificado
    public double getTotalBalance(int userAccountNumber) {
        return getAccount(userAccountNumber).getTotalBalance();
    } // fim do método getTotalBalance

    // credita uma quantia a Account com o número da conta especificado
    public void credit(int userAccountNumber, double amount) {
        getAccount(userAccountNumber).credit(amount);
    } // fim do método credit

    // debita uma quantidade da Account com o número da conta especificado
    public void debit(int userAccountNumber, double amount) {
        getAccount(userAccountNumber).debit(amount);
    } // fim do método debit
} // fim da classe BankDatabase