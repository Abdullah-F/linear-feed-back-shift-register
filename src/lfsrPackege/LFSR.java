/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lfsrPackege;

import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Abdullah Fadhel
 */
public class LFSR {
public static Vector<Integer> getInputs()
{
StringBuilder  polynomial ;
       Vector<Integer> FB_index,// this vector contains the LFSR degree in the first cell
        //and the remaining cells cintains feedback coeffitcints (powers of each x in the equation).
                initialVector, 
               plainTextVector;
       
       int plainTextSize , lfsrDegree , bit;
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter initial polynomial for example like this x^4+x^3+x^0 format : ");
          polynomial = new StringBuilder(input.nextLine());
          
          
         FB_index =  LFSR.analyzePolynomial(polynomial); // getting the equation informations
          
          
        lfsrDegree =FB_index.get(0); // storing the degree of the LFSR which in the first index in the vector
        
        FB_index.remove(0);// removing the degree from  the vector
        // now the vector only contains the coeffitcints of the FFTs (which powers in the equation
        // except the first power in the equation)
        
        System.out.println("Enter size of plainText : ");
        plainTextSize = input.nextInt();
        
        System.out.println("Enter plainText : ");
        plainTextVector = new Vector<>();
        
        for(int i = 0 ; i < plainTextSize ; i++)
        {
            bit = input.nextInt();
           
            if(bit == 0 || bit == 1)// only 1 or 0 is  allowed  to  be  entered 
            {
                plainTextVector.add(bit);
            
            }
            else{// notify the user 
                System.out.println("you must enter a binary input !");
            // note the error here is not comletly handeled
            }
        }
       
       
         System.out.println("Enter initial vector :");
         initialVector = new Vector<>();// to be used as a starting point
         
         for(int i = 0 ; i < lfsrDegree ; i++)
        {
            bit  = input.nextInt();
            
            if(bit == 0 || bit == 1)
                initialVector.add(bit);
            else
                System.out.println("you must enter a binary input !");

        }
         
       
               return generateCipher(generateKey( plainTextSize ,
                      FB_index , initialVector),plainTextVector);  
    }


public static Vector<Integer> analyzePolynomial( StringBuilder  polynomial)
    {
       
    String number;
    Vector<Integer> feedback_coefficients = new Vector<>();
    
    
    Scanner input = new Scanner(System.in);
    
    polynomial.append('+');// to help later in the check
        
          for(int i = 0 ; i < polynomial.length() ; i++)
          {
              number= "";
              if( polynomial.charAt(i) == '+')//loop till finding a + character
              {
              
              for(int j = i-1 ; polynomial.charAt(j) !='^' ;j-- )//go back and take numbers you find
              {
               number+=polynomial.charAt(j);
              }/// this loop stops when finding ^ in each time
              
                feedback_coefficients.//reversing the string fetched and storing it as a number
                        add(Integer.parseInt(new StringBuilder(number).reverse().toString()));
             
              }
          }
          
      return feedback_coefficients;// a vector of powers in the polynomial    
    }


public static Vector<Integer> generateKey(int plainTextSize ,
        Vector<Integer> FB_index,Vector<Integer> initialVector)
{
    Vector<Integer> key = new Vector<>();
    Collections.reverse(initialVector);// for example 1000 will  be 0001
    
    
    int xor = initialVector.get(FB_index.get(0)); 
       
    for(int j = 1 ; j < FB_index.size() ; j++)// xoring for  first time
        {
           if(xor == initialVector.get(FB_index.get(j)))
               xor = 0;
           else if(xor != initialVector.get(FB_index.get(j)))
               xor = 1;
        }

    for(int i = 0 ; i < plainTextSize ; i++)
    {
        key.add(initialVector.get(0));//output befor shifting is taken
        
        for(int j = 0 ; j < initialVector.size() ; j++)
        {
            if(j != initialVector.size()-1)// this loop  shifts
            initialVector.set(j,initialVector.get(j+1) );
            
        }
        
        initialVector.set(initialVector.size()-1, xor);// storing xored values  in  the  last FFT
        
        xor = initialVector.get(FB_index.get(0));//xoring again and again
        
        for(int j = 1 ; j < FB_index.size() ; j++)
        {
           if(xor == initialVector.get(FB_index.get(j)))
               xor = 0;
           else if(xor != initialVector.get(FB_index.get(j)))
               xor = 1;
        }
        
        
        
    }
    
    return key; // key is generated
}
public static Vector<Integer> generateCipher(Vector<Integer> key,Vector<Integer> plainText)
{
    for(int i = 0 ; i < plainText.size() ; i++)
    {
        if(plainText.get(i) == key.get(i))
        {
            plainText.set(i, 0);
        }
        else if(plainText.get(i) != key.get(i))
        {
            plainText.set(i, 1);
        }
    }
    return plainText;
}
}