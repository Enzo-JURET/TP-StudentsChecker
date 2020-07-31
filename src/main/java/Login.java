
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {

    static Connection con = DataConnection.getConnection();
    private boolean connecte = false;
    private Object o = null;
    private String id;
    private String mdp;

    public Login(boolean connecte, Object o) {
        this.connecte = connecte;
        this.o = o;
    }

    public Login() {
    }

    public boolean isConnecte() {
        return connecte;
    }

    public void setConnecte(boolean connecte) {
        this.connecte = connecte;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public void seConnecter() throws SQLException {

        String id = this.id;
        String mdp = this.mdp;

        String query = "select * from admin where mailAdmin= ? and mdpAdmin=?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, id);
        ps.setString(2, mdp);
        ResultSet rs = ps.executeQuery();
        boolean check = false;

        while (rs.next()) {
            check = true;
            this.o = null;
            this.connecte = true;
        }

        ResultSet rs2 = null;
        if(check==false){
            String query2 = "select * from eleve where mail= ? and mdp=?";
            PreparedStatement ps2 = con.prepareStatement(query2);
            ps2.setString(1, id);
            ps2.setString(2, mdp);
            rs2 = ps2.executeQuery();
            while (rs2.next()) {
                check = true;
                Eleve e = new Eleve();
                e.setIdEleve(rs2.getInt("idEleve"));
                e.setNomEleve(rs2.getString("nomEleve"));
                e.setPrenomEleve(rs2.getString("prenomEleve"));
                e.setDateNaissance(rs2.getString("dateNaissance"));
                e.setIdClasse(rs2.getInt("idClasse"));
                e.setMail(rs2.getString("mail"));
                e.setMdp(rs2.getString("mdp"));
                this.o = e;
                this.connecte = true;
            }
        }

    }

    public boolean isAdmin(){
        if (this.connecte == true){
            if (this.o == null){
                return true;
            }else
                return false;
        }else
            return false;
    }

    public void deconnection(){
        this.setConnecte(false);
    }

}
