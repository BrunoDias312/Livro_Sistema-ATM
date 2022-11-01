//Representa um caixa eletronico


public class ATM
{
    private boolean userAuthenticated;
    private int currentAccountNumber;
    private Screen screen; // Tela do ATM
    private Keypad keypad; // Teclado do ATM
    private CashDispenser cashDispenser; // Dispensador de celulas do ATM
    private DepositSlot depositSlot; // abertura para deposito do ATM
    private BankDatabase bankDatabase; // Banco de dados sobre a conta

    //Constante que correspondem ás principais opçoes de menu
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int DEPOSIT = 3;
    private static final int EXIT = 4;

    //Construtor sem argumentos de ATM inicializa as variavesis de instancia
    public ATM()
    {
        userAuthenticated = false; // usuario não foi autenticado para iniciar
        currentAccountNumber = 0; // Nenhum numero atual de conta para iniciar
        screen = new Screen(); // cria a tela
        keypad = new Keypad(); // Cria o teclado
        cashDispenser = new CashDispenser(); // Cria o dispensador de cedulas
        depositSlot = new DepositSlot(); // Cria a abertura para deposito
        bankDatabase = new BankDatabase(); // cria o banco de dados com informações sobre as contas
    }// Fim do construtor ATM sem argumentos

    // Inicia o ATM
    public void run()
    {
        // Dá boas-vindas e autentica o usuario; realiza transações
        while (true)
        {
            // Faz um loop enquanto o usuario ainda nao esta autenticado
            while(!userAuthenticated)
            {
                screen.displayMessageLine("\nWelcome!");
                authenticateUser(); //autentica o usuario
            }// Fim do while

            performTransactions(); // O usuario agora esta autenticado
            userAuthenticated = false; // reinicializa antes da proxima sessao do ATM
            currentAccountNumber = 0; // Renicializa antes da proxima sessao do ATM
            screen.displayMessageLine ("\nThank you! Goodbye!");
        }// Fim do while
    }// Fim do metodo run

    // tenta autenticar o usuario contra o banco de dados
    private void authenticateUser()
    {
        screen.displayMessage("\nPlease enter you account number: ");
        int accountNumber = keypad.getInput(); // insere o numero de conta
        screen.displayMessage("\nEnter your PIN: "); //Solicita o PIN
        int pin = keypad.getInput(); //insere o PIN

        // configura userAuthenticated como um valor booleano retornado pelo banco de dados
        userAuthenticated =
                bankDatabase.authenticateUser( accountNumber, pin );

        // Verificar se a autenticação foi bem-sucedida
        if (userAuthenticated)
        {
            currentAccountNumber = accountNumber; // Salva a conta do usuario #
        }// fim do if
        else
            screen.displayMessageLine("Invalid account number or PIN. Please try again.");

    }//Fim do metodo authenticateUser

    // exibe o menu principal e realiza transações
    private void performTransactions()
    {
        // Variável local para armazenar a transação atualmente processada
        Transaction currentTransaction = null;

        boolean userExited = false; // usuario optou por nao sair

        // faz um loop enquanto o usuario nao escolher a opção para sair do sistema
        while(!userExited)
        {
            //mostra o menu principal e obtem a seleção de usuario
            int mainMenuSelection = displayMainMenu();

            // Decide como prosseguir com base na seleção de menu feita pelo usuario
            switch ( mainMenuSelection )
            {
                // O Usuario optou pro realizar um entre três tipos transações
                case BALANCE_INQUIRY:
                case WITHDRAWAL:
                case DEPOSIT:

                    // Inicializa como o novo objeto do tipo escolhido
                    currentTransaction =
                            createTransaction( mainMenuSelection );

                    currentTransaction.execute(); // executa a transação
                    break;
                case EXIT: // Usuario optou por terminar a sessão
                    screen.displayMessageLine("\nExiting the system...");
                    userExited = true; //Essa sessao de ATM deve terminar
                    break;
                default: // usuario não inseriu um inteiro de 1 a 4
                    screen.displayMessageLine(
                            "\nYou did not enter a valid selection. Try again.");
                    break;
            }// Fim do switch
        }// Fim do while
    }// Fim do metodo performTransactions

    // exibe o menu principal e retorna uma seleção de entrada
    private int displayMainMenu()
    {
        screen.displayMessageLine( "\nMain menu: " );
        screen.displayMessageLine( "1 - View my balance: " );
        screen.displayMessageLine( "2 - Withdraw cash" );
        screen.displayMessageLine( "3 - Deposit funds" );
        screen.displayMessageLine( "4 - Exit\n" );
        screen.displayMessageLine( "5 - Enter a choice: " );
        return keypad.getInput(); // returna a seleção do usuario
    } // fim do método displayMainMenu

    // Retorna o objeto da subclasse de transactoin especificada
    private Transaction createTransaction( int type )
    {
        Transaction temp = null; // variavel Transaction Temporaria

        //Determina qual tipo de Transaction criar
        switch ( type )
        {
            case BALANCE_INQUIRY: //cria uma nova transação BalanceInquiry
                temp = new BalanceInquiry(
                        currentAccountNumber, screen, bankDatabase);
                break;
            case WITHDRAWAL: // cria uma nova transação Withdrawal
                temp = new Withdrawal( currentAccountNumber, screen,
                        bankDatabase, keypad, cashDispenser);
                break;
            case DEPOSIT: // cria uma nova transação Deposit
                temp = new Deposit( currentAccountNumber, screen,
                        bankDatabase, keypad, depositSlot);
                break;
        } // fim do switch

        return temp; // retorna o objeto recem criado
    }// Fim do metodo createTransaction
} // Fim da Classe ATM
