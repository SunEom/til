package FinalExample.ycho2021;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Locale;

public class Main {
   public static void main(String[] args){
      /* String str = "Java is a high-level, class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible. It is a general-purpose programming language intended to let programmers write once, run anywhere (WORA),[17] meaning that compiled Java code can run on all platforms that support Java without the need for recompilation.[18] Java applications are typically compiled to bytecode that can run on any Java virtual machine (JVM) regardless of the underlying computer architecture. The syntax of Java is similar to C and C++, but has fewer low-level facilities than either of them. The Java runtime provides dynamic capabilities (such as reflection and runtime code modification) that are typically not available in traditional compiled languages. As of 2019, Java was one of the most popular programming languages in use according to GitHub,[19][20] particularly for client–server web applications, with a reported 9 million developers.[21]\n" +
              "\n" +
              "Java was originally developed by James Gosling at Sun Microsystems (which has since been acquired by Oracle) and released in 1995 as a core component of Sun Microsystems' Java platform. The original and reference implementation Java compilers, virtual machines, and class libraries were originally released by Sun under proprietary licenses. As of May 2007, in compliance with the specifications of the Java Community Process, Sun had relicensed most of its Java technologies under the GPL-2.0-only license. Oracle offers its own HotSpot Java Virtual Machine, however the official reference implementation is the OpenJDK JVM which is free open-source software and used by most developers and is the default JVM for almost all Linux distributions.\n" +
              "\n" +
              "As of October 2021, Java 17 is the latest version. Java 8, 11 and 17 are the current long-term support (LTS) versions. Oracle released the last zero-cost public update for the legacy version Java 8 LTS in January 2019 for commercial use, although it will otherwise still support Java 8 with public updates for personal use indefinitely. Other vendors have begun to offer zero-cost builds of OpenJDK 8 and 11 that are still receiving security and other upgrades.\n" +
              "\n" +
              "Oracle (and others) highly recommend uninstalling outdated and unsupported versions of Java, because of serious risks due to unresolved security issues.[22] Oracle advises its users to immediately transition to a supported version, such as one of the LTS versions (8, 11, 17).";*/
      try{
         FileReader fr = new FileReader("sentence.txt");
         BufferedReader br = new BufferedReader(fr);
         String str = "";
         int data;
         while(( data = br.read()) != -1){
            str += "" + (char)data;
         }

         String[] notAllow = {"17", "18", "19", "20", "21", "of", "to", "a", "on","8","11","as","by"};
         char[] separator = {' ','\t','\n',',','.',':','(',')','{','}','[',']','\'','\"'};

         WordCounter wc = new WordCounter(str.toLowerCase(),separator, notAllow);
         wc.countWords();
//      wc.printWords();
         wc.printResult(3);
         System.out.println("\n\n\n");
         wc.printResult(5);

      } catch(Exception e){
         e.printStackTrace();
      }
   }

}
