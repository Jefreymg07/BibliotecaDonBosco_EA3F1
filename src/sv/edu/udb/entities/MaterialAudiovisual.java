package sv.edu.udb.entities;

public class MaterialAudiovisual extends Material {
    private String formato;
    private int duracion; // en minutos

    public MaterialAudiovisual(String codigo, String titulo, int anio, String tipo, String formato, int duracion) {
        super(codigo, titulo, anio, tipo);
        this.formato = formato;
        this.duracion = duracion;
    }

    public String getFormato(){ return formato; }
    public int getDuracion(){ return duracion; }
    public void setFormato(String f){ this.formato = f; }
    public void setDuracion(int d){ this.duracion = d; }

    @Override
    public String getInformacion(){
        return "Código: " + codigo
             + " | Título: " + titulo
             + " | Año: " + anio
             + " | Tipo: " + tipo
             + " | Formato: " + formato
             + " | Duración: " + duracion + "m";
    }
}
