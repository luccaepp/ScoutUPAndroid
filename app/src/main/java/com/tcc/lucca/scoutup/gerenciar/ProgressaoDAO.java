package com.tcc.lucca.scoutup.gerenciar;

/**
 * Created by lucca on 20/10/17.
 */

public class ProgressaoDAO extends GenericDAO {

    public ProgressaoDAO() {
        setReferencia("escopoProgressao");

    }

    public static ProgressaoDAO getInstance() {
        return new ProgressaoDAO();
    }




}