import java.sql.Date;

public class Eleve {

    private int idEleve;
    private String nomEleve;
    private String prenomEleve;
    private String dateNaissance;
    private int idClasse;
    private String mail;
    private String mdp;

    public Eleve(String nomEleve, String prenomEleve, String dateNaissance, int idClasse, String mail, String mdp) {
        this.nomEleve = nomEleve;
        this.prenomEleve = prenomEleve;
        this.dateNaissance = dateNaissance;
        this.idClasse = idClasse;
        this.mail = mail;
        this.mdp = mdp;
    }

    public Eleve() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    @Override
    public String toString() {
        return nomEleve + ' '+prenomEleve;
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

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }
}
