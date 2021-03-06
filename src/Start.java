import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

class Start {

    public static void main(String[] args) {
        Scanner usrInput = new Scanner(System.in);
        String usrVar = usrInput.nextLine();

        Calc calc = new Calc();

        try{
            String[] arg = calc.getArg(usrVar);
            int result = calc.calc(arg[3],
                    Integer.parseInt(arg[1]),
                    Integer.parseInt(arg[2]));

            if (arg[0].equals("rome")){
                System.out.println(RomanArabicConverter.arabicToRoman(result));
            }else{
                System.out.println(result);
            }
        }
        catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}

class Calc {

    public String[] getArg(String usrVar) throws Exception {
        String[] result = validat(usrVar);
        if (result[0].equals("rome")) {
            result[1] = String.valueOf(RomanArabicConverter.romanToArabic(result[1]));
            result[2] = String.valueOf(RomanArabicConverter.romanToArabic(result[2]));
        }
        if ((Integer.parseInt(result[1]) > 10 || Integer.parseInt(result[1]) < 1) ||
                (Integer.parseInt(result[2]) > 10 || Integer.parseInt(result[2]) < 1)) {
            throw new Exception("Калькулятор работает только с числами от 1 до 10 включительно.");
        }
        return result;
    }

    //  обработка входящей строки - получение аргументов и типа действия
    public String[] validat(String usrVar) throws Exception {
      usrVar = usrVar.replaceAll("\\s+", "");
        char[] act = new char[]{'+', '-', '*', '/'};
        int indexM = -1;
        for (char c : act) {
            if (usrVar.indexOf(c) != -1) {
                indexM = usrVar.indexOf(c);
            }
        }
        if (indexM == -1) {throw new Exception("Не указанно арифмитическое действие");}

        String[] argument = usrVar.split(Pattern.quote(String.valueOf(usrVar.charAt(indexM))));    // Pattern.quote регулярка для (*,+,/)

        if (argument.length!=2) {throw new Exception("Не правильный ввод - Введите два висла.");}

        //Определение типа чисел (арабские или римские)
        if ((argument[0].matches("\\d+"))
                && (argument[1].matches("\\d+"))) {
            return new String[] {"arabian", argument[0], argument[1], String.valueOf(usrVar.charAt(indexM))};
        }else if((argument[0].matches("^[IVX]+"))
                && (argument[1].matches("^[IVX]+"))) {
            return new String[] {"rome", argument[0], argument[1], String.valueOf(usrVar.charAt(indexM))};
        }else{
            throw new Exception("Введенные аргументы не являются целыми числами, либо введены числа разного вида.");
        }
    }

    public int calc(String job, int arg1, int arg2) throws Exception {
        // сделать расчет
        int result = 0;
        switch(job){
            case "+":
                result = arg1+arg2;
                break;
            case "-":
                result = arg1-arg2;
                break;
            case "*":
                result = arg1*arg2;
                break;
            case "/":
                if (arg2 != 0){
                    result = arg1/arg2;
                }else {
                    throw new Exception("Деление на ноль не возможно.");
                }
                break;
        }
        return result;
    }
}

// Класс преобразования Арабские <=> Римские - найден в интернете, принцип понят, но сам с ходу не напишу
class RomanArabicConverter {
    enum RomanNumeral {
        I(1), IV(4), V(5), IX(9), X(10),
        XL(40), L(50), XC(90), C(100),
        CD(400), D(500), CM(900), M(1000);

        private int value;

        RomanNumeral(int value) {
            this.value = value;
        }
        public int getValue() {
            return value;
        }
        public static List<RomanNumeral> getReverseSortedValues() {
            return Arrays.stream(values())
                    .sorted(Comparator.comparing((RomanNumeral e) -> e.value).reversed())
                    .collect(Collectors.toList());
        }
    }

    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;

        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }

        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " не правильная запись, невозможно преобразовать");
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        if ((number <= 0) || (number > 4000)) {
            throw new IllegalArgumentException(number + " использование римских чисел возможно в диапозоне (0,4000]");
        }

        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();

        int i = 0;
        StringBuilder sb = new StringBuilder();

        while ((number > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }

        return sb.toString();
    }
}
