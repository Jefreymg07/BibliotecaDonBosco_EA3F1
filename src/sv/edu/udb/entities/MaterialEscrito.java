package sv.edu.udb.entities;

public class MaterialEscrito extends Material {

    private String autor;
    private String genero;

    // Constructor vacío seguro: llama a super(...) con valores por defecto
    // Esto evita el error de "constructor Material cannot be applied to given types"
    public MaterialEscrito() {
        super("", "", 0, "Escrito"); // valores por defecto: si prefieres otros, cambia aquí
        this.autor = "";
        this.genero = "";
    }

    // Constructor completo (para cuando crees objetos con datos)
    public MaterialEscrito(String codigo, String titulo, int anio, String tipo, String autor, String genero) {
        super(codigo, titulo, anio, tipo);
        this.autor = autor;
        this.genero = genero;
    }

    public String getAutor() { return autor; }
    public String getGenero() { return genero; }
    public void setAutor(String a) { this.autor = a; }
    public void setGenero(String g) { this.genero = g; }

    @Override
    public String getInformacion() {
        return "Código: " + codigo
                + " | Título: " + titulo
                + " | Año: " + anio
                + " | Tipo: " + tipo
                + " | Autor: " + autor
                + " | Género: " + genero;
    }
}
