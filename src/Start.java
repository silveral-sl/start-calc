import java.util.Scanner;
import java.util.regex.Pattern;

class Start {

    public static void main(String[] args) {
        Scanner usrInput = new Scanner(System.in);
        String usrVar = usrInput.nextLine();

        Calc calc = new Calc();
        String[] arg = calc.calculation(usrVar);
        System.out.println(arg[0]);
    }

}

class Calc {

    public String[] calculation(String usrVar){
        String[] result = validat(usrVar);
        System.out.println(result[0]);
        return result;
    }

    //  обработка входящей строки - получение аргументов и типа действия
    public String[] validat(String usrVar) {
        usrVar = usrVar.replaceAll("\\s+", "");
 //       String typeNam;
        char[] act = new char[]{'+', '-', '*', '/'};
        int indexM = -1;
        for (int i=0; i<act.length-1; i++){
            if (usrVar.indexOf(act[i]) != -1) {
                indexM = usrVar.indexOf(act[i]);
            }
        }
        if (indexM == -1) {return new String[] {"Err01"};}

        System.out.println(indexM);
        System.out.println(usrVar.charAt(indexM));
        String[] argument = usrVar.split(Pattern.quote(String.valueOf(usrVar.charAt(indexM))));    // Pattern.quote регулярка (*,+,/)

        if (argument.length!=2) {return new String[] {"Err01"};}
        if ((argument[0].matches("\\d+"))
                && (argument[1].matches("\\d+"))) {
            System.out.println("числа");
            return new String[] {"arabian", argument[0], argument[1], String.valueOf(usrVar.charAt(indexM))};
        }else if((argument[0].matches("^[IVX]+"))
                && (argument[1].matches("[IVX]+"))) {
            System.out.println("Rome");
            return new String[] {"rome", argument[0], argument[1], String.valueOf(usrVar.charAt(indexM))};
        }else{
            return new String[] {"Err01"};
        }
//        System.out.println(argument[0]+">>"+argument[1]+">> "+usrVar.charAt(indexM));
//        return new String[] {argument[0], argument[1], String.valueOf(usrVar.charAt(indexM))};
    }
}
