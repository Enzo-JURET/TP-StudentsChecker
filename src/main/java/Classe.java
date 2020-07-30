public class Classe {

    // Non obligatoire
    private Integer idClasse;
    private String libelleClasse;
    private String periodeClasse;

    public Classe(String libelleClasse, String periodeClasse) {
        this.libelleClasse = libelleClasse;
        this.periodeClasse = periodeClasse;
    }

    public Classe() {
    }

    @Override
    public String toString() {
        return "Classe{" +
                "idClasse=" + idClasse +
                ", libelleClasse='" + libelleClasse + '\'' +
                ", periodeClasse='" + periodeClasse + '\'' +
                '}';
    }

    public Integer getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(Integer idClasse) {
        this.idClasse = idClasse;
    }

    public String getLibelleClasse() {
        return libelleClasse;
    }

    public void setLibelleClasse(String libelleClasse) {
        this.libelleClasse = libelleClasse;
    }

    public String getPeriodeClasse() {
        return periodeClasse;
    }

    public void setPeriodeClasse(String periodeClasse) {
        this.periodeClasse = periodeClasse;
    }
}
