package gov.justucuman.seguridad_barrial.domain;

public class TelefonoMother {

    public static Telefono valid() {
           // Genera un teléfono aleatorio de 10 dígitos
           StringBuilder sb = new StringBuilder();
           for (int i = 0; i < 10; i++) {
              sb.append((int) (Math.random() * 10));
           }
           return new Telefono(sb.toString());
    }

    public static Telefono withNull() {
        return new Telefono(null);
    }

    public static String invalidBlank() {
        return "   ";
    }
}
