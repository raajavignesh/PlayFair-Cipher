package com.RVCodes;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    private static String keyGenerator(String key) {
        char[] keychar = key.toCharArray();
        int flag = 0;
        String temp = String.valueOf(keychar[0]);
        for(int i=1; i<keychar.length; i++)
        {
            char[] temparr = temp.toCharArray();
            for(int j=0; j<temparr.length; j++)
            {
                if(keychar[i] == temparr[j])
                {
                    flag = 0;
                    break;
                }
                else
                {
                    flag = 1;
                }
            }
            if(flag == 1)
            {
                temp = temp + keychar[i];
            }
        }
        System.out.println(temp);
        keychar = temp.toCharArray();
        String fullkey = temp;
        flag = 0;
        for(int i=0; i<26;i++)
        {
            char alpha = (char)(i+97);
            for(int j=0;j<keychar.length;j++)
            {
                if(alpha == keychar[j])
                {
                    flag=0;
                    break;
                }
                else
                {
                    flag=1;
                }
            }
            if(flag==1)
            {
                fullkey +=alpha;
            }
        }
        fullkey = fullkey.replace('j', 'i');
        System.out.println(fullkey+" "+fullkey.length());
        String finalkey="";
        int count = 0;
        for(int i=0; i<26; i++)
        {
            if(fullkey.charAt(i) == 'i')
            {
                if(count == 1)
                {
                    continue;
                }
                finalkey = finalkey + fullkey.charAt(i);
                count++;
            }
            else
            {
                finalkey = finalkey + fullkey.charAt(i);
            }
        }
        System.out.println(finalkey);
        return finalkey;
    }

    private static char[][] matrixFormation(String fullkey) {
        char[] key = fullkey.toCharArray();
        char[][] keymatrix = new char[5][5];
        int x=0;
        for(int i=0; i<5; i++)
        {
            for(int j=0; j<5; j++)
            {
                keymatrix[i][j] = key[x];
                x++;
            }
        }
        return keymatrix;
    }

    private static String formatPlainText(String plain) {
        String rep = plain.replace('j', 'i');
        plain = rep;
        for(int k=0; k<plain.length()/2; k++)
        {
            StringBuilder rev  = new StringBuilder();
            rev.append(plain);
            String reverse = rev.reverse().toString();
            char[] reversearr = reverse.toCharArray();
            Stack<Character> s = new Stack();
            for(int i=0; i<reverse.length(); i++)
            {
                s.push(reversearr[i]);
            }
            String ans = "";
            for(int i=0; i<reverse.length()/2; i++)
            {
                char x = s.pop();
                char y = s.pop();
                if(x==y)
                {
                    String temp = x + "x";
                    s.push(y);
                    ans = ans + temp;
                    break;
                }
                ans = ans + x + y;
            }
            while(true)
            {
                if(s.isEmpty())
                {
                    break;
                }
                else
                {
                    ans = ans + s.pop();
                }
            }
            plain = ans;
        }
        if(plain.length()%2 != 0)
        {
            plain = plain + "x";
        }
        return plain;
    }

    private static String findCoordinates(char x, char[][] keytable) {
        String cor="";
        for(int i=0; i<5; i++)
        {
            for(int j=0; j<5; j++)
            {
                if(keytable[i][j] == x)
                {
                    cor = i+","+j;
                    break;
                }
            }
        }
        return cor;
    }


    private static String encrypt(String[] plainarray, char[][] keytable) {
        String encrypted = "";
        for(int i=0; i<plainarray.length; i++)
        {
            String value = "";
            char[] temp = plainarray[i].toCharArray();
            char x = temp[0];
            char y = temp[1];
            String xcor = findCoordinates(x,keytable);
            String ycor = findCoordinates(y,keytable);
            System.out.println(xcor+" "+ycor);
            String[] xsplit = xcor.split(",");
            String[] ysplit = ycor.split(",");
            int x1 = Integer.parseInt(xsplit[0]);
            int y1 = Integer.parseInt(xsplit[1]);
            int x2 = Integer.parseInt(ysplit[0]);
            int y2 = Integer.parseInt(ysplit[1]);
            System.out.println(x1+" "+y1+" , "+x2+" "+y2);
            if(y1==y2)
            {
                if(x1<x2)
                {
                    x1=(x1+1)%5;
                    x2=(x2+1)%5;
                    value+=keytable[x1][y1];
                    value+=keytable[x2][y2];
                    System.out.println("1");
                }
                else if(x2<x1)
                {
                    x1=(x1-1)%5;
                    x2=(x2-1)%5;
                    if(x1<0)
                        x1+=5;
                    if(x2<0)
                        x2+=5;
                    value+=keytable[x1][y1];
                    value+=keytable[x2][y2];
                    System.out.println("2");
                }
            }
            else if(x1==x2)
            {

                if(y1<y2)
                {
                    y1=(y1+1)%5;
                    y2=(y2+1)%5;
                    value+=keytable[x1][y1];
                    value+=keytable[x2][y2];
                    System.out.println("3");
                }
                else if(y2<y1)
                {
                    y1=(y1-1)%5;
                    y2=(y2-1)%5;
                    if(y1<0)
                        y1+=5;
                    if(y2<0)
                        y2+=5;
                    value+=keytable[x1][y1];
                    value+=keytable[x2][y2];
                    System.out.println("4");
                }

            }
            else if(x1!=x2 && y1!=y2)
            {
                value+=keytable[x2][y1];
                value+=keytable[x1][y2];

                System.out.println("5");
            }
            encrypted+=value;
        }
        return encrypted;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter plain text : ");
        String plain = s.nextLine();
        String formattedplain = formatPlainText(plain);
        System.out.println(formattedplain);
        System.out.println("Enter key : ");
        String key = s.nextLine();
        String fullkey = keyGenerator(key);
        char[][] x = matrixFormation(fullkey);
        System.out.println(Arrays.deepToString(x));
        int plen = formattedplain.length()/2;
        int ptr = 0;
        String[] plainarray = new String[plen];
        for(int i=0; i<plen; i++)
        {
            String temp = "";
            for(int j=0; j<2; j++)
            {
                temp += formattedplain.charAt(ptr);
                ptr++;
            }
            plainarray[i] = temp;
            System.out.println(plainarray[i]);
        }
        String encrptedvalue = encrypt(plainarray, x);
        System.out.println("Encrypted Value : " + encrptedvalue);
    }
}
