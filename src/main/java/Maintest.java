import java.sql.SQLException;
import java.util.List;

public class Maintest {

    public static void main(String[] args)throws SQLException{

        Login log = new Login();
        log.setId("ayman072@hotmail.fr");
        log.setMdp("mdpbidon");
        log.seConnecter();

        System.out.println(log.getId()+" "+log.getMdp());
        System.out.println(log.isConnecte());
        System.out.println(log.isAdmin());

        Eleve eleve = new Eleve();
        eleve.setPrenomEleve("ayman");
        eleve.setNomEleve("agharbi");
        eleve.setDateNaissance("1998-05-14");
        eleve.setIdClasse(1);
        eleve.setMail("ayman072@hotmail.fr");
        eleve.setMdp("mdpbidon");
        EleveDao alevesDao = new EleveDao();

        // add
        //alevesDao.addEleve(eleve);

        // read
        Eleve ele = alevesDao.getEleve(9);
        System.out.println(ele.toString());

        // read All
        List<Eleve> ls = alevesDao.getEleves();
        for (Eleve allele : ls) {
            System.out.println(allele);
        }

        // update
        Eleve tempEleve = alevesDao.getEleve(9);
        tempEleve.setNomEleve("Asgard");
        alevesDao.updateEleve(tempEleve);

        // delete
       //alevesDao.deleteEleve(5);
    }
}
