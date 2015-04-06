/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lfsrPackege;

import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author Abdullah Fadhel
 */
public class LFSR_MAIN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    Vector<Integer> cipherText;
      cipherText =  LFSR.getInputs();
        System.out.println("this is the cipherText :");
              for(int i = 0 ; i < cipherText.size() ; i++)
          {
              System.out.print(cipherText.get(i));
          }
          
              System.out.println();

}
    
    
    
}
