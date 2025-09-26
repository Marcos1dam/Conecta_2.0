package utilidades;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Utilidades {

    // Scanner único para toda la clase (mejor práctica)
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Leer String simple (método requerido por Main.java)
     *
     * @param message Mensaje a mostrar al usuario
     * @return String ingresado por el usuario
     */
    public static String leerString(String message) {
        System.out.print(message);
        try {
            return scanner.nextLine().trim();
        } catch (NoSuchElementException e) {
            System.out.println("Error al leer datos");
            return "";
        }
    }

    /**
     * Leer entero simple (método requerido por Main.java)
     *
     * @param message Mensaje a mostrar al usuario
     * @return Entero ingresado por el usuario
     */
    public static int leerInt(String message) {
        while (true) {
            try {
                System.out.print(message);
                String input = scanner.nextLine().trim();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Por favor, ingrese un número válido.");
            } catch (NoSuchElementException e) {
                System.out.println("❌ Error al leer datos");
                return 0;
            }
        }
    }

    /**
     * Pausar hasta que el usuario presione Enter
     */
    public static void pausa() {
        System.out.print("Presione Enter para continuar...");
        scanner.nextLine();
    }

    /**
     * Pausar con mensaje personalizado
     */
    public static void pausa(String message) {
        System.out.print(message);
        scanner.nextLine();
    }

    /**
     * Validar si una cadena no está vacía
     */
    public static boolean validarCadenaNoVacia(String cadena) {
        return cadena != null && !cadena.trim().isEmpty();
    }

    /**
     * Limpiar pantalla (simulado)
     */
    public static void limpiarPantalla() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    /**
     * Mostrar mensaje de error estándar
     */
    public static void mostrarError(String mensaje) {
        System.err.println("❌ ERROR: " + mensaje);
    }

    /**
     * Mostrar mensaje de éxito estándar
     */
    public static void mostrarExito(String mensaje) {
        System.out.println("✅ ÉXITO: " + mensaje);
    }

    /**
     * Mostrar mensaje de advertencia estándar
     */
    public static void mostrarAdvertencia(String mensaje) {
        System.out.println("⚠️ ADVERTENCIA: " + mensaje);
    }

    /**
     * Repetir un carácter o string n veces (compatible con Java 8+)
     *
     * @param str String a repetir
     * @param count Número de repeticiones
     * @return String repetido
     */
    public static String repetir(String str, int count) {
        if (count <= 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * Repetir un carácter n veces (versión optimizada para caracteres)
     *
     * @param ch Carácter a repetir
     * @param count Número de repeticiones
     * @return String con el carácter repetido
     */
    public static String repetir(char ch, int count) {
        if (count <= 0) {
            return "";
        }

        char[] array = new char[count];
        for (int i = 0; i < count; i++) {
            array[i] = ch;
        }
        return new String(array);
    }

    // Utilidad para leer cadenas
    public static String introducirCadena(String mensaje) {
        Scanner sc = new Scanner(System.in);

        String cadena = "";
        System.out.println(mensaje);

        try {
            cadena = sc.next();
        } catch (NoSuchElementException er) {
            System.out.println("Error al introducir datos");
            System.exit(0);
        }
        // sc.close();
        return cadena;
    }

    // Utilidad para leer un real
    public static float leerFloat(String message) {
        float n = 0;
        boolean ok;

        do {
            try {
                ok = true;
                n = Float.parseFloat(introducirCadena(message));
            } catch (NumberFormatException e) {
                System.out.println("te equivocaste wey, intentalo otra vez: ");
                ok = false;
            }
        } while (!ok);

        return n;
    }

    // Utilidad para leer un entero
    /*public static int leerInt(String message) {
        int n = -1;
        boolean ok;

        do {
            try {
                ok = true;
                n = Integer.parseInt(introducirCadena(message));
            } catch (NumberFormatException e) {
                System.out.println("No has introducido un entero, intentalo otra vez: ");
                ok = false;
            }
        } while (!ok);

        return n;
    }*/

    // Leer un string
    public static String leerString(int x, String message) {
        String cadena = null;
        boolean ok;
        do {
            ok = true;
            cadena = introducirCadena(message);
            if (cadena.length() > x) {
                System.out.println("Error al introducir datos. ");
                ok = false;
            }
        } while (!ok);
        return cadena;
    }

    // Leer una respuesta
    public static boolean leerRespuesta(String message) {
        String respu;
        do {
            respu = introducirCadena(message).toLowerCase();
        } while (!respu.equals("0") && !respu.equals("1") && !respu.equals("si") && !respu.equals("no")
                && !respu.equals("s") && !respu.equals("n") && !respu.equals("true") && !respu.equals("false"));
        if (respu.equals("1") || respu.equals("si") || respu.equals("s") || respu.equals("true")) {
            return true;
        } else {
            return false;
        }
    }

    // leer int entre un rango
    public static int leerInt(int x, int y, String message) {
        int num = 0;
        boolean ok;
        do {
            try {
                ok = true;
                num = Integer.parseInt(introducirCadena(message));

            } catch (NumberFormatException e) {
                System.out.println("Hay que introducir numeros");
                ok = false;
                num = x;

            }
            if (num < x || num > y) {
                System.out.println("Dato fuera de rango, introduce entre " + x + " y " + y);
                ok = false;
            }
        } while (!ok);
        return num;
    }

    // leer float entre un rango
    public static float leerFloat(float x, float y, String message) {
        float fNumero = 0;
        boolean ok;
        do {
            try {
                ok = true;
                fNumero = Float.parseFloat(introducirCadena(message));
            } catch (NumberFormatException e) {
                System.out.println("Hay que introducir numeros. Vuelve aintroducir: ");
                ok = false;
                fNumero = x;
            }
            if (fNumero < x || fNumero > y) {
                System.out.println("Dato fuera de rando. Introduce entre " + x + " y " + y);
                ok = false;
            }
        } while (!ok);
        return fNumero;
    }

    // leer caracter
    public static char leerChar(String message) {
        boolean error = false;
        String letra;

        do {
            error = false;
            letra = introducirCadena(message);
            if (letra.length() != 1) {
                System.out.println("Error, introduce un caracter: ");
                error = true;
            }

        } while (error);
        return letra.charAt(0);
    }

    // Pido fecha
    public static LocalDate pidoFechaDMA(String message) {
        String fechaS;
        boolean hay;
        LocalDate fecha = null;
        // parseador
        DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        do {
            hay = true;
            fechaS = Utilidades.introducirCadena(message + " en formato dd-mm-aaaa: ");
            try {
                fecha = LocalDate.parse(fechaS, formateador);
            } catch (DateTimeParseException p) {
                System.out.println("Error... formato de fecha introducido incorrecto.");
                hay = false;
            }
        } while (!hay);
        return fecha;
    }

    public static char leerCharArray(char caracteres[], String message) {
        int i;
        boolean error = false;
        String letra;
        char aux = 0;

        do {
            error = false;
            letra = introducirCadena(message);
            if (letra.length() != 1) {
                System.out.println("Error, introduce un caracter: ");
                error = true;
            } else {
                aux = letra.toUpperCase().charAt(0);
                for (i = 0; i < caracteres.length; i++) {
                    if (Character.toUpperCase(caracteres[i]) == Character.toUpperCase(aux)) {
                        break;
                    }
                }
                if (i == caracteres.length) {
                    error = true;
                    System.out.println("Error, el caracter introducido no es valido. ");
                }
            }
        } while (error);
        return aux;
    }

    // Devuelve el n�mero de objetos de un fichero
    public static int calculoFichero(File fich) {
        int cont = 0;
        if (fich.exists()) {
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                fis = new FileInputStream(fich);
                ois = new ObjectInputStream(fis);

                Object aux = ois.readObject();

                while (aux != null) {
                    cont++;
                    aux = ois.readObject();
                }
            } catch (EOFException e1) {
                System.out.println("Has acabado de leer, tienes " + cont + " objetos");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                ois.close();
                fis.close();
            } catch (IOException e) {
                System.out.println("Error al cerrar los flujos");
            }
        }
        return cont;
    }

}
