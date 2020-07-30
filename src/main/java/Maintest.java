import java.sql.SQLException;
import java.util.List;

public class Maintest {

    public static void main(String[] args)throws SQLException{

        Eleve eleve = new Eleve();
        eleve.setPrenomEleve("ayman");
        eleve.setNomEleve("agharbi");
        eleve.setDateNaissance("1998-05-14");
        eleve.setIdClasse(1);
        EleveDao alevesDao = new EleveDao();

        // add
        alevesDao.add(eleve);

        // read
        Eleve ele = alevesDao.getEleve(5);
        System.out.println(ele.toString());

        // read All
        List<Eleve> ls = alevesDao.getEleves();
        for (Eleve allele : ls) {
            System.out.println(allele);
        }

        // update
        Eleve tempEleve = alevesDao.getEleve(4);
        tempEleve.setNomEleve("Asgard");
        alevesDao.update(tempEleve);

        // delete
        alevesDao.delete(5);
    }
}
