import java.sql.Date;

public class Eleves {

    private int idEleve;
    private String nomEleve;
    private String prenomEleve;
    private Date dateNaissance;
    private int idClasse;

    public Eleves(String nomEleve, String prenomEleve, Date dateNaissance, int idClasse) {
        this.nomEleve = nomEleve;
        this.prenomEleve = prenomEleve;
        this.dateNaissance = dateNaissance;
        this.idClasse = idClasse;
    }

    public Eleves(String nomEleve, String prenomEleve, Date dateNaissance) {
        this.nomEleve = nomEleve;
        this.prenomEleve = prenomEleve;
        this.dateNaissance = dateNaissance;
    }

    @Override
    public String toString() {
        return "Eleves{" +
                "idEleve=" + idEleve +
                ", nomEleve='" + nomEleve + '\'' +
                ", prenomEleve='" + prenomEleve + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", idClasse=" + idClasse +
                '}';
    }

    public int getIdEleve() {
        return idEleve;
    }

    public void setIdEleve(int idEleve) {
        this.idEleve = idEleve;
    }

    public String getNomEleve() {
        return nomEleve;
    }

    public void setNomEleve(String nomEleve) {
        this.nomEleve = nomEleve;
    }

    public String getPrenomEleve() {
        return prenomEleve;
    }

    public void setPrenomEleve(String prenomEleve) {
        this.prenomEleve = prenomEleve;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }
}
