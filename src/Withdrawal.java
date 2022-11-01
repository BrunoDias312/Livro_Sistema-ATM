// A classe Withdrawal representa uma transação de saque no ATM

public class Withdrawal extends Transaction {
    //Atributos
    private int amount; // Quantia a sacar
    private Keypad keypad; // Referencia ao teclado numerico
    private CashDispenser cashDispenser; // Dispensador de celulas do ATM


    // constante que corresponde à opção cancelar no menu
    private final static int CANCELED = 6;

    // Construtor de Withdrawal
    public Withdrawal(int userAccountNumber, Screen atmScreen,
                      BankDatabase atmBankDatabase, Keypad atmKeypad,
                      CashDispenser atmCashDispenser) {
        // inicializa as variáveis da superclasse
        super(userAccountNumber, atmScreen, atmBankDatabase);

        // inicializa as referências ao teclado numérico e ao dispensador de cédulas
        keypad = atmKeypad;
        cashDispenser = atmCashDispenser;
    } // fim do construtor de Withdrawal

    // realiza a transação
    @Override
    public void execute() {
        boolean cashDispensed = false; // cédulas ainda não foram entregues
        double availableBalance; // quantia disponível para saque

        // obtém as referências ao banco de dados e tela do banco
        BankDatabase bankDatabase = getBankDatabase();
        Screen screen = getScreen();

        // faz um loop até as cédulas serem entregues ou o usuário cancelar
        do {
            // obtém a quantia de um saque escolhida pelo usuário
            amount = displayMenuOfAmounts();

            // verifica se o usuário escolheu uma quantia de saque ou cancelou
            if (amount != CANCELED) {
                // obtém o saldo disponível na conta envolvida
                availableBalance =
                        bankDatabase.getAvailableBalance(getAccountNumber());

                // verifica se o usuário tem dinheiro suficiente na conta
                if (amount <= availableBalance) {
                    // verifica se o dispensador de cédulas tem cédulas suficientes
                    if (cashDispenser.isSufficientCashAvailable(amount)) {
                        // atualiza a conta envolvida para refletir a retirada/saque
                        bankDatabase.debit(getAccountNumber(), amount);

                        cashDispenser.dispenseCash(amount); // entrega cédulas
                        cashDispensed = true; // cédulas foram entregues

                        // instrui o usuário a pegar as cédulas
                        screen.displayMessageLine("\nYour cash has been" +
                                " dispensed. Please take your cash now.");
                    } // fim do if
                    else // o dispensador de cédulas não tem cédulas suficientes
                        screen.displayMessageLine(
                                "\nInsufficient cash available in the ATM." +
                                        "\n\nPlease choose a smaller amount.");
                } // fim do if
                else // não há dinheiro suficiente disponível na conta do usuário
                {
                    screen.displayMessageLine(
                            "\nInsufficient funds in your account." +
                                    "\n\nPlease choose a smaller amount.");
                } // fim de else
            } // fim do if
            else // o usuário escolheu a opção cancelar no menu
            {
                screen.displayMessageLine("\nCanceling transaction...");
                return; // retorna ao menu principal porque o usuário cancelou
            } // fim de else
        } while (!cashDispensed);

    } // fim do método execute

    // exibe um menu de quantias de saques e a opção para cancelar;
    // retorna a quantia escolhida ou 0 se o usuário escolher cancelar
    private int displayMenuOfAmounts() {
        int userChoice = 0; // variável local para armazenar o valor de retorno

        Screen screen = getScreen(); // obtém referência de tela

        // array de quantias que correspondem aos números no menu
        int[] amounts = {0, 20, 40, 60, 100, 200};

        // faz um loop enquanto nenhuma escolha válida for feita
        while (userChoice == 0) {
            // exibe o menu
            screen.displayMessageLine("\nWithdrawal Menu:");
            screen.displayMessageLine("1 - $20");
            screen.displayMessageLine("2 - $40");
            screen.displayMessageLine("3 - $60");
            screen.displayMessageLine("4 - $100");
            screen.displayMessageLine("5 - $200");
            screen.displayMessageLine("6 - Cancel transaction");
            screen.displayMessage("\nChoose a withdrawal amount: ");

            int input = keypad.getInput(); // obtém a entrada de usuário pelo teclado

            // determina como prosseguir com base no valor de entrada
            switch (input) {
                case 1: // se o usuário escolheu uma quantia de saque
                case 2: // (isto é, escolheu a opção 1, 2, 3, 4 ou 5), retorna a
                case 3: // quantia correspondente do array de quantias
                case 4:
                case 5:
                    userChoice = amounts[input]; // salva a escolha do usuário
                    break;
                case CANCELED: // o usuário escolheu cancelar
                    userChoice = CANCELED; // salva a escolha do usuário
                    break;
                default: // o usuário não inseriu um valor ente 1 e 6
                    screen.displayMessageLine(
                            "\nInvalid selection. Try again.");
            } // fim do switch
        } // fim do while

        return userChoice; // retorna a quantia de saque ou CANCELADA
    } // fim do método displayMenuOfAmounts
} // fim da classe Withdrawal