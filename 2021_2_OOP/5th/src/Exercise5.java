import java.util.Scanner;

public class Exercise5 {
   public static void main(String[] args){
      Scanner sc = new Scanner(System.in);
      int num = sc.nextInt();

      while(num != 0){
         System.out.println(num);
         num = sc.nextInt();
      }
   }
}
