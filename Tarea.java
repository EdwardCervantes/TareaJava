package javaapplication2;
import java.io.*;
import java.util.*;
/**
 *
 * @author Alumno-C
 */

public class JavaApplication2 {
static int charclass;
static char [] lexeme = new char[8];
static char nextChar;
static int lexLen;
static int token;
static int nextToken;
//FILE *in_fp, *fopen();
private static final int LETTER = 0;
private static final int DIGIT = 1;
private static final int UNKNOWN = 99;
private static final int INT_LIT = 10;
private static final int IDENT = 11;
private static final int ASSIGN_OP = 20;
private static final int ADD_OP = 21;
private static final int SUB_OP = 22;
private static final int MULT_OP = 23;
private static final int DIV_OP = 24;
private static final int LEFT_PAREN = 25;
private static final int RIGHT_PAREN = 26;
static File archivo = null;
static FileReader fr = null;
static BufferedReader br = null;
static String linea=null;
static Scanner scanner=null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try{
        archivo = new File ("D:\\archivo.txt");
         fr = new FileReader(archivo);
         br = new BufferedReader(fr);
         //String linea;
         while((linea=br.readLine())!=null)
            System.out.println(linea);
         scanner=new Scanner(archivo);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
         // En el finally cerramos el fichero, para asegurarnos
         // que se cierra tanto si todo va bien como si salta
         // una excepcion.
        try
        {                   
            if( null != fr )
            {  
               fr.close();    
            }                 
        }
        catch (Exception e2)
        {
            e2.printStackTrace();
        }
        }
        //if ((in_fp = fopen("front.in", "r")) == null)
        //    System.out.println("ERROR - cannot open front.in \n");
        //else
        //{
            getChar();
            do
            {
                lex();
            }
            while (nextToken != -100);
        //}
    }
    public static int lookup(char ch)
    {
        switch (ch)
        {
            case '(':
            addChar();
            nextToken = LEFT_PAREN;
            break;
            case ')':
            addChar();
            nextToken = RIGHT_PAREN;
            break;
            case '+':
            addChar();
            nextToken = ADD_OP;
            break;
            case '-':
            addChar();
            nextToken = SUB_OP;
            break;
            case '*':
            addChar();
            nextToken = MULT_OP;
            break;
            case '/':
            addChar();
            nextToken = DIV_OP;
            break;
            default:
            addChar();
            nextToken = -100;
            break;
        }
        return nextToken;
    }
           
    public static void addChar()
    {
        if (lexLen <= 98)
        {
            lexeme[lexLen++] = nextChar;
            lexeme[lexLen] = 0;
        }
        else
            System.out.println("Error - lexeme is too long \n");
    }
    public static void getChar()
    {
        if(scanner.nextLine()!=null)//   if ((nextChar = getc(in_fp)) != EOF)
        {
            if (Character.isAlphabetic(nextChar))
                charclass = LETTER;
            else if (Character.isDigit(nextChar))
                    charclass = DIGIT;
                else charclass = UNKNOWN;
        }
        else
            charclass = -100;
    }
    static void getNonBlank()
    {
        while (Character.isSpace(nextChar))
        getChar();
    }
    static int lex()
    {
        lexLen = 0;
        getNonBlank();
        switch (charclass)
        {
            case LETTER:
            addChar();
            getChar();
            while (charclass == LETTER || charclass == DIGIT)
            {
                addChar();
                getChar();
            }
            nextToken = IDENT;
            break;
            case DIGIT:
            addChar();
            getChar();
            while (charclass == DIGIT)
            {
                addChar();
                getChar();
            }
            nextToken = INT_LIT;
            break;
            case UNKNOWN:
            lookup(nextChar);
            getChar();
            break;
            /* EOF */
            case -100:
            nextToken = -100;
            lexeme[0] = 'E';
            lexeme[1] = 'O';
            lexeme[2] = 'F';
            lexeme[3] = 0;
            break;
        }
        System.out.format("Next token is: %d"+" Next lexeme is %s\n",nextToken, lexeme);
        return nextToken;
    }
}
