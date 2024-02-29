package ti2cc;

public class Relogio {
    private int codigo;
    private String modelo;
    private String marca;
    private int anofabricacao;

    public Relogio() {
        this.codigo = -1;
        this.modelo = "";
        this.marca = "";
        this.anofabricacao = -1;
    }

    public Relogio(int codigo, String modelo, String marca, int anofabricacao) {
        this.codigo = codigo;
        this.modelo = modelo;
        this.marca = marca;
        this.anofabricacao = anofabricacao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getAnoFabricacao() {
        return anofabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anofabricacao = anoFabricacao;
    }

    @Override
    public String toString() {
        return "Relogio [codigo=" + codigo + ", modelo=" + modelo + ", marca=" + marca + ", anofabricacao=" + anofabricacao + "]";
    }
}