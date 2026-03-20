package gov.justucuman.seguridad_barrial.domain;

public class DniMother {

    public static Dni valid() {
           // Genera un DNI aleatorio de 7 u 8 dígitos
           int length = Math.random() < 0.5 ? 7 : 8;
           StringBuilder sb = new StringBuilder();
           for (int i = 0; i < length; i++) {
              sb.append((int) (Math.random() * 10));
           }
           return new Dni(sb.toString());
    }

    public static Dni withSevenDigits() {
        return new Dni("1234567");
    }

    public static String invalidWithLetters() {
        return "1234abcd";
    }

    public static String invalidWithLessThanSevenDigits() {
        return "123456";
    }

    public static String invalidWithMoreThanEightDigits() {
        return "123456789";
    }
}
