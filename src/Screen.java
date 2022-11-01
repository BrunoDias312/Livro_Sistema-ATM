// Representa a tela do ATM


public class Screen
{
    // exibe uma mensagem sem retorno de carro
    public void displayMessage(String message )
    {
        System.out.print( message );
    }// fim do metodo displayMessage

    //exibe uma mensagem com um retorno de carro
    public void displayMessageLine(String message)
    {
        System.out.println(message);
    }// fim do metodo displayMessageLine

    //exibe um valor em dolares
    public void displayDollarAmount( double amount)
    {
        System.out.printf("$%,.2f", amount );
    }// Fim do método displayDollarAmount
} // Fim da classe Screen
