package sv.edu.udb.entities;

public abstract class Material {
    protected String codigo;
    protected String titulo;
    protected int anio;
    protected String tipo;

    public Material(String codigo, String titulo, int anio, String tipo) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.anio = anio;
        this.tipo = tipo;
    }

    // getters / setters
    public String getCodigo(){ return codigo; }
    public String getTitulo(){ return titulo; }
    public int getAnio(){ return anio; }
    public String getTipo(){ return tipo; }

    public void setTitulo(String titulo){ this.titulo = titulo; }
    public void setAnio(int anio){ this.anio = anio; }

    public void setCodigo(String codigo){
    this.codigo = codigo;
}

public void setTipo(String tipo){
    this.tipo = tipo;
}

    // cada subclase devuelve una descripción para la tabla
    public abstract String getInformacion();
}
