package sv.edu.udb.entities;

import java.util.HashMap;

public class IdGenerator {

    private static final HashMap<String, Integer> counters = new HashMap<>();

    // Genera IDs como LIB0001, REV0001, etc.
    public static String generarCodigo(String prefijo) {

        int numeroActual = counters.getOrDefault(prefijo, 0) + 1;
        counters.put(prefijo, numeroActual);

        return prefijo + String.format("%04d", numeroActual);
    }
}
