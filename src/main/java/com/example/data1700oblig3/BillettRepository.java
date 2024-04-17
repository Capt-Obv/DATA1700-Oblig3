package com.example.data1700oblig3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class BillettRepository {
    @Autowired
    private JdbcTemplate db;
    private final Logger logger = LoggerFactory.getLogger(BillettRepository.class);
    public boolean lagreBillett(Billett billett){
        String sql = "insert into Billett (film,antall,fornavn,etternavn,telefonnr,email) values(?,?,?,?,?,?)";
        try {
            db.update(sql, billett.getFilm(),billett.getAntall(), billett.getFornavn(),billett.getEtternavn(),billett.getTelefonnr(),billett.getEmail());
            return true;
        } catch (Exception e) {
            //have to make the logger work
            return false;
        }
    }

    public boolean slettEn(Billett billett){
        String sql =  "delete from Billett where fornavn=? and etternavn=? and telefonnr=? and email=?";
        try {
            db.update(sql, billett.getFornavn(), billett.getEtternavn(), billett.getTelefonnr(), billett.getEmail());
            return true;
        }
        catch (Exception e){
            //
            return false;
        }
    }
    public List<Billett> hentBilletter() {
        String sql = "select * from Billett order by etternavn";
        return db.query(sql, new BeanPropertyRowMapper<>(Billett.class));
    }


    public boolean slettAlle(){
        String sql = "delete from Billett";
        try{
            db.update(sql);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public boolean update(Billett oldBillett, Billett newBillett){
        String sql = "select id from Billett where film=? and antall=? and fornavn=? and etternavn=? and telefonnr=? and email=?";
        try{
            List<Billett> oldDbBillett = db.query(sql, new BeanPropertyRowMapper<>(Billett.class));
            String sql2 = "update Billett SET film=?, antall=?, fornavn=?, etternavn=?, telfonnr=?, email=? where id=?";
            db.update(sql2, newBillett.getFilm(), newBillett.getAntall(), newBillett.getFornavn(), newBillett.getEtternavn(),
                    newBillett.getTelefonnr(), newBillett.getEmail(), oldDbBillett.get(0));
            logger.info("Id: " +oldDbBillett.get(0));
            return true;
        }
        catch (Exception e){
            //logger
            return false;
        }
    }
}
